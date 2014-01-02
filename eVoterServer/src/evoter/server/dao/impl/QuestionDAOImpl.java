package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import evoter.server.dao.QuestionDAO;
import evoter.server.model.Question;
import evoter.server.model.mapper.QuestionRowMapper;


public class QuestionDAOImpl extends JdbcDaoSupport implements QuestionDAO {

	@Override
	public int insert(Question question) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ SESSION_ID + "," + QUESTION_TYPE_ID + ","+ QUESTION_TEXT + ")" + " VALUES(?,?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{question.getSessionId(),
								question.getQuestionTypeId(), 
								question.getQuestionText()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionRowMapper());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new QuestionRowMapper());
		
	}

	@Override
	public List<Question> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	@Override
	public List<Question> findBySessionId(long sessionId) {
		
		return findByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});	
		
	}

	@Override
	public List<Question> findByQuestionText(String questionText) {
		
		return findByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Override
	public List<Question> findByQuestionTypeId(long questionTypeId) {
		
		return findByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
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
	public void deleteBySessiontId(long sessionId) {
		
		deleteByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
		
	}

	@Override
	public void deleteByQuestionText(String questionText) {
		
		deleteByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Override
	public void deleteByQuestionTypeId(long questionTypeId) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

}
