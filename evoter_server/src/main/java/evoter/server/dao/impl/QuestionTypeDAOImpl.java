package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.QuestionTypeRowMapper;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.model.QuestionType;
/**
 * 
 * This class is an implementation of {@link QuestionTypeDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("questionTypeDAO")
public class QuestionTypeDAOImpl extends JdbcDaoSupport implements QuestionTypeDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionTypeDAO#findAll()
	 */
	@Override
	public List<QuestionType> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new QuestionTypeRowMapper());

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionTypeDAO#insert(evoter.share.model.QuestionType)
	 */
	@Override
	public long insert(final QuestionType questionType) {
		
		final String sql = "INSERT INTO " + TABLE_NAME +
				"("+QUESTION_TYPE_VALUE + ") VALUES (?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setString(1, questionType.getQuestionTypeValue());
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionTypeDAO#findById(long)
	 */
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
