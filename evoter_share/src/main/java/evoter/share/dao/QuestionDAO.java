package evoter.share.dao;

import java.sql.Timestamp;
import java.util.List;

import evoter.share.model.Question;
import evoter.share.model.QuestionType;

/**
 * This is an interface that maps fields of QUESTION table in database </br>
 * It is responsible for insert, update, delete and search {@link Question} object in database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface QuestionDAO {
	
	public static final String ID = "ID";
	public static final String QUESTION_TEXT = "QUESTION_TEXT";
//	public static final String SESSION_ID = "SESSION_ID";
	public static final String QUESTION_TYPE_ID = "QUESTION_TYPE_ID";
	public static final String CREATION_DATE = "CREATION_DATE";
	public static final String USER_ID = "USER_ID";
	public static final String PARENT_ID = "PARENT_ID";
	public static final String STATUS = "STATUS";
	
	public static final String TABLE_NAME = "QUESTION";	
	public static final String BEAN_NAME = "questionDAO";
	
	/**
	 * Add new {@link Question} to the database and return the generated key of new record </br>
	 * 
	 * @param question  inserted {@link Question} </br>
	 * @return the generated key of inserted record </br>
	 */
	public long insert (Question question);
	/**
	 * Return a {@link List} of {@link Question} in the database </br>
	 * @return List of {@link Question} </br>
	 */
	public List<Question> findAll();
	/**
	 * Search {@link Question} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public List<Question> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Return a {@link Question} that has the same id with input value </br>
	 * @param id searched question id </br>
	 * @return a {@link List} of {@link Question} </br>
	 */
	public List<Question> findById(long id);
	/**
	 * Return a List of {@link Question} that have the user id the same with input value </br>
	 * @param userId  id of user creating {@link Question} </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public List<Question> findByUserId(long userId);
	/**
	 * Return a {@link List} of {@link Question} that have question text matching the input value </br>
	 * @param questionText the content of {@link Question} </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public List<Question> findByQuestionText(String questionText);
	/**
	 * Return a {@link List} of {@link Question} that have question type id matching the input value </br>
	 * @param questionTypeId id of {@link QuestionType} </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public List<Question> findByQuestionTypeId(long questionTypeId);
	public List<Question> findByCreationDate(Timestamp creationDate);
	public List<Question> findByParentId(long parentId);
	public List<Question> findByStatus(int status);
	/**
	 * 
	 * Delete {@link Question} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * Remove {@link Question} out the database that has the id value matching the input value </br>
	 * @param id value of {@link Question} id </br>
	 */
	public void deleteById(long id);
	/**
	 * Remove {@link Question} out the database that has the user id value matching the input value </br>
	 * @param userId value of user id of {@link Question} </br>
	 */
	public void deleteByUserId(long userId);
	/**
	 * Remove {@link Question} out the database that has the creation date matching the input value </br>
	 * @param creationDate value of creation date of {@link Question} </br>
	 */
	public void deleteByCreationDate(Timestamp creationDate);
	/**
	 * Remove {@link Question} out the database that has the question text matching the input value </br>
	 * @param questionText value of {@link Question} content </br>
	 */
	public void deleteByQuestionText(String questionText);
	/**
	 * Remove {@link Question} out the database that has question type id matching the input value </br>
	 * @param questionTypeId value of {@link QuestionType} id
	 */
	public void deleteByQuestionTypeId(long questionTypeId);
	/**
	 * Remove {@link Question} out the database that has the parent id value matching the input value </br>
	 * @param parentId value of parent question id
	 */
	public void deleteByParentId(long parentId);
	
	public void deleteByStatus(int status);
	/**
	 * Update the changes values of {@link Question} to the database </br>
	 * @param question {@link Question} object that is needed to update </br>
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Question question);

}
