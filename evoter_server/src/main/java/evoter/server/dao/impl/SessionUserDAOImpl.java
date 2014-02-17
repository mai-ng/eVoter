package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.SessionUserRowMapper;
import evoter.share.dao.SessionUserDAO;
import evoter.share.model.SessionUser;
/**
 * This class is an implementation of {@link SessionUserDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("sessionUserDAO")
public class SessionUserDAOImpl extends JdbcDaoSupport implements SessionUserDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#insert(evoter.share.model.SessionUser)
	 */
	@Override
	public int insert(SessionUser sessionUser) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ USER_ID + "," + SESSION_ID + "," + DELETE_INDICATOR + "," + ACCEPT_SESSION 
		+ ") VALUES(?,?,?,?)";
		
		return getJdbcTemplate().update(sql, 
				new Object[]{sessionUser.getUserId(), sessionUser.getSessionId(), sessionUser.isDeleteIndicator(), sessionUser.isAcceptSession()});

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findAll()
	 */
	@Override
	public List<SessionUser> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SessionUserRowMapper());

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<SessionUser> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new SessionUserRowMapper());

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findByUserId(long)
	 */
	@Override
	public List<SessionUser> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findBySessionId(long)
	 */
	@Override
	public List<SessionUser> findBySessionId(long sessionId) {
		
		return findByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findByDeleteIndicator(boolean)
	 */
	@Override
	public List<SessionUser> findByDeleteIndicator(boolean deleteIndicator) {
		
		return findByProperty(new String[]{DELETE_INDICATOR}, new Boolean[]{deleteIndicator});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#findByAcceptSession(boolean)
	 */
	@Override
	public List<SessionUser> findByAcceptSession(boolean acceptSession) {
	
		return findByProperty(new String[]{ACCEPT_SESSION}, new Boolean[]{acceptSession});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.SessionUserDAO#deleteByUserId(long)
	 */
	@Override
	public void deleteByUserId(long userId) {
		
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#deleteBySessionId(long)
	 */
	@Override
	public void deleteBySessionId(long sessionId) {
		
		deleteByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#deleteByDeleteIndicator(boolean)
	 */
	@Override
	public void deleteByDeleteIndicator(boolean deleteIndicator) {
		
		deleteByProperty(new String[]{DELETE_INDICATOR}, new Boolean[]{deleteIndicator});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#deleteByAcceptSession(boolean)
	 */
	@Override
	public void deleteByAcceptSession(boolean acceptSession) {
		
		deleteByProperty(new String[]{ACCEPT_SESSION}, new Boolean[]{acceptSession});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionUserDAO#update(evoter.share.model.SessionUser)
	 */
	@Override
	public int update(SessionUser sessUser) {
		
		String sql = "UPDATE " + TABLE_NAME + 
				" SET " + ACCEPT_SESSION +"=" +sessUser.isAcceptSession() 
					+ " , " + DELETE_INDICATOR + "=" + sessUser.isDeleteIndicator()
					+ " WHERE " + SESSION_ID + "=" + sessUser.getSessionId()
					+ " AND " + USER_ID + " = " + sessUser.getUserId();

		return getJdbcTemplate().update(sql);
		
	}

}
