package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.test.context.ContextConfiguration;

import evoter.server.model.mapper.AnswerRowMapper;
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
/**
 * 
 * This class is an implementation of {@link AnswerDAO} </br>
 * 
 * @author btdiem </br>
 *
 */
//@ContextConfiguration(locations = {"/resources/applicationContext.xml"})
@Repository("answerDAO")
public class AnswerDAOImpl extends JdbcDaoSupport implements AnswerDAO {

//	@Autowired
//	JdbcTemplate getJdbcTemplate();
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#insert(evoter.share.model.Answer)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#findAll()
	 */
	@Override
	public List<Answer> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new AnswerRowMapper());

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
	
	
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

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#findById(long)
	 */
	
	
	@Override
	public List<Answer> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#findByAnswerText(java.lang.String)
	 */
	
	
	@Override
	public List<Answer> findByAnswerText(String answerText) {
		
		return findByProperty(new String[]{ANSWER_TEXT}, new String[]{answerText});
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#findByQuestionId(long)
	 */
	
	
	@Override
	public List<Answer> findByQuestionId(long questionId) {

		return findByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.AnswerDAO#deleteById(long)
	 */
	
	
	@Override
	public void deleteById(long id) {
		
		deleteByProperty(new String[]{ID}, new Long[]{id});
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#deleteByQuestionId(long)
	 */
	
	
	@Override
	public void deleteByQuestionId(long questionId) {
		
		deleteByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#deleteByAnswerText(java.lang.String)
	 */
	
	
	@Override
	public void deleteByAnswerText(String answerText) {
		
		deleteByProperty(new String[]{ANSWER_TEXT}, new String[]{answerText});
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.AnswerDAO#update(evoter.share.model.Answer)
	 */
	@Override
	public int update(Answer answer) {
		
		final String sql = 
				"UPDATE " + TABLE_NAME 
				+ " SET " 
//				+ QUESTION_ID + "=" + answer.getQuestionId() 
				+  ANSWER_TEXT +"='" + answer.getAnswerText() + "'" 
				+ " WHERE " + ID + "=" + answer.getId();

		return getJdbcTemplate().update(sql);
	}
	
	

}
