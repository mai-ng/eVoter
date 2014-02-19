/**
 * 
 */
package evoter.mobile.main;

import java.util.ArrayList;

import evoter.mobile.abstracts.EVoterActivity;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.Subject;

/**
 * {@link EVoterShareMemory} manage the running data of application
 * 
 * @author luongnv89
 */
public class EVoterShareMemory {
	
	/**
	 * List ID of question which student has submit answer
	 */
	private static ArrayList<Long> listIDAnsweredQuestion = new ArrayList<Long>();
	
	/**
	 * {@link OfflineEVoterManager}
	 */
	private static OfflineEVoterManager offlineEVoterManager;
	
	/**
	 * User_key of user to authenticate all request from user to server.
	 * <br> It is diffirent string for each login time
	 */
	private static String USER_KEY;
	
	/**
	 * Current question 
	 */
	private static Question currentQuestion;
	/**
	 * excited question of current session
	 */
	private static Question exictedQuestion;
	/**
	 * difficult question of current session
	 */
	private static Question difficultQuestion;
	
	/**
	 * Current session
	 */
	private static Session currentSession;
	
	/**
	 * Current subject
	 */
	private static Subject currentSubject;
	
	/**
	 * Current username
	 */
	private static String currentUserName;
	
	/**
	 * List all session which student has accepted to join
	 */
	private static ArrayList<Long> listAcceptedSessions = new ArrayList<Long>();
	
	/**
	 * List all session which is running
	 */
	private static ArrayList<Long> listActiveSessions = new ArrayList<Long>(); 
	
	/**
	 * Previous context of current activity
	 */
	private static EVoterActivity previousContext;
	
	/**
	 * List all question of session
	 */
	private static ArrayList<Question> listQuestions = new ArrayList<Question>();
	
	/**
	 * Add a question to list question
	 * @param question
	 */
	public static void addQuestionToList(Question question) {
		listQuestions.add(question);
	}
	
	/**
	 * @return the listActiveSessions
	 */
	public static ArrayList<Long> getListActiveSessions() {
		return listActiveSessions;
	}


	/**
	 * @param listActiveSessions the listActiveSessions to set
	 */
	public static void addToListActiveSessions(long sessionID) {
		EVoterShareMemory.listActiveSessions.add(sessionID);
	}

	public static boolean isCurrentSessionActive(){
		return getListActiveSessions().contains(getCurrentSession().getId());
	}


	/**
	 * @return the listQuestions
	 */
	public static ArrayList<Question> getListQuestions() {
		return listQuestions;
	}
	
	/**
	 * @param listQuestions the listQuestions to set
	 */
	public static void setListQuestions(ArrayList<Question> listQuestions) {
		EVoterShareMemory.listQuestions = listQuestions;
	}
	
	/**
	 * @return the exictedQuestion
	 */
	public static Question getExictedQuestion() {
		return exictedQuestion;
	}
	
	/**
	 * @param exictedQuestion the exictedQuestion to set
	 */
	public static void setExictedQuestion(Question exictedQuestion) {
		EVoterShareMemory.exictedQuestion = exictedQuestion;
	}
	
	/**
	 * @return the difficultQuestion
	 */
	public static Question getDifficultQuestion() {
		return difficultQuestion;
	}
	
	/**
	 * @param difficultQuestion the difficultQuestion to set
	 */
	public static void setDifficultQuestion(Question difficultQuestion) {
		EVoterShareMemory.difficultQuestion = difficultQuestion;
	}
	
	/**
	 * @return the listAnsweredQuestion
	 */
	public static ArrayList<Long> getListIDAnsweredQuestion() {
		return listIDAnsweredQuestion;
	}
	
	/**
	 * @param listAnsweredQuestion the listAnsweredQuestion to set
	 */
	public static void addAnsweredQuestion(long questionID) {
		EVoterShareMemory.listIDAnsweredQuestion.add(questionID);
	}
	
	/**
	 * @return the offlineEVoterManager
	 */
	public static OfflineEVoterManager getOfflineEVoterManager() {
		return offlineEVoterManager;
	}
	
