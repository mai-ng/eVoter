package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.QuestionTypeRowMapper;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.model.QuestionType;
/**
 * 
 * @author btdiem
 *
 */
public class QuestionTypeDAOImpl extends JdbcDaoSupport implements QuestionTypeDAO {

	
	
	@Override
	public List<QuestionType> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new QuestionTypeRowMapper());

	}

	
	
	@Override
	public int insert(QuestionType questionType) {
		
		String sql = "INSERT INTO " + TABLE_NAME +
				"("+QUESTION_TYPE_VALUE + ") VALUES (?)";
		return getJdbcTemplate().update(sql, new Object[]{questionType.getQuestionTypeValue()});
		
	}

	
	
	@Override
	public List<QuestionType> findById(long id) {

		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	
	
	@Override
	public List<QuestionType> findByQuestionTypeValue(String questionTypeValue) {
		
		return findByProperty(new String[]{QUESTION_TYPE_VALUE}, new String[]{questionTypeValue});
	}

	
	
	@Override
	public List<QuestionType> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return (List<QuestionType>)getJdbcTemplate().query(sql, propertyValues, new QuestionTypeRowMapper());
	}

	
	
	@Override
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues) {
		
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		getJdbcTemplate().update(sql, propertyValues);
		
	}

	
	
	@Override
	public void deleteById(long id) {
		
		deleteByProperty(new String[]{ID}, new Long[]{id});
		
	}

	
	
	@Override
	public void deleteByQuestionTypeValue(String questionTypeValue) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_VALUE}, new String[]{questionTypeValue});
		
	}

}
