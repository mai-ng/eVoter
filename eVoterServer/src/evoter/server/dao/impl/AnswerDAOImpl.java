package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import evoter.server.dao.AnswerDAO;
import evoter.server.model.Answer;
import evoter.server.model.mapper.AnswerRowMapper;

public class AnswerDAOImpl extends JdbcDaoSupport implements AnswerDAO {

	@Override
	public int insert(Answer answer) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + ANSWER_TEXT +")" + " VALUES(?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{answer.getQuestionId(),
								answer.getAnswerText()});
	}

	@Override
	public List<Answer> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new AnswerRowMapper());

	}

	@Override
	public List<Answer> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new AnswerRowMapper());
	}

	@Override
	public List<Answer> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	@Override
	public List<Answer> findByAnswerText(String answerText) {
		
		return findByProperty(new String[]{ANSWER_TEXT}, new String[]{answerText});
	}

	@Override
	public List<Answer> findByQuestionId(long questionId) {

		return findByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
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
	public void deleteByQuestionId(long questionId) {
		
		deleteByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
		
	}

	@Override
	public void deleteByAnswerText(String answerText) {
		
		deleteByProperty(new String[]{ANSWER_TEXT}, new String[]{answerText});
		
	}

}
