package evoter.share.dao;

import java.util.List;

import evoter.share.model.Question;
import evoter.share.model.QuestionSession;
import evoter.share.model.Session;
/**
 * This is an interface that maps fields of QUESTION_SESSION table in database </br>
 * It is responsible for insert, update, delete and search {@link QuestionSession} object in database </br>
 *
 * @author btdiem
 *
 */
public interface QuestionSessionDAO {

	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String SESSION_ID = "SESSION_ID";
	public static final String TABLE_NAME = "SESSION_QUESTION";
	public static final String BEAN_NAME = "questionSessionDAO";
	
	/**
	 * @param questionSession value of {@link QuestionSession} </br>
	 * @return positive integer if {@link QuestionSession} is added successfully  </br>
	 * @return negative integer value if {@link QuestionSession} is added failure </br>
	 */
	public int insert (QuestionSession questionSession);
	/**
	 * @return all {@link QuestionSession} in the database </br> 
	 */
	public List<QuestionSession> findAll();
	/**
	 * Search {@link QuestionSession} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link QuestionSession} </br>
	 */
	public List<QuestionSession> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * 
	 * @param questionId value of {@link Question} id </br>
	 * @return {@link List} of {@link QuestionSession} that have {@link Question} id matching the input value </br>
	 */
	public List<QuestionSession> findByQuestionId(String questionId);
	/**
	 * @param sessionId value of {@link Session} id </br>
	 * @return List of {@link QuestionSession} that has {@link Session} id matching with the input value</br>
	 */
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
	/**
	 * Remove {@link QuestionSession} out the database that has {@link Question} id matching the input value </br>
	 * @param questionId value of {@link Question} id </br>
	 */
	public void deleteByQuestionId(long questionId);
	/**
	 * Remove {@link QuestionSession} out the database that has {@link Question} id matching the input value </br>
	 * @param sessionId value 
	 */
	public void deleteBySessionId(long sessionId);
	
}
