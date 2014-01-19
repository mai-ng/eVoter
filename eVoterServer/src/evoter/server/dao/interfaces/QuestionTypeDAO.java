package evoter.server.dao.interfaces;

import java.util.List;

import evoter.server.model.QuestionType;

public interface QuestionTypeDAO {

	public static final String ID = "id";
	public static final String QUESTION_TYPE_VALUE = "question_type_value";
	public static final String TABLE_NAME = "QUESTION_TYPE";
	public static final int MULTIPLE_CHOICE = 1;
	public static final int YES_NO = 2;
	public static final int AGREE_DISAGREE = 3;
	public static final int SLIDER = 4;
	public static final int MATCH = 5;
	
	public List<QuestionType> findAll();
	public int insert(QuestionType QuestionType);
	public List<QuestionType> findById(long id);
	public List<QuestionType> findByQuestionTypeValue(String questionTypeValue);
	public List<QuestionType> findByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteById(long id);
	public void deleteByQuestionTypeValue(String QuestionTypeValue);
}
