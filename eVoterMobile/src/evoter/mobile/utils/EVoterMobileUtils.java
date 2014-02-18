package evoter.mobile.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
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
	
	/**
	 * @param response
	 */
	public static Question parserToQuestion(JSONObject s) {
		String answerColumn1 = "null";
		String answerColumn2 = "null";
		Log.i("Question object", s.toString());
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
	
	/**
	 * @param answerColumn1
	 * @return
	 */
	public static ArrayList<Answer> parserListAnswer(String answerColumn1, long questionID) {
		ArrayList<Answer> listAnswers = new ArrayList<Answer>();
		try {
			JSONArray listAnswer1 = new JSONArray(answerColumn1);
			for (int i = 0; i < listAnswer1.length(); i++) {
				Answer answer = parserJSONObjectToAnswer(listAnswer1.getJSONObject(i), questionID);
				if (answer != null) listAnswers.add(answer);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listAnswers;
	}
	
	/**
	 * @param jsonObject
	 * @return
	 */
	public static Answer parserJSONObjectToAnswer(JSONObject jsonObject, long questionID) {
		try {
			return new Answer(jsonObject.getLong(AnswerDAO.ID), questionID, jsonObject.getString(AnswerDAO.ANSWER_TEXT));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
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
	
	public static ArrayList<String> drawStatistic(String response, Question question) {
		ArrayList<AnswerData> listAnswerDatas = new ArrayList<AnswerData>();
		try {
			JSONArray arrayStatistic = new JSONArray(response);
			ArrayList<String> listDataRow = new ArrayList<String>();
			listDataRow.add("QUESTION: ");
			listDataRow.add(question.getTitle() + "\n");
			listDataRow.add("ANSWERS: \n");
			ArrayList<Answer> listAnswers = parserListAnswer(question.getAnswerColumn1(), question.getId());
			Log.i("List answer", listAnswers.toString());
			if (question.getQuestionTypeId() == QuestionType.INPUT_ANSWER) {
				JSONObject statisticObject =arrayStatistic.getJSONObject(0);
				String[] array = statisticObject.getString(AnswerDAO.STATISTICS).split(":");
				for (int i = 1; i < array.length; i++) {
					listDataRow.add(i+": "+array[i]);
				}
			} else if (question.getQuestionTypeId() == QuestionType.SLIDER) {
				JSONObject statisticObject =arrayStatistic.getJSONObject(0);
				
				String[] array = statisticObject.getString(AnswerDAO.STATISTICS).split(":");
				ArrayList<AnswerData> listAnswerValue = new ArrayList<AnswerData>();
				for (int i = 1; i < array.length; i++) {
					int value = Integer.parseInt(array[i]);
					int index = getIndex(value, listAnswerValue);
					if (index == -1) {
						listAnswerValue.add(new AnswerData(value, 1));
					} else {
						listAnswerValue.get(index).setStatistic(listAnswerValue.get(index).getStatistic()+ 1);
					}
				}
				
				for (int i = 0; i < listAnswerValue.size(); i++) {
					listDataRow.add(listAnswerValue.get(i).getId() + ": " + listAnswerValue.get(i).getStatistic() + "\n");
				}
			} else {
				for (int i = 0; i < arrayStatistic.length(); i++) {
					JSONObject ans = arrayStatistic.getJSONObject(i);
					Log.i("Statistic object", ans.toString());
					long id = ans.getLong(AnswerDAO.ID);
					String statisticString = ans.getString(AnswerDAO.STATISTICS);
					Log.i("Statistic String", statisticString);
					if(statisticString==null||statisticString.equals("null"))
						listAnswerDatas.add(new AnswerData(id, 0));
					else
					{
						int statistic = Integer.parseInt(statisticString);
						listAnswerDatas.add(new AnswerData(id, statistic));
					}
					
				}
				for (int i = 0; i < listAnswers.size(); i++) {
					long id = listAnswers.get(i).getId();
					int statistic = findStatistic(id, listAnswerDatas);
					listDataRow.add(listAnswers.get(i).getAnswerText() + ": " + statistic);
				}
			}
			return listDataRow;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param listAnswerDatas
	 * @return
	 */
	private static int findStatistic(long id, ArrayList<AnswerData> listAnswerDatas) {
		for (int i = 0; i < listAnswerDatas.size(); i++) {
			if (listAnswerDatas.get(i).getId() == id) return listAnswerDatas.get(i).getStatistic();
		}
		return 0;
	}
	
	/**
	 * @param value
	 * @param listAnswerValue
	 * @return
	 */
	private static int getIndex(int value, ArrayList<AnswerData> listAnswerValue) {
		for (int i = 0; i < listAnswerValue.size(); i++) {
			if (listAnswerValue.get(i).getId() == value) return i;
		}
		return -1;
	}
	
	/**
	 * @param title
	 * @return
	 */
	private static TextView createTextView(String title, Context context) {
		TextView tv = new TextView(context);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setText(title);
		return null;
	}
}
