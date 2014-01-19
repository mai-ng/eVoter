package evoter.server.dao.interfaces;

import java.util.List;

import evoter.server.model.QuestionSession;

public interface QuestionSessionDAO {

	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String SESSION_ID = "SESSION_ID";
	public static final String TABLE_NAME = "SESSION_QUESTION";
	public static final String BEAN_NAME = "questionSessionDAO";
	
	public int insert (QuestionSession questionSession);
	public List<QuestionSession> findAll();
	/**
	 * Search {@link QuestionSession} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link QuestionSession} </br>
	 */
	public List<QuestionSession> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<QuestionSession> findByQuestionId(String questionId);
	public List<QuestionSession> findBySessionId(long sessionId);
	/**
	 * 
	 * Delete {@link QuestionSession} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link QuestionSession} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteByQuestionId(long questionId);
	public void deleteBySessionId(long sessionId);
	
}
