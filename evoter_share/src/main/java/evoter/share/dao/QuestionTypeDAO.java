package evoter.share.dao;

import java.util.List;

import evoter.share.model.QuestionType;
/**
 * 
 * This class is an interface mapping all properties in this class to fields
 *  of QUESTION_TYPE table in the database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface QuestionTypeDAO {
	//id column
	public static final String ID = "id";
	//question_type_value column
	public static final String QUESTION_TYPE_VALUE = "question_type_value";
	//table name
	public static final String TABLE_NAME = "QUESTION_TYPE";
	//multiple choice question type id
	public static final int MULTIPLE_CHOICE = 1;
	//yes/no question type id
	public static final int YES_NO = 2;
	//agree/disagree question type id	
	public static final int AGREE_DISAGREE = 3;
	//slider question type id	
	public static final int SLIDER = 4;
	//match question type id
	public static final int MATCH = 5;
	
	/**
	 * Select all records of question_type table
	 * @return List of {@link QuestionType} if there are records returned</br>
	 * Otherwise, returning an empty list </br> 
	 */
	public List<QuestionType> findAll();
	/**
	 * Insert a {@link QuestionType} to database </br>
	 * @param questionType contains new values inserted </br>
	 * @return a generated id of new record if {@link QuestionType} is added successfully </br>
	 * @return a negative integer if {@link QuestionType} is not added failure </br>
	 */
	public long insert(QuestionType questionType);
	/**
	 * Select all records matching the given id </br>
	 * 
	 * @param id value of {@link QuestionType} id </br>
	 * @return List of {@link QuestionType} there are records returned </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<QuestionType> findById(long id);
	/**
	 * Select all records that have question type matching give questionTypeValue </br>
	 * @param questionTypeValue value of questionType </br>
	 * @return List of {@link QuestionType} there are records returned </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<QuestionType> findByQuestionTypeValue(String questionTypeValue);
	/**
	 * Select all records matching the input conditions </br>

	 * @param propertyNames name value of columns </br>
	 * @param propertyValues value of columns </br>

	 * @return List of {@link QuestionType} there are records returned </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<QuestionType> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records matching the input condition </br>
	 * 
	 * @param propertyNames name value of columns </br>
	 * @param propertyValues value of columns </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete a record that has id matching the given id </br>
	 * @param id value {@link QuestionType} id </br>
	 */
	public void deleteById(long id);
	/**
	 * Delete all records that have type matching the give type </br>
	 * @param questionTypeValue type value of question </br>
	 */
	public void deleteByQuestionTypeValue(String questionTypeValue);
}
