/**
 * 
 */
package evoter.mobile.main;

import java.sql.Timestamp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.activities.AcceptedStudentsActivity;
import evoter.mobile.activities.EditQuestionActivity;
import evoter.mobile.activities.EditSessionActivity;
import evoter.mobile.activities.NewQuestionActivity;
import evoter.mobile.activities.NewSessionActivity;
import evoter.mobile.activities.QuestionActivity;
import evoter.mobile.activities.QuestionDetailActivity;
import evoter.mobile.activities.ResetPasswordActivity;
import evoter.mobile.activities.SessionActivity;
import evoter.mobile.activities.StudentFeedbackActivity;
import evoter.mobile.activities.SubjectActivity;
import evoter.mobile.activities.SubjectUserActivity;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * Manage all request of application to server
 * @author luongnv89
 */
public class EVoterRequestManager {
	
	/**
	 * answer a question
	 * 
	 * @param answerID
	 * @param questionTypeID
	 * @param statistic
	 * @param context
	 */
	public static void doVote(long answerID, long questionTypeID, final String statistic, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(questionTypeID));
		params.put(AnswerDAO.ID, String.valueOf(answerID));
		if (statistic != null)
			params.put(AnswerDAO.STATISTICS, statistic);
		client.post(EVoterRequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.SUBMIT_ANSWER_EVOTER_REQUEST);
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
	 * Do login to system
	 * @param i_Usrname
	 * @param i_Password
	 */
	public static void doLogin(final String i_Usrname, final String i_Password, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_NAME, i_Usrname);
		params.add(UserDAO.PASSWORD, i_Password);
		client.post(EVoterRequestConfig.getURL(URIRequest.LOGIN), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.LOGIN_EVOTER_REQUEST);
					}
					
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
	 * Reset password
	 * @param i_email
	 */
	public static void resetPassword(final String i_email, final ResetPasswordActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put(UserDAO.EMAIL, i_email);
		client.post(EVoterRequestConfig.getURL(URIRequest.RESET_PASSWORD), params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.RESET_PASSWORD_EVOTER_REQUEST);
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				Log.e("Reset password", "onFailure error : "
						+ error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * Create new student user
	 * @param i_email
	 * @param i_usrname
	 * @param i_password
	 * @param registerActivity
	 */
	public static void createNewUser(String i_email, final String i_usrname, String i_password, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		
		RequestParams params = new RequestParams();
		params.put(UserDAO.EMAIL, i_email);
		params.put(UserDAO.PASSWORD, i_password);
		params.put(UserDAO.USER_NAME, i_usrname);
		params.put(UserDAO.USER_TYPE_ID, String.valueOf(UserType.STUDENT));
		
		client.post(EVoterRequestConfig.getURL(URIRequest.REGISTER), params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.CREATE_USER_EVOTER_REQUEST);
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				Log.e("REGISTER", "onFailure error : "
						+ error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * Update current question
	 * @param context
	 */
	public static void updateQuestion(final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.VIEW_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.UPDATE_QUESTION_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * Update current session
	 * @param context
	 */
	public static void updateSession(final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.ID,
				String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.UPDATE_SESSION_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Update session", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Get statistic of a question
	 * @param questionID
	 * @param context
	 */
	public static void getStatistic(long questionID, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(questionID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_STATISTICS), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.GET_STATISTIC_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * Get list subject of current user
	 * @param subjectActivity
	 */
	public static void getListSubject(final SubjectActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_ALL_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.GET_ALL_SUBJECT_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Subject Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Get list session of current subject
	 * @param sessionActivity
	 */
	public static void getAllSession(final SessionActivity sessionActivity, long subjectID) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.SUBJECT_ID,
				String.valueOf(subjectID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		Log.i("SUBJECT_ID",
				String.valueOf(subjectID));
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_ALL_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Get all Session: ", response);
						sessionActivity.updateRequestCallBack(response, CallBackMessage.GET_ALL_SESSION_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Get list user who involve in current subject
	 * @param subjectUserActivity
	 * @param id
	 */
	public static void getUserOfSubject(final SubjectUserActivity subjectUserActivity, long id) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SubjectDAO.ID, String.valueOf(EVoterShareMemory.getCurrentSubjectID()));
		
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_ALL_USERS_OF_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						Log.i("Request all user of subject", response);
						subjectUserActivity.updateRequestCallBack(response, CallBackMessage.GET_USER_OF_SUBJECT_EVOTER_REQUEST);
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
	 * Update statistic of feedback in {@link StudentFeedbackActivity}
	 * @param studentFeedbackActivity
	 * @param excited
	 */
	public static void updateStaticValue(final StudentFeedbackActivity context, final String excited) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (excited.equals(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST)) {
			params.add(QuestionDAO.ID,
					String.valueOf(EVoterShareMemory.getExictedQuestion().getId()));
		} else {
			params.add(QuestionDAO.ID,
					String.valueOf(EVoterShareMemory.getDifficultQuestion().getId()));
		}
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_STATISTICS), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Statistic", response);
						context.updateRequestCallBack(response, excited);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get statistic value", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Get all question of current session
	 * @param questionActivity
	 */
	public static void getListQuestion(final QuestionActivity questionActivity) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_ALL_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						questionActivity.updateRequestCallBack(response, CallBackMessage.GET_ALL_QUESTION_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Get all student who has accepted to join current session
	 * @param acceptedStudents
	 */
	public static void getUsersOfSession(final AcceptedStudentsActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.add(SessionUserDAO.ACCEPT_SESSION, String.valueOf(true));
		
		client.post(EVoterRequestConfig.getURL(URIRequest.GET_ALL_STUDENT), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.GET_LIST_ACCEPTED_STUDENT_EVOTER_REQUEST);
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
	 * Create new session
	 * @param sessionTitle
	 * @param subjectID
	 * @param context
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
		
		client.post(EVoterRequestConfig.getURL(URIRequest.CREATE_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.CREATE_SESSION_EVOTER_REQUEST);
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
	 * Edit a session
	 * @param newTitle
	 * @param sessionID
	 * @param context
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
			
			client.post(EVoterRequestConfig.getURL(URIRequest.UPDATE_SESSION), params,
					new AsyncHttpResponseHandler() {
						// Request successfully - client receive a response
						@Override
						public void onSuccess(String response) {
							context.updateRequestCallBack(response, CallBackMessage.EDIT_SESSION_EVOTER_REQUEST);
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
	 * Create new question
	 * @param params
	 * @param newQuestionActivity
	 */
	public static void createNewQuestion(RequestParams params, final NewQuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(EVoterRequestConfig.getURL(URIRequest.CREATE_QUESTION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.CREATE_QUESTION_EVOTER_REQUEST);
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
					}
				});
		
	}
	
	/**
	 * Edit a question
	 * @param params
	 * @param editSessionActivity
	 */
	public static void editQuestion(RequestParams params, final EditQuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(EVoterRequestConfig.getURL(URIRequest.UPDATE_QUESTION), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.EDIT_QUESTION_EVOTER_REQUEST);
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
					}
				});
		
	}
	
	/**
	 * Student accept to join a session
	 * @param params
	 * @param questionActivity
	 */
	public static void acceptSession(long sessionID, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(sessionID));
		client.post(EVoterRequestConfig.getURL(URIRequest.ACCEPT_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.ACCEPT_SESSION_EVOTER_REQUEST);
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
	 * Teacher change status of a session
	 * @param start
	 * @param params
	 * @param url
	 * @param questionActivity
	 */
	public static void changeSessionStatus(final boolean start, long sessionID, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionDAO.ID, String.valueOf(sessionID));
		String url = start ? URIRequest.ACTIVE_SESSION : URIRequest.INACTIVE_SESSION;
		client.post(EVoterRequestConfig.getURL(url), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response + (start ? EVoterMainMenu.MN_STOP_SESSION : EVoterMainMenu.MN_START_SESSION), CallBackMessage.CHANGE_SESSION_STATUS_EVOTER_REQUEST);
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
	 * Student send feedback about current session
	 * @param params
	 * @param questionActivity
	 */
	public static void submitStatisticValue(RequestParams params, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(EVoterRequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.SUBMIT_STATISTIC_EVOTER_REQUEST);
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
	 * User delete a question
	 * @param id
	 * @param questionActivity
	 */
	public static void deleteQuestion(long id, final QuestionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		client.post(EVoterRequestConfig.getURL(URIRequest.DELETE_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.DELETE_QUESTION_EVOTER_REQUEST);
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
	 * Teacher stop receive answer for a question
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void stopReceiveAnswer(long sessionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionID));
		client.post(EVoterRequestConfig.getURL(URIRequest.STOP_SEND_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.STOP_RECEIVE_ANSWER_EVOTER_REQUEST);
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
	 * Update status of a question
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void updateQuestionStatus(long questionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(questionID));
		client.post(EVoterRequestConfig.getURL(URIRequest.VIEW_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.CHECK_QUESTION_STATUS_EVOTER_REQUEST);
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
	 * Update status of current session
	 * @param id
	 * @param questionDetailActivity
	 */
	public static void checkSessionStatus(long sessionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.ID,
				String.valueOf(sessionID));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						context.updateRequestCallBack(response, CallBackMessage.CHECK_SESSION_STATUS_EVOTER_REQUEST);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("View session", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * Teacher send a question to student
	 * <br>Teacher cannot send two question at the same time
	 * @param id
	 * @param id2
	 * @param questionDetailActivity
	 */
	public static void sendQuestion(long questionID, long sessionID, final QuestionDetailActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(questionID));
		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionID));
		client.post(EVoterRequestConfig.getURL(URIRequest.SEND_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.SEND_QUESTION_EVOTER_REQUEST);
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
	 * User delete a session
	 * <br> Session will be deleted on account of user, in other account still have session
	 * @param id
	 * @param sessionActivity
	 */
	public static void deleteSession(long sessionID, final SessionActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(sessionID));
		client.post(EVoterRequestConfig.getURL(URIRequest.DELETE_SESSION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				context.updateRequestCallBack(response, CallBackMessage.DELETE_SESSION_EVOTER_REQUEST);
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
	 * User logout
	 * @param eVoterActivity
	 */
	public static void logout(final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(EVoterRequestConfig.getURL(URIRequest.LOGOUT), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.i("Response", response);
						if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
							EVoterMobileUtils.showeVoterToast(context, "Goodbye...!");
						} else {
							EVoterMobileUtils.showeVoterToast(context, "You are not logged out from system!");
						}
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								context,
								"Cannot request logout from server!");
						Log.e("Logout", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
}
