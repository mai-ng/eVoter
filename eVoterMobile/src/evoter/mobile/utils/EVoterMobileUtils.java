package evoter.mobile.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import evoter.mobile.main.EVoterShareMemory;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.ItemData;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
import evoter.share.model.Session;
import evoter.share.model.Subject;
import evoter.share.model.UserType;

/**
 * <br>
 * Update by @author luongnv89 on Sun - 09-Feb-2014: <br>
 * <li>Change java.sql.Date to using java.sql.Timestamp <br>
 * Created by luongnv89 on 05/12/13.
 */
public class EVoterMobileUtils {
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * Convert a string which present a date to {@link Timestamp}
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp convertToDate(String date) throws ParseException {
		java.util.Date utilDate = dateFormat.parse(date);
		return new Timestamp(utilDate.getTime());
	}
	
	/**
	 * Convert a {@link Timestamp} to a {@link String}
	 * @param creationDate
	 * @return
	 */
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
	 * Show a message on activity
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
	 * Parser a String to list of {@link Question}
	 * @param response
	 * @return empty list if there is any exception
	 * <br> an {@link ArrayList} of {@link Question}
	 */
	public static ArrayList<ItemData> parserQuestionArray(String response) {
		ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
		try {
			JSONArray array = new JSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				Question question = EVoterMobileUtils.parserToQuestion(array.getJSONObject(i), EVoterShareMemory.getCurrentSession().getId());
				if (question != null) {
					if (question.getTitle().equals(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST) || question.getTitle().equals(CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST)) {
						setStaticAnswerID(question);
					} else {
						//With student, only load the question which already sent or finished.
						if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER)
						{
							listQuestion.add(question);
							EVoterShareMemory.addQuestionToList(question);
						} else {
							if (question.getStatus() != 0) {
								listQuestion.add(question);
							}
						}
						
					}
				}
			}
			return listQuestion;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listQuestion;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return listQuestion;
		}
	}
	
	/**
	 * Parser a {@link JSONObject} to a {@link Question} in session has sessionID
	 * @param s
	 * @param sessionID
	 * @return null if there is any exception
	 * <br> a {@link Question}
	 */
	public static Question parserToQuestion(JSONObject s, long sessionID) {
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
			
			if (s.toString().contains(QuestionSessionDAO.SESSION_ID)) {
				sessionID = s.getLong(QuestionSessionDAO.SESSION_ID);
			}
			Question question = new Question(
					s.getLong(QuestionDAO.ID),
					s.getString(QuestionDAO.QUESTION_TEXT),
					s.getLong(QuestionDAO.QUESTION_TYPE_ID),
					s.getLong(QuestionDAO.USER_ID),
					EVoterMobileUtils.convertToDate(s
							.getString(QuestionDAO.CREATION_DATE)),
					sessionID,
					s.getLong(QuestionDAO.PARENT_ID),
					answerColumn1, answerColumn2);
			question.setStatus(s.getInt(QuestionDAO.STATUS));
			return question;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * Parser a {@link JSONObject} to an {@link Answer} of question questionID
	 * @param jsonObject
	 * @param questionID
	 * @return null if there is any exception
	 * <br> an {@link Answer}
	 */
	public static Answer parserToAnswer(JSONObject jsonObject, long questionID) {
		try {
			return new Answer(jsonObject.getLong(AnswerDAO.ID), questionID, jsonObject.getString(AnswerDAO.ANSWER_TEXT));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Parser a {@link String} to {@link ArrayList} of {@link Answer} of questionID
	 * @param answerColumn1
	 * @param questionID
	 * @return empty list if there is any exception 
	 * <br> an {@link ArrayList} of {@link Answer}
	 * 
	 */
	public static ArrayList<Answer> parserAnswerArray(String answerColumn1, long questionID) {
		Log.i("AnswerColumn", answerColumn1);
		ArrayList<Answer> listAnswers = new ArrayList<Answer>();
		
		JSONArray listAnswer1;
		try {
			listAnswer1 = new JSONArray(answerColumn1);
		} catch (JSONException e) {
			e.printStackTrace();
			listAnswer1 = null;
		}
		
		if (listAnswer1 != null) {
			for (int i = 0; i < listAnswer1.length(); i++) {
				JSONObject ob;
				try {
					ob = listAnswer1.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ob = null;
				}
				if (ob != null) {
					Answer answer = parserToAnswer(ob, questionID);
					if (answer != null) listAnswers.add(answer);
				}
			}
			return listAnswers;
		}
		return listAnswers;
	}
	

	
	/**
	 * Parser a {@link JSONObject} to a {@link Session}
	 * @param s
	 * @return null if there is any exception
	 * <br> a {@link Session}
	 */
	public static Session parserToSession(JSONObject s) {
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
			
			boolean isActive = Boolean.parseBoolean(s.getString(SessionDAO.IS_ACTIVE));
			Session session = new Session(sessionID, Long.parseLong(s
					.getString(SessionDAO.SUBJECT_ID)), s
					.getString(SessionDAO.NAME), EVoterMobileUtils
					.convertToDate(s
							.getString(SessionDAO.CREATION_DATE)),
							isActive, Long.parseLong(s.getString(SessionDAO.USER_ID)), creator);
			return session;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parser a {@link String} to {@link ArrayList} of {@link Session}
	 * @param response
	 * @return empty list if there is any exception
	 * <br> an {@link ArrayList} of {@link Session}
	 */
	public static ArrayList<ItemData> parserSessionArray(String response) {
		
		ArrayList<ItemData> listItems = new ArrayList<ItemData>();
		try {
			JSONArray array = new JSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				Session session = EVoterMobileUtils.parserToSession(array.getJSONObject(i));
				if (session != null)
					listItems.add(session);
			}
			return listItems;
		} catch (JSONException e) {
			e.printStackTrace();
			return listItems;
		}
	}
	
	/**
	 * Parser a {@link JSONObject} to a {@link Subject}
	 * @param item
	 * @return null if there is any exception
	 * <br> a {@link Subject}
	 */
	public static Subject parserToSubject(JSONObject item) {
		try {
			Subject s = new Subject(Long.parseLong(item.getString(SubjectDAO.ID)), item.getString(SubjectDAO.TITLE),
					EVoterMobileUtils.convertToDate(item.getString(SubjectDAO.CREATION_DATE)));
			return s;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * Parser a {@link String} to {@link ArrayList} of {@link Subject}
	 * @param response
	 * @return empty list if there is any exception
	 * <br> an {@link ArrayList} of {@link Subject}
	 */
	public static ArrayList<ItemData> parserSubjectArray(String response) {
		ArrayList<ItemData> listItems = new ArrayList<ItemData>();
		try {
			JSONArray array = new JSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				Subject subject = parserToSubject(item);
				if (subject != null)
					listItems.add(subject);
			}
			return listItems;
		} catch (JSONException e) {
			e.printStackTrace();
			return listItems;
			
		}
		
	}
	
	/**
	 * Parser a JSONObject to a string which present the information of an user
	 * 
	 * @param ob
	 * @return null if there is some exception or user type not
	 *         {@link UserType#TEACHER} or {@link UserType#STUDENT}
	 */
	public static String parserJSONToUser(JSONObject ob) {
		try {
			long userTypeID = ob.getLong(UserDAO.USER_TYPE_ID);
			String fullName = ob.getString(UserDAO.FULL_NAME);
			String email = ob.getString(UserDAO.EMAIL);
			if (userTypeID == UserType.STUDENT)
				return "STUDENT_" + fullName + ": " + email;
			else if (userTypeID == UserType.TEACHER)
				return "TEACHER_" + fullName + ": " + email;
			else
				return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Draw statistic of an answer by a {@link String}
	 * @param response response receive from server when request to get statistic
	 * @param question question to draw statistic
	 * @return a {@link String} present the statistic, content of a question.
	 * <br> an empty list if there is any exception
	 */
	public static ArrayList<String> drawStatistic(String response, Question question) {
		ArrayList<String> listDataRow = new ArrayList<String>();
		ArrayList<AnswerData> listAnswerDatas = new ArrayList<AnswerData>();
		try {
			JSONArray arrayStatistic = new JSONArray(response);
			listDataRow.add("QUESTION: ");
			listDataRow.add(question.getTitle() + "\n");
			listDataRow.add("ANSWERS: \n");
			ArrayList<Answer> listAnswers = parserAnswerArray(question.getAnswerColumn1(), question.getId());
			Log.i("List answer", listAnswers.toString());
			if (question.getQuestionTypeId() == QuestionType.INPUT_ANSWER) {
				JSONObject statisticObject = arrayStatistic.getJSONObject(0);
				String[] array = statisticObject.getString(AnswerDAO.STATISTICS).split(":");
				for (int i = 1; i < array.length; i++) {
					listDataRow.add(i + ": " + array[i]);
				}
			} else if (question.getQuestionTypeId() == QuestionType.SLIDER) {
				JSONObject statisticObject = arrayStatistic.getJSONObject(0);
				
				String[] array = statisticObject.getString(AnswerDAO.STATISTICS).split(":");
				ArrayList<AnswerData> listAnswerValue = new ArrayList<AnswerData>();
				for (int i = 1; i < array.length; i++) {
					int value = Integer.parseInt(array[i]);
					int index = getIndex(value, listAnswerValue);
					if (index == -1) {
						listAnswerValue.add(new AnswerData(value, 1));
					} else {
						listAnswerValue.get(index).setStatistic(listAnswerValue.get(index).getStatistic() + 1);
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
					if (statisticString == null || statisticString.equals("null"))
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
			return listDataRow;
		}
	}
	
	/**
	 * Find statistic of an answer in statistic response from server
	 * @param listAnswerDatas
	 * @return 0 if there is not any statistic for answer which has id
	 */
	private static int findStatistic(long id, ArrayList<AnswerData> listAnswerDatas) {
		for (int i = 0; i < listAnswerDatas.size(); i++) {
			if (listAnswerDatas.get(i).getId() == id) return listAnswerDatas.get(i).getStatistic();
		}
		return 0;
	}
	
	/**
	 * Get Index of an {@link AnswerData#id} in an {@link ArrayList} of {@link AnswerData}
	 * @param value
	 * @param listAnswerValue
	 * @return -1 if there is not any {@link AnswerData} has id = value
	 */
	private static int getIndex(int value, ArrayList<AnswerData> listAnswerValue) {
		for (int i = 0; i < listAnswerValue.size(); i++) {
			if (listAnswerValue.get(i).getId() == value) return i;
		}
		return -1;
	}
	
	
	/**
	 * Set staticAnswer ID for excited and difficult bar
	 * 
	 * @param question
	 */
	public static void setStaticAnswerID(Question question) {
		if (question.getTitle().contains(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST)) {
			if (EVoterShareMemory.getExictedQuestion() == null || question.getId() != EVoterShareMemory.getExictedQuestion().getId())
				EVoterShareMemory.setExictedQuestion(question);
		}
		if (question.getTitle().contains(CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST)) {
			if (EVoterShareMemory.getDifficultQuestion() == null || question.getId() != EVoterShareMemory.getDifficultQuestion().getId())
				EVoterShareMemory.setDifficultQuestion(question);
		}
	}
	
	/**
	 * Get id of seekbar
	 * 
	 * @param seekbar
	 * <br>
	 *            seekbar =
	 *            {@link CallBackMessage#EXCITED_BAR_STATISTIC_EVOTER_REQUEST}
	 *            return id of answer for Excited bar <br>
	 *            seekbar =
	 *            {@link CallBackMessage#DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST}
	 *            return id of answer for difficult bar
	 * @return id of seekbar
	 */
	public static long getstaticAnswerID(String seekbar) {
		if (seekbar.equals(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST))
			return getFirstAnswerID(EVoterShareMemory.getExictedQuestion());
		else
			return getFirstAnswerID(EVoterShareMemory.getDifficultQuestion());
	}
	
	/**
	 * Get the id of the first Answer of a question.
	 * 
	 * @param exictedQuestion
	 * @return the id of the first answer of question
	 */
	public static long getFirstAnswerID(Question exictedQuestion) {
		ArrayList<Answer> listAnswers = EVoterMobileUtils.parserAnswerArray(exictedQuestion.getAnswerColumn1(), exictedQuestion.getId());
		if(listAnswers.isEmpty()) return -1;
		else return listAnswers.get(0).getId();
	}
}
