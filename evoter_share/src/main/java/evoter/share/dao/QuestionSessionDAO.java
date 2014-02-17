package evoter.share.dao;

import java.util.List;


import evoter.share.model.QuestionSession;

/**
 * This is an interface that maps properties in this file to fields of QUESTION_SESSION table in database </br>
 * It is responsible for insert, update, delete and search {@link QuestionSession} object in database </br>
 *
 * @author btdiem </br>
 *
 */
public interface QuestionSessionDAO {
	//question_id column
	public static final String QUESTION_ID = "QUESTION_ID";
	//session_id column
	public static final String SESSION_ID = "SESSION_ID";
	//table name
	public static final String TABLE_NAME = "SESSION_QUESTION";
	//spring bean
	public static final String BEAN_NAME = "questionSessionDAO";
	
	/**
	 * Insert an new {@link QuestionSession} to database </br>
	 * @param questionSession contains new values created </br>
	 * @return positive integer if {@link QuestionSession} is added successfully  </br>
	 * @return negative integer value if {@link QuestionSession} is added failure </br>
	 */
	public int insert (QuestionSession questionSession);
	/**
	 *Select all {@link QuestionSession} in database </br>
	 * @return List of  {@link QuestionSession} if there are records returned </br>
	 * Otherwise, returning an empty list </br> 
	 */
	public List<QuestionSession> findAll();
	/**
	 * Search {@link QuestionSession} in the database matching input values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * 
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * 
	 * @return List of  {@link QuestionSession} if there are records returned </br>
	 * Otherwise, returning an empty list </br> 
	 */
	public List<QuestionSession> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Search {@link QuestionSession} in the database matching in the given questionId </br> 
	 * @param questionId value of questionId of {@link QuestionSession}</br>
	 * 
	 * @return List of  {@link QuestionSession} if there are records returned </br>
	 * Otherwise, returning an empty list </br> 
	 */
	public List<QuestionSession> findByQuestionId(String questionId);
	/**
	 * 
	 * Search {@link QuestionSession} in the database matching in the given sessionId </br> 
	 * @param sessionId value of questionId of {@link QuestionSession}</br>
	 * 
	 * @return List of  {@link QuestionSession} if there are records returned </br>
	 * Otherwise, returning an empty list </br> 
	 */
	public List<QuestionSession> findBySessionId(long sessionId);
	/**
	 * 
	 * Delete {@link QuestionSession} in the database matching input values </br> 

	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * Delete {@link QuestionSession} int the database matching the given questionId</br>
	 * @param questionId questionId value of {@link QuestionSession} </br>
	 */
	public void deleteByQuestionId(long questionId);
	/**
	 * Delete {@link QuestionSession} int the database matching the given sessionId</br>
	 * @param sessionId sessionId value of {@link QuestionSession} </br>
	 */
	public void deleteBySessionId(long sessionId);
	
}
