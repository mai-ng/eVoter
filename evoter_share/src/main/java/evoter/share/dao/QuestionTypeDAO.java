package evoter.share.dao;

import java.util.List;

import evoter.share.model.QuestionType;
/**
 * 
 * This class is an interface mapping all fields of QUESTION_TYPE table in the database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface QuestionTypeDAO {

	public static final String ID = "id";
	public static final String QUESTION_TYPE_VALUE = "question_type_value";
	public static final String TABLE_NAME = "QUESTION_TYPE";
	public static final int MULTIPLE_CHOICE = 1;
	public static final int YES_NO = 2;
	public static final int AGREE_DISAGREE = 3;
	public static final int SLIDER = 4;
	public static final int MATCH = 5;
	
	/**
	 * 
	 * @return all {@link QuestionType} in the database </br> 
	 */
	public List<QuestionType> findAll();
	/**
	 * 
	 * @param questionType value of {@link QuestionType} </br>
	 * @return a generated id if {@link QuestionType} is added successfully </br>
	 * @return a negative integer if {@link QuestionType} is not added failure </br>
	 */
	public long insert(QuestionType questionType);
	/**
	 * 
	 * @param id value of {@link QuestionType} id </br>
	 * @return all {@link QuestionType} in the database that have {@link QuestionType} id matching the input value </br>
	 */
	public List<QuestionType> findById(long id);
	/**
	 * 
	 * @param questionTypeValue value of {@link QuestionType} </br>
	 * @return all {@link QuestionType} in the database that have {@link QuestionType} matching the input value </br>
	 */
	public List<QuestionType> findByQuestionTypeValue(String questionTypeValue);
	/**
	 * 
	 * @param propertyNames name value of columns </br>
	 * @param propertyValues value of columns </br>
	 * @return all {@link QuestionType} that match the input values </br>
	 */
	public List<QuestionType> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Remove {@link QuestionType} out the database that match the input values </br>
	 * @param propertyNames name value of columns </br>
	 * @param propertyValues value of columns </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * 
	 * @param id value {@link QuestionType} id </br>
	 */
	public void deleteById(long id);
	public void deleteByQuestionTypeValue(String QuestionTypeValue);
}
