package evoter.share.dao;

import java.sql.Timestamp;
import java.util.List;

import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;

/**
 * This is an interface that maps properties in this class to fields of QUESTION table in database </br>
 * It is responsible for insert, update, delete and search {@link Question} object in database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface QuestionDAO {
	//id column
	public static final String ID = "ID";
	//question_text column
	public static final String QUESTION_TEXT = "QUESTION_TEXT";
	//question_type_id column
	public static final String QUESTION_TYPE_ID = "QUESTION_TYPE_ID";
	//creation_date column
	public static final String CREATION_DATE = "CREATION_DATE";
	//user_id column
	public static final String USER_ID = "USER_ID";
	//parent_id column
	public static final String PARENT_ID = "PARENT_ID";
	//status column
	public static final String STATUS = "STATUS";
	//table name
	public static final String TABLE_NAME = "QUESTION";
	//spring bean name
	public static final String BEAN_NAME = "questionDAO";
	
	/**
	 * Add new {@link Question} to the database and return the generated key of new record </br>
	 * 
	 * @param question  inserted Question </br>
	 * @return the generated key of new record </br>
	 */
	public long insert (Question question);
	/**
	 * Select all {@link Question} in the database
	 * @return List of {@link Question} if question list can be found</br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<Question> findAll();
	/**
	 * Search {@link Question} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Question} if answer list can be found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Select {@link Question} from the given id </br>
	 * @param id given answer id </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findById(long id);
	/**
	 * Select {@link Question} from the given user id </br>
	 * @param user id user id who creates question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findByUserId(long userId);
	/**
	 * Select {@link Question} from the given questionText </br>
	 * @param questionText content of question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findByQuestionText(String questionText);
	/**
	 * Select {@link Question} from the given question type id </br>
	 * @param questionTypeId type value of question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findByQuestionTypeId(long questionTypeId);
	/**
	 * Select {@link Question} from the creation date </br>
	 * @param creationDate the date creating question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */	
	public List<Question> findByCreationDate(Timestamp creationDate);
	/**
	 * Select {@link Question} from the given parent id </br>
	 * @param parentId parent id value of question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */	
	public List<Question> findByParentId(long parentId);
	/**
	 * Select {@link Question} from the status</br>
	 * @param status status value of a question </br>
	 * @return List of {@link Question} if there is an records found</br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Question> findByStatus(int status);
	/**
	 * 
	 * Delete {@link Question} by the conditions of properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * 
	 * Delete {@link Question} having the same the given id </br>
	 * @param id question id should be deleted </br>
	 */
	public void deleteById(long id);
	/**
	 * 
	 * Delete {@link Question} having the same the given user id </br>
	 * @param userId user id of user who creates question</br>
	 */

	public void deleteByUserId(long userId);
	/**
	 * 
	 * Delete {@link Question} having the same the given creation date </br>
	 * @param creationDate time creating question</br>
	 */
	public void deleteByCreationDate(Timestamp creationDate);
	/**
	 * 
	 * Delete {@link Question} having the same the given questionText</br>
	 * @param questionText question content</br>
	 */
	public void deleteByQuestionText(String questionText);
	/**
	 * 
	 * Delete {@link Question} having the same the given question type id </br>
	 * @param questionTypeId user id of user who creates question</br>
	 */
	public void deleteByQuestionTypeId(long questionTypeId);
	/**
	 * 
	 * Delete {@link Question} having the same the given parent id </br>
	 * @param parentId parent id value of question</br>
	 */
	public void deleteByParentId(long parentId);
	/**
	 * 
	 * Delete {@link Question} having the same the given status</br>
	 * @param status question status value</br>
	 */	
	public void deleteByStatus(int status);
	/**
	 * Update the changes values of {@link Question} to the database </br>
	 * @param question contains the changed value needed to be updated </br>
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Question question);

}
