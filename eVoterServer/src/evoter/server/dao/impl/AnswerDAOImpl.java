package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import evoter.server.dao.AnswerDAO;
import evoter.server.model.Answer;
import evoter.server.model.mapper.AnswerRowMapper;

public class AnswerDAOImpl extends JdbcDaoSupport implements AnswerDAO {

	@Override
	public long insert(final Answer answer) {
		
		
		final String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + ANSWER_TEXT +")" + " VALUES(?,?)";
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setLong(1, answer.getQuestionId());
		            ps.setString(2, answer.getAnswerText());
		            return ps;

				}
		    }, keyHolder);
		
//		return getJdbcTemplate().update(sql, 
//				new Object[]{answer.getQuestionId(),
//								answer.getAnswerText()});
		
		 return keyHolder.getKey().longValue();
		//getJdbcTemplate().
		//return getJdbcTemplate().getMaxRows();
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
