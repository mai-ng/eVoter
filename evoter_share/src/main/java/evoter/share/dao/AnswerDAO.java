package evoter.share.dao;

import java.util.List;

import evoter.share.model.Answer;

/**
 * This is an interface that maps properties to fields of ANSWER table in database </br>
 * It is responsible for insert, update, delete and search {@link Answer} object in database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface AnswerDAO {
	//ID  column 
	public static final String ID = "ID";
	//answer_text column
	public static final String ANSWER_TEXT = "ANSWER_TEXT";
	//question_id column
	public static final String QUESTION_ID = "QUESTION_ID";
	//statistics columns
	public static final String STATISTICS = "STATISTICS";
	//answer table name
	public static final String TABLE_NAME = "ANSWER";
	//spring bean name
	public static final String BEAN_NAME = "answerDAO";
	
	/**
	 * Add a {@link Answer} to database and return the generated id of new record </br>
	 * 
	 * @param answer {@link Answer} object 
	 * @return the id of insert record </br>
	 */
	public long insert (Answer answer);
	/**
	 * Select all {@link Answer} in the databse
	 * @return List of {@link Answer} if answer list can be found</br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<Answer> findAll();
	/**
	 * Search {@link Answer} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Answer} if answer list can be found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Answer> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Select {@link Answer} from the given id </br>
	 * @param id given answer id </br>
	 * @return List of {@link Answer} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Answer> findById(long id);
	/**
	 * Select {@link Answer} from the given answer text </br>
	 * Return a {@link List} of {@link Answer} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 * 
	 * @param answerText the searched answer text </br>
	 */
	public List<Answer> findByAnswerText(String answerText);
	/**
	 * 
	 * Select {@link Answer} from the given questionId </br>
	 * Return a {@link List} of {@link Answer} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 * 
	 * @param questionId id of {@link Answer} list searcher </br>

	 */
	public List<Answer> findByQuestionId(long questionId);
	/**
	 * 
	 * Delete {@link Answer} by the conditions of properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * 
	 * Delete {@link Answer} having the same the given id </br>
	 * @param id answer id should be deleted </br>
	 */
	public void deleteById(long id);
	/**
	 * Delete all {@link Answer} of given question id </br>
	 * @param questionId id of answer should be deleted </br>
	 */
	public void deleteByQuestionId(long questionId);
	/**
	 * Delete all {@link Answer} having answer_text matching the given answer text </br>
	 * @param answerText  </br>
	 */
	public void deleteByAnswerText(String answerText);
	/**
	 * Update the changes of {@link Answer} to the database </br>
	 * @param answer {@link Answer}
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Answer answer);


}
