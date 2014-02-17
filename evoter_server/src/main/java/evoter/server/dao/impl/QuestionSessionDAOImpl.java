package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.QuestionSessionRowMapper;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.model.QuestionSession;
/**
 * 
 * This class is an implementation of {@link QuestionSessionDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("questionSessionDAO")
public class QuestionSessionDAOImpl extends JdbcDaoSupport implements
		QuestionSessionDAO {

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#insert(evoter.share.model.QuestionSession)
	 */
	@Override
	public int insert(QuestionSession questionSession) {
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + SESSION_ID +")" + " VALUES(?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{questionSession.getQuestionId(),
					questionSession.getSessionId()});

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#findAll()
	 */
	@Override
	public List<QuestionSession> findAll() {
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionSessionRowMapper());
	}

	
	/*
	 * 	(non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<QuestionSession> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new QuestionSessionRowMapper());	
	}


	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#findByQuestionId(java.lang.String)
	 */
	@Override
	public List<QuestionSession> findByQuestionId(String questionId) {
		return findByProperty(new String[]{QuestionSessionDAO.QUESTION_ID}
							, new Object[]{questionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#findBySessionId(long)
	 */
	@Override
	public List<QuestionSession> findBySessionId(long sessionId) {
		return findByProperty(new String[]{QuestionSessionDAO.SESSION_ID}
							, new Object[]{sessionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.QuestionSessionDAO#deleteByQuestionId(long)
	 */
	@Override
	public void deleteByQuestionId(long questionId) {
		deleteByProperty(new String[]{QuestionSessionDAO.QUESTION_ID}
						, new Object[]{questionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.QuestionSessionDAO#deleteBySessionId(long)
	 */
	@Override
	public void deleteBySessionId(long sessionId) {
		deleteByProperty(new String[]{QuestionSessionDAO.SESSION_ID}
						, new Object[]{sessionId});
	}

}
