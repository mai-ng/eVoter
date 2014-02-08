package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;


import evoter.server.model.mapper.QuestionRowMapper;
import evoter.share.dao.QuestionDAO;
import evoter.share.model.Question;

/**
 * 
 * @author btdiem
 *
 */
@Repository("questionDAO")
public class QuestionDAOImpl extends JdbcDaoSupport implements QuestionDAO {

	
	
	
	@Override
	public long insert(final Question question) {

		final String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ USER_ID 
		+ "," + QUESTION_TYPE_ID 
		+ ","+ QUESTION_TEXT 
		+ "," + CREATION_DATE 
		+ "," + PARENT_ID + ")" 
		+ " VALUES(?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setLong(1, question.getUserId());
		            ps.setLong(2, question.getQuestionTypeId());
		            ps.setString(3, question.getQuestionText());
		            ps.setTimestamp(4, question.getCreationDate());
		            ps.setLong(5, question.getParentId());
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();
		
//		return getJdbcTemplate().update(sql, 
//				new Object[]{	question.getUserId(),
//								question.getQuestionTypeId(), 
//								question.getQuestionText(),
//								question.getCreationDate(),
//								question.getParentId()});
	}

	
	
	@Override
	public List<Question> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionRowMapper());

	}
	
	
	
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
	public List<Question> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});	
		
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
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	
	
	@Override
	public void deleteByQuestionText(String questionText) {
		
		deleteByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	
	
	@Override
	public void deleteByQuestionTypeId(long questionTypeId) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

	
	
	@Override
	public List<Question> findByCreationDate(Timestamp creationDate) {
		
		return findByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
	}

	
	
	@Override
	public void deleteByCreationDate(Timestamp creationDate) {
		
		deleteByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
		
	}

	
	
	@Override
	public List<Question> findByParentId(long parentId) {
		
		return findByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

	
	
	@Override
	public void deleteByParentId(long parentId) {
		
		deleteByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

}
