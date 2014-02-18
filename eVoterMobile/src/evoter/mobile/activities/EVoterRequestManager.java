/**
 * 
 */
package evoter.mobile.activities;

import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 */
public class EVoterRequestManager {
	
	/**
	 * submit a value of an answer
	 * 
	 * @param answerID
	 * @param questionTypeID
	 * @param statistic
	 * @param context
	 */
	public static void doVote(long answerID, long questionTypeID, final String statistic, final EVoterActivity context) {
		String valueToSend = null;
		if (statistic != null) {
			if (statistic.contains(QuestionActivity.ANSWER_SUBMIT))
				valueToSend = statistic.replace(QuestionActivity.ANSWER_SUBMIT, "");
			else
				valueToSend = statistic;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(questionTypeID));
		params.put(AnswerDAO.ID, String.valueOf(answerID));
		if (valueToSend != null)
			params.put(AnswerDAO.STATISTICS, valueToSend);
		client.post(RequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response + QuestionActivity.ANSWER_SUBMIT);
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(context,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param i_Usrname
	 * @param i_Password
	 */
	public static void doLogin(final String i_Usrname, final String i_Password, final Context context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_NAME, i_Usrname);
		params.add(UserDAO.PASSWORD, i_Password);
		client.post(RequestConfig.getURL(URIRequest.LOGIN), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						Log.i("Response", response);
						loginResponseProcess(i_Usrname, i_Password, response, context);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("Login", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
	}
	
	/**
	 * @param i_Usrname
	 * @param response
	 */
	public static void loginResponseProcess(final String i_Usrname, final String i_password, String response, Context context) {
		String userKey = null;
		try {
			
			JSONObject object = new JSONObject(
					response);
			userKey = object
					.getString(UserDAO.USER_KEY);
			
		} catch (JSONException e) {
			e.printStackTrace();
			EVoterMobileUtils.showeVoterToast(context, "Error! Cannot get user information");
		}
		
		//Got the userkey
		if (userKey != null && userKey != "null") {
			Log.i("USER_KEY", userKey);
			EVoterShareMemory.getOfflineEVoterManager()
					.rememberCurrentUser(i_Usrname,
							i_password);
			EVoterShareMemory
					.setUSER_KEY(userKey);
			EVoterShareMemory
					.setCurrentUserName(i_Usrname);
			EVoterMobileUtils.showeVoterToast(
					context,
					"Welcome "
							+ EVoterShareMemory
									.getCurrentUserName()
							+ " to eVoter!");
			
			Intent subjectIntent = new Intent(
					context,
					SubjectActivity.class);
			subjectIntent
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			subjectIntent
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(subjectIntent);
			
		}
		else {
			EVoterMobileUtils.showeVoterToast(context, "Error! Username and password is not correct. Please try again!");
		}
	}
	
	/**
	 * @param i_email
	 */
	public static void resetPassword(final String i_email, final Context context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put(UserDAO.EMAIL, i_email);
		client.post(RequestConfig.getURL(URIRequest.RESET_PASSWORD), params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				Log.i("Reset password", response);
				if (response.contains(URIRequest.FAILURE_MESSAGE)) {
					EVoterMobileUtils.showeVoterToast(context,
							"Email not exists. Please try again or register new account!");
				}
				else if (response.contains(URIRequest.EMAIL_EXIST_MESSAGE)) {
					// TODO: Send request to sever: email to change the password
					EVoterMobileUtils.showeVoterToast(context,
							"You will receive an email confirm to reset your password! Email send to address: " + i_email);
					ActivityManager.gotoLogin(context);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				Log.e("Reset password", "onFailure error : "
						+ error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param i_email
	 * @param i_usrname
	 * @param i_password
	 * @param registerActivity
	 */
	public static void createNewUser(String i_email, final String i_usrname, String i_password, final Context context) {
		AsyncHttpClient client = new AsyncHttpClient();
		
		RequestParams params = new RequestParams();
		params.put(UserDAO.EMAIL, i_email);
		params.put(UserDAO.PASSWORD, i_password);
		params.put(UserDAO.USER_NAME, i_usrname);
		params.put(UserDAO.USER_TYPE_ID, String.valueOf(UserType.STUDENT));
		
		client.post(RequestConfig.getURL(URIRequest.REGISTER), params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				Log.i("REGISTER", "Successful: " + response);
				if (response.contains("USER EXISTS ALREADY")) {
					EVoterMobileUtils.showeVoterToast(context,
							"Username already used by other user. Please choose another username");
				}
				else if (response.contains("EMAIL EXISTS ALREADY")) {
					EVoterMobileUtils.showeVoterToast(context,
							"Email already registered in system. Please register by another email or use reset password!");
				}
				else {
					EVoterMobileUtils.showeVoterToast(context,
							"You will receive an email to confirm your register!");
					EVoterShareMemory.setCurrentUserName(i_usrname);
					ActivityManager.gotoLogin(context);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				Log.e("REGISTER", "onFailure error : "
						+ error.toString() + "content : " + content);
			}
		});
		
	}
	
	public static void updateQuestion(final Question questionToUpdate) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(questionToUpdate.getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.VIEW_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						updateQuestionCallBack(questionToUpdate, response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * @param questionToUpdate
	 */
	protected static void updateQuestionCallBack(Question questionToUpdate, String response) {
		try {
			JSONArray array = new JSONArray(response);
			Question question = null;
			if (array != null)
				question = EVoterMobileUtils.parserToQuestion(array.getJSONObject(0));
			if (question != null) {
				questionToUpdate = question;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void updateSession(final Session session) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.ID,
				String.valueOf(session.getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						updateSessionCallBack(session, response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Update session", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param session
	 * @param response
	 */
	protected static void updateSessionCallBack(Session sessionToUpdate, String response) {
		Log.i("View session", response);
		JSONArray array;
		try {
			array = EVoterMobileUtils.getJSONArray(response);
			Session session = EVoterMobileUtils.parserSession(array.getJSONObject(0));
			if (session != null) {
				sessionToUpdate = session;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Update session", "Exception");
		}
		
	}
	
	public static void getStatistic(long questionID, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(questionID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.GET_STATISTICS), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Statistic", response);
						context.updateRequestCallBack(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * @param subjectActivity
	 */
	public static void getListSubject(final SubjectActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Subject Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param sessionActivity
	 */
	public static void getListSession(final SessionActivity sessionActivity, long subjectID) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.SUBJECT_ID,
				String.valueOf(subjectID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		Log.i("SUBJECT_ID",
				String.valueOf(subjectID));
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						sessionActivity.updateRequestCallBack(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param subjectUserActivity
	 * @param id
	 */
	public static void getUserOfSubject(final SubjectUserActivity subjectUserActivity, long id) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SubjectDAO.ID, String.valueOf(EVoterShareMemory.getCurrentSubjectID()));
		
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_USERS_OF_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						Log.i("Request all user of subject", response);
						subjectUserActivity.updateRequestCallBack(response);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								subjectUserActivity,
								"Cannot request to server!");
						Log.e("Get users of subject", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
	/**
	 * @param studentFeedbackActivity
	 * @param excited
	 */
	public static void updateStaticValue(final StudentFeedbackActivity context, final String excited) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (excited.equals(QuestionActivity.EXCITED)) {
			params.add(QuestionDAO.ID,
					String.valueOf(EVoterShareMemory.getExictedQuestion().getId()));
		} else {
			params.add(QuestionDAO.ID,
					String.valueOf(EVoterShareMemory.getDifficultQuestion().getId()));
		}
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.GET_STATISTICS), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Statistic", response);
						context.updateRequestCallBack(response + excited);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get statistic value", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param questionActivity
	 */
	public static void getListQuestion(final QuestionActivity questionActivity) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						questionActivity.updateRequestCallBack(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param acceptedStudents
	 */
	public static void getListAcceptedSutdent(final AcceptedStudents context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.add(SessionUserDAO.ACCEPT_SESSION, String.valueOf(true));
		
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_STUDENT), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("Accepted user request", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
	/**
	 * 
	 */
	public static void createSessionRequest(String sessionTitle, long subjectID, final NewSessionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		params.add(SessionDAO.CREATION_DATE, EVoterMobileUtils.convertToString(timeStamp));
		params.add(SessionDAO.IS_ACTIVE, String.valueOf(false));
		params.add(SessionDAO.NAME, sessionTitle);
		params.add(SessionDAO.SUBJECT_ID, String.valueOf(subjectID));
		
		client.post(RequestConfig.getURL(URIRequest.CREATE_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("Create new session", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
	}
	
	/**
	 * 
	 */
	public static void editSession(String newTitle, long sessionID, final EditSessionActivity context) {
		if (newTitle.equals("")) {
			EVoterMobileUtils.showeVoterToast(context, "Session title cannot be empty!");
		}
		else {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
			params.add(SessionDAO.NAME, newTitle);
			params.add(SessionDAO.ID, String.valueOf(sessionID));
			
			client.post(RequestConfig.getURL(URIRequest.UPDATE_SESSION), params,
					new AsyncHttpResponseHandler() {
						// Request successfully - client receive a response
						@Override
						public void onSuccess(String response) {
							context.updateRequestCallBack(response);
						}
						
						//Login fail
						@Override
						public void onFailure(Throwable error,
								String content) {
							EVoterMobileUtils.showeVoterToast(
									context,
									"Cannot request to server!");
							Log.e("Create new session", "onFailure error : "
									+ error.toString() + "content : "
									+ content);
						}
					});
			
		}
	}
	
	/**
	 * @param params
	 * @param newQuestionActivity
	 */
	public static void createNewQuestion(RequestParams params, final NewQuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(RequestConfig.getURL(URIRequest.CREATE_QUESTION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("create question", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
						context.finish();
					}
				});
		
	}
	
	/**
	 * @param params
	 * @param editSessionActivity
	 */
	public static void editQuestion(RequestParams params, final EditQuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(RequestConfig.getURL(URIRequest.UPDATE_QUESTION), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response);
					}
					
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("edit question", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
						context.finish();
					}
				});
		
	}
	
	/**
	 * @param params
	 * @param questionActivity
	 */
	public static void acceptSession(RequestParams params, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(RequestConfig.getURL(URIRequest.ACCEPT_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response + QuestionActivity.ACCEPT_SESSION);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("Accept session", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
	/**
	 * @param start
	 * @param params
	 * @param url
	 * @param questionActivity
	 */
	public static void changeSessionStatus(boolean start, RequestParams params, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = start ? URIRequest.ACTIVE_SESSION : URIRequest.INACTIVE_SESSION;
		client.post(RequestConfig.getURL(url), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response + QuestionActivity.CHANGE_SESSION_STATUS);
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request to server!");
						Log.e("start session", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
	/**
	 * @param params
	 * @param questionActivity
	 */
	public static void submitStatisticValue(RequestParams params, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(RequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response + QuestionActivity.SUBMIT_STATISTIC_MESSAGE);
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(context,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param id
	 * @param questionActivity
	 */
	public static void deleteQuestion(long id, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		client.post(RequestConfig.getURL(URIRequest.DELETE_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response + QuestionActivity.DELETE_QUESTION_MESSAGE);
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(context,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void stopReceiveAnswer(long sessionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionID));
		client.post(RequestConfig.getURL(URIRequest.STOP_SEND_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response + QuestionDetailActivity.STOP_RECEIVE_ANSWER);
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(context,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void submitAnswer(long questionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(questionID));
		client.post(RequestConfig.getURL(URIRequest.VIEW_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response + QuestionDetailActivity.SUBMIT_ANSWER);
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(context,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void checkSessionStatus(long sessionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.ID,
				String.valueOf(sessionID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response + QuestionDetailActivity.CHECK_SESSION_STATUS);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("View session", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
}