	/**
	 * @param offlineEVoterManager the offlineEVoterManager to set
	 */
	public static void setOfflineEVoterManager(OfflineEVoterManager offlineEVoterManager) {
		EVoterShareMemory.offlineEVoterManager = offlineEVoterManager;
	}
	
	/**
	 * @return the previousContext
	 */
	public static EVoterActivity getPreviousContext() {
		return previousContext;
	}
	
	/**
	 * @param previousContext the previousContext to set
	 */
	public static void setPreviousContext(EVoterActivity previousContext) {
		EVoterShareMemory.previousContext = previousContext;
	}
	
	/**
	 * @return the listAcceptedSessions
	 */
	public static ArrayList<Long> getListAcceptedSessions() {
		return listAcceptedSessions;
	}
	
	/**
	 * @param listAcceptedSessions the listAcceptedSessions to set
	 */
	public static void addToListAcceptedSessions(long sessionID) {
		EVoterShareMemory.listAcceptedSessions.add(sessionID);
	}
	
	public static void resetListAcceptedSessions() {
		listAcceptedSessions.clear();
	}
	
	/**
	 * @param currentUserName the currentUserName to set
	 */
	public static void setCurrentUserName(String currentUserName) {
		EVoterShareMemory.currentUserName = currentUserName;
	}
	
	/**
	 * @return the currentSubject
	 */
	public static Subject getCurrentSubject() {
		return currentSubject;
	}
	
	/**
	 * @param currentSubject the currentSubject to set
	 */
	public static void setCurrentSubject(Subject currentSubject) {
		EVoterShareMemory.currentSubject = currentSubject;
	}
	
	/**
	 * @return the currentSession
	 */
	public static Session getCurrentSession() {
		return currentSession;
	}
	
	/**
	 * @param currentSession the currentSession to set
	 */
	public static void setCurrentSession(Session currentSession) {
		EVoterShareMemory.currentSession = currentSession;
	}
	
	/**
	 * @return the currentUserName
	 */
	public static String getCurrentUserName() {
		return currentUserName;
	}
	
	/**
	 * @return the uSER_KEY
	 */
	public static String getUSER_KEY() {
		return USER_KEY;
	}
	
	/**
	 * @param uSER_KEY
	 *            the uSER_KEY to set
	 */
	public static void setUSER_KEY(String uSER_KEY) {
		USER_KEY = uSER_KEY;
	}
	
	/**
	 * @return the currentSubjectName
	 */
	public static String getCurrentSubjectName() {
		return currentSubject.getTitle();
	}
	
	/**
	 * @return the currentSessionName
	 */
	public static String getCurrentSessionName() {
		return currentSession.getTitle();
	}
	
	/**
	 * @return the currentSessionID
	 */
	public static long getCurrentSessionID() {
		return currentSession.getId();
	}
	
	/**
	 * @return the currentSessionStatus
	 */
	public static boolean currentSessionIsActive() {
		return currentSession.isActive();
	}
	
	/**
	 * @return the currentSubjectID
	 */
	public static long getCurrentSubjectID() {
		return currentSubject.getId();
	}
	
	/**
	 * @return the currentQuestion
	 */
	public static Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	/**
	 * @param currentQuestion the currentQuestion to set
	 */
	public static void setCurrentQuestion(Question auestion) {
		currentQuestion = auestion;
	}
	
	/**
	 * Extract user type from user_key
	 * 
	 * @return the currentUserType
	 */
	public static long getCurrentUserType() {
		if (getUSER_KEY() == null || getUSER_KEY().equals("")) return 0;
		String[] array = getUSER_KEY().split("_");
		return Long.parseLong(array[array.length - 1]);
	}
	
	/**
	 * Check if there is static bar (excited and difficult bar)
	 * @return
	 */
	public static boolean hasStaticBar() {
		return EVoterShareMemory.getExictedQuestion() != null && EVoterShareMemory.getDifficultQuestion() != null;
	}
	
	/**
	 * Check the current user is joined in current session
	 * @return
	 */
	public static boolean userJoinedSession() {
		return getListAcceptedSessions().contains(EVoterShareMemory.getCurrentSession().getId());
	}
	
}
