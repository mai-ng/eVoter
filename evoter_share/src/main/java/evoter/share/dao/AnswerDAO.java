package evoter.share.dao;

import java.util.List;

import evoter.share.model.Answer;

/**
 * This is an interface that maps fields of ANSWER table in database </br>
 * It is responsible for insert, update, delete and search {@link Answer} object in database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface AnswerDAO {
	
	public static final String ID = "ID";
	public static final String ANSWER_TEXT = "ANSWER_TEXT";
	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String STATISTICS = "STATISTICS";
	public static final String TABLE_NAME = "ANSWER";
	public static final String BEAN_NAME = "answerDAO";
	
	/**
	 * Add a {@link Answer} to database and return the generated id of new record </br>
	 * 
	 * @param answer {@link Answer} object 
	 * @return the id of insert record </br>
	 */
	public long insert (Answer answer);
	/**
	 * 
	 * @return List of {@link Answer} existing in the databse </br>
	 */
	public List<Answer> findAll();
	/**
	 * Search {@link Answer} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Answer} </br>
	 */
	public List<Answer> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Return an {@link Answer} that has id matches input id </br>
	 * @param id of searched {@link Answer} </br>
	 * @return List of {@link Answer} </br>
	 */
	public List<Answer> findById(long id);
	/**
	 * Return a {@link List} of {@link Answer} that have answer text matching input value </br>
	 * 
	 * @param answerText
	 * @return  {@link List} of {@link Answer} </br>
	 */
	public List<Answer> findByAnswerText(String answerText);
	/**
	 * Return a {@link List} of {@link Answer} that have question id matching input value </br>
	 * 
	 * @param questionId id of question of searched {@link Answer} </br>
	 * @return a {@link List} of {@link Answer} </br>
	 */
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
	/**
	 * 
	 * Delete {@link Answer} in database that has the same id with input value </br>
	 * @param id {@link Answer} id </br>
	 */
	public void deleteById(long id);
	/**
	 * Delete all {@link Answer} object in the database that has question id matching the input value </br>
	 * @param questionId id of question of answer </br>
	 */
	public void deleteByQuestionId(long questionId);
	/**
	 * Delete all {@link Answer} objects in the database that has answer text matching the input value </br>
	 * @param answerText {@link Answer} content </br>
	 */
	public void deleteByAnswerText(String answerText);
	/**
	 * Update the changes of {@link Answer} to the database </br>
	 * @param answer {@link Answer}
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Answer answer);


}
