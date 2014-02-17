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
 * This class is an implementation of {@link QuestionDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("questionDAO")
public class QuestionDAOImpl extends JdbcDaoSupport implements QuestionDAO {

	
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#insert(evoter.share.model.Question)
	 */
	@Override
	public long insert(final Question question) {

		final String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ USER_ID 
		+ "," + QUESTION_TYPE_ID 
		+ ","+ QUESTION_TEXT 
		+ "," + CREATION_DATE 
		+ "," + PARENT_ID 
		+ "," + STATUS
		+ ")" 
		+ " VALUES(?,?,?,?,?,?)";
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
		            ps.setInt(6, question.getStatus());
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findAll()
	 */
	@Override
	public List<Question> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionRowMapper());

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
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
	
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findById(long)
	 */
	@Override
	public List<Question> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByUserId(long)
	 */
	@Override
	public List<Question> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});	
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByQuestionText(java.lang.String)
	 */
	@Override
	public List<Question> findByQuestionText(String questionText) {
		
		return findByProperty(new String[]{QUESTION_TEXT}
							, new String[]{questionText});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByQuestionTypeId(long)
	 */
	@Override
	public List<Question> findByQuestionTypeId(long questionTypeId) {
		
		return findByProperty(new String[]{QUESTION_TYPE_ID}
							, new Long[]{questionTypeId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
	 */
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

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		deleteByProperty(new String[]{ID}, new Long[]{id});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByUserId(long)
	 */
	@Override
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByQuestionText(java.lang.String)
	 */
	@Override
	public void deleteByQuestionText(String questionText) {
		
		deleteByProperty(new String[]{QUESTION_TEXT}
						, new String[]{questionText});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByQuestionTypeId(long)
	 */
	@Override
	public void deleteByQuestionTypeId(long questionTypeId) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_ID}
						, new Long[]{questionTypeId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByCreationDate(java.sql.Timestamp)
	 */
	@Override
	public List<Question> findByCreationDate(Timestamp creationDate) {
		
		return findByProperty(new String[]{CREATION_DATE}
							, new Object[]{creationDate});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByCreationDate(java.sql.Timestamp)
	 */
	@Override
	public void deleteByCreationDate(Timestamp creationDate) {
		
		deleteByProperty(new String[]{CREATION_DATE}
						, new Object[]{creationDate});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByParentId(long)
	 */
	@Override
	public List<Question> findByParentId(long parentId) {
		
		return findByProperty(new String[]{PARENT_ID}
							, new Long[]{parentId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByParentId(long)
	 */
	@Override
	public void deleteByParentId(long parentId) {
		
		deleteByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}


	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#update(evoter.share.model.Question)
	 */
	@Override
	public int update(Question question) {
		
		final String sql = "UPDATE " + TABLE_NAME 
		+ " SET " 
		+ USER_ID + "=" +  question.getUserId()
		+ "," + QUESTION_TYPE_ID + "=" + question.getQuestionTypeId()
		+ ","+ QUESTION_TEXT + "='" + question.getQuestionText() + "'"
		+ "," + CREATION_DATE + "='" + question.getCreationDate()+"'"
		+ "," + PARENT_ID + "=" + question.getParentId()
		+ "," + STATUS + "=" + question.getStatus()
		+ " WHERE " + ID + "=" + question.getId();
		
		return getJdbcTemplate().update(sql);
	}


	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#findByStatus(int)
	 */
	@Override
	public List<Question> findByStatus(int status) {
		return findByProperty(new String[]{STATUS}, new Object[]{status});
	}


	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionDAO#deleteByStatus(int)
	 */
	@Override
	public void deleteByStatus(int status) {
		deleteByProperty(new String[]{STATUS}, new Object[]{status});
	}

	
}
