package evoter.mobile.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Update by @author luongnv89 on Sun - 09-Feb-2014: <br>
 * <li>Change java.sql.Date to using java.sql.Timestamp <br>
 * Created by luongnv89 on 05/12/13.
 */
public class EVoterMobileUtils {
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static JSONArray getJSONArray(String response) throws JSONException {
		return new JSONArray(response);
	}
	
	public static Timestamp convertToDate(String date) throws ParseException {
		java.util.Date utilDate = dateFormat.parse(date);
		return new Timestamp(utilDate.getTime());
	}
	
	public static String convertToString(Timestamp creationDate) {
		return dateFormat.format(creationDate);
	}
	
	/**
	 * Check internet connection
	 * 
	 * @param context
	 * @return true if the mobile phone has internet connection <br>
	 *         false otherwise
	 */
	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
	
	/**
	 * Show a message
	 * 
	 * @param context
	 * @param message
	 */
	public static void showeVoterToast(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
		t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		t.show();
	}
	
	public static void updateCurrentQuestion() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						try {
							JSONArray array = new JSONArray(response);
							Question question = parserToQuestion(array.getJSONObject(0));
							if(question!=null) EVoterShareMemory.setCurrentQuestion(question);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * @param response
	 */
	public static Question parserToQuestion(JSONObject s) {
		String answerColumn1 = "null";
		String answerColumn2 = "null";
		try {
			if (s.toString().contains(Question.COL1)) {
				answerColumn1 = s
						.getString(Question.COL1);
			}
			if (s.toString().contains(Question.COL2)) {
				answerColumn2 = s
						.getString(Question.COL2);
			}
			Question question = new Question(
					Long.parseLong(s
							.getString(QuestionDAO.ID)),
					s.getString(QuestionDAO.QUESTION_TEXT),
					Long.parseLong(s
							.getString(QuestionDAO.QUESTION_TYPE_ID)),
					Long.parseLong(s
							.getString(QuestionDAO.USER_ID)),
					EVoterMobileUtils.convertToDate(s
							.getString(QuestionDAO.CREATION_DATE)),
					Long.parseLong(s
							.getString(QuestionSessionDAO.SESSION_ID)),
					Long.parseLong(s
							.getString(QuestionDAO.PARENT_ID)),
					answerColumn1, answerColumn2);
			question.setStatus(s.getInt(QuestionDAO.STATUS));
			return question;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void updateCurrentSession() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add(SessionDAO.ID,
				String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.VIEW_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("View session", response);
						JSONArray array;
						try {
							array = EVoterMobileUtils.getJSONArray(response);
							Session session = parserSession(array.getJSONObject(0));
							if(session!=null) EVoterShareMemory.setCurrentSession(session);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("View session", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param response
	 */
	public static Session parserSession(JSONObject s) {
		try {
			
			long sessionID = Long.parseLong(s
					.getString(SessionDAO.ID));
			String creator;
			if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
				creator = EVoterShareMemory.getCurrentUserName();
			} else {
				creator = s.getString("CREATOR");
				boolean isAccepted = s.getBoolean(SessionUserDAO.ACCEPT_SESSION);
				if (isAccepted) EVoterShareMemory.addToListAcceptedSessions(sessionID);
			}
			Session session = new Session(sessionID, Long.parseLong(s
					.getString(SessionDAO.SUBJECT_ID)), s
					.getString(SessionDAO.NAME), EVoterMobileUtils
					.convertToDate(s
							.getString(SessionDAO.CREATION_DATE)),
					Boolean.parseBoolean(s
							.getString(SessionDAO.IS_ACTIVE)), Long.parseLong(s.getString(SessionDAO.USER_ID)), creator);
			return session;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
