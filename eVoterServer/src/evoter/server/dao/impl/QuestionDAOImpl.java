package evoter.server.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import evoter.server.model.mapper.QuestionRowMapper;
import evoter.share.dao.QuestionDAO;
import evoter.share.model.Question;

/**
 * 
 * @author btdiem
 *
 */
public class QuestionDAOImpl extends JdbcDaoSupport implements QuestionDAO {

	
	@Transactional
	@Rollback(true)
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
		            ps.setDate(4, question.getCreationDate());
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

	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionRowMapper());

	}
	
	@Transactional
	@Rollback(false)
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
	
	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}
	
	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});	
		
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findByQuestionText(String questionText) {
		
		return findByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findByQuestionTypeId(long questionTypeId) {
		
		return findByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

	@Transactional
	@Rollback(true)
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

	@Transactional
	@Rollback(true)
	@Override
	public void deleteById(long id) {
		deleteByProperty(new String[]{ID}, new Long[]{id});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByQuestionText(String questionText) {
		
		deleteByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByQuestionTypeId(long questionTypeId) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findByCreationDate(Date creationDate) {
		
		return findByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByCreationDate(Date creationDate) {
		
		deleteByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
		
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Question> findByParentId(long parentId) {
		
		return findByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByParentId(long parentId) {
		
		deleteByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

}
