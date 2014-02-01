package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.QuestionSessionRowMapper;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.model.QuestionSession;
/**
 * 
 * @author btdiem
 *
 */
public class QuestionSessionDAOImpl extends JdbcDaoSupport implements
		QuestionSessionDAO {

	@Transactional
	@Rollback(true)
	@Override
	public int insert(QuestionSession questionSession) {
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + SESSION_ID +")" + " VALUES(?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{questionSession.getQuestionId(),
					questionSession.getSessionId()});

	}

	@Transactional
	@Rollback(false)
	@Override
	public List<QuestionSession> findAll() {
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionSessionRowMapper());
	}

	@Transactional
	@Rollback(false)	
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


	@Transactional
	@Rollback(false)
	@Override
	public List<QuestionSession> findByQuestionId(String questionId) {
		return findByProperty(new String[]{QuestionSessionDAO.QUESTION_ID}, new Object[]{questionId});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<QuestionSession> findBySessionId(long sessionId) {
		return findByProperty(new String[]{QuestionSessionDAO.SESSION_ID}, new Object[]{sessionId});
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
	public void deleteByQuestionId(long questionId) {
		deleteByProperty(new String[]{QuestionSessionDAO.QUESTION_ID}, new Object[]{questionId});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteBySessionId(long sessionId) {
		deleteByProperty(new String[]{QuestionSessionDAO.SESSION_ID}, new Object[]{sessionId});
	}

}
