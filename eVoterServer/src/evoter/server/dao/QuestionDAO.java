package evoter.server.dao;

import java.sql.Date;
import java.util.List;
import evoter.server.model.Question;


public interface QuestionDAO {
	
	public static final String ID = "ID";
	public static final String QUESTION_TEXT = "QUESTION_TEXT";
//	public static final String SESSION_ID = "SESSION_ID";
	public static final String QUESTION_TYPE_ID = "QUESTION_TYPE_ID";
	public static final String CREATION_DATE = "CREATION_DATE";
	public static final String USER_ID = "USER_ID";
	public static final String PARENT_ID = "PARENT_ID";
	
	public static final String TABLE_NAME = "QUESTION";	
	public static final String BEAN_NAME = "questionDAO";
	
	
	public int insert (Question Question);
	public List<Question> findAll();
	/**
	 * Search {@link Question} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public List<Question> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<Question> findById(long id);
	public List<Question> findByUserId(long userId);
	public List<Question> findByQuestionText(String questionText);
	public List<Question> findByQuestionTypeId(long questionTypeId);
	public List<Question> findByCreationDate(Date creationDate);
	public List<Question> findByParentId(long parentId);
	/**
	 * 
	 * Delete {@link Question} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Question} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteById(long id);
	public void deleteByUserId(long userId);
	public void deleteByCreationDate(Date creationDate);
	public void deleteByQuestionText(String questionText);
	public void deleteByQuestionTypeId(long questionTypeId);
	public void deleteByParentId(long parentId);

}
