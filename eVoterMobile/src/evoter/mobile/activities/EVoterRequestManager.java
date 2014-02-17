/**
 * 
 */
package evoter.mobile.activities;

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
import evoter.share.dao.UserDAO;
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
	public static void doVote(long answerID, long questionTypeID, String statistic, final EVoterActivity context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(questionTypeID));
		params.put(AnswerDAO.ID, String.valueOf(answerID));
		if (statistic != null)
			params.put(AnswerDAO.STATISTICS, statistic);
		client.post(RequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
					EVoterMobileUtils.showeVoterToast(context,
							"Successful!");
					context.updateRequestCallBack();
				}
				else {
					EVoterMobileUtils.showeVoterToast(context,
							"Fail: " + response);
				}
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
	public static void resetPassword(final String i_email,final Context context) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put(UserDAO.EMAIL, i_email);
		client.post(RequestConfig.getURL(URIRequest.RESET_PASSWORD), params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(String response) {
				Log.i("Reset password",response);
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
	
}
