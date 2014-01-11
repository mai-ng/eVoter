package evoter.server.dao;

import java.util.List;

import evoter.server.model.Answer;


public interface AnswerDAO {
	
	public static final String ID = "ID";
	public static final String ANSWER_TEXT = "ANSWER_TEXT";
	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String TABLE_NAME = "ANSWER";
	public static final String BEAN_NAME = "answerDAO";
	
	/**
	 * 
	 * @param answer
	 * @return the id of insert record </br>
	 */
	public long insert (Answer answer);
	public List<Answer> findAll();
	/**
	 * Search {@link Answer} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Answer} </br>
	 */
	public List<Answer> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<Answer> findById(long id);
	public List<Answer> findByAnswerText(String answerText);
	public List<Answer> findByQuestionId(long questionId);
	/**
	 * 
	 * Delete {@link Answer} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Answer} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteById(long id);
	public void deleteByQuestionId(long questionId);
	public void deleteByAnswerText(String answerText);


}
