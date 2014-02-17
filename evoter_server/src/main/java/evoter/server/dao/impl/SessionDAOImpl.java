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

import evoter.server.model.mapper.SessionRowMapper;
import evoter.share.dao.SessionDAO;
import evoter.share.model.Session;

/**
 * 
 * This class is an implementation of {@link SessionDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("sessionDAO")
public class SessionDAOImpl extends JdbcDaoSupport implements SessionDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#insert(evoter.share.model.Session)
	 */
	@Override
	public long insert(final Session session) {
		
		final String sql = "INSERT INTO " + TABLE_NAME + 
				"(" + SUBJECT_ID 
				+ "," + NAME + "," 
				+ CREATION_DATE + "," 
				+ IS_ACTIVE + "," 
				+ USER_ID + ")" 
				+ " VALUES(?,?,?,?,?)";
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setLong(1, session.getSubjectId());
		            ps.setString(2, session.getName());
		            ps.setTimestamp(3, session.getCreationDate());
		            ps.setBoolean(4, session.isActive());
		            ps.setLong(5, session.getUserId());
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findAll()
	 */
	@Override
	public List<Session> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SessionRowMapper());

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<Session> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		
		return getJdbcTemplate().query(sql, propertyValues, new SessionRowMapper());

	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findById(long)
	 */
	@Override
	public List<Session> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findBySubjectId(long)
	 */
	@Override
	public List<Session> findBySubjectId(long subjectId) {
		
		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findByName(java.lang.String)
	 */
	@Override
	public List<Session> findByName(String name) {
		
		return findByProperty(new String[]{NAME}, new String[]{name});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findByCreationDate(java.sql.Timestamp)
	 */
	@Override
	public List<Session> findByCreationDate(Timestamp date) {
		
		return findByProperty(new String[]{CREATION_DATE}, new Object[]{date});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.SessionDAO#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		
		deleteByProperty(new String[]{ID}, new Long[]{id});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteBySubjectId(long)
	 */
	@Override
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteByName(java.lang.String)
	 */
	@Override
	public void deleteByName(String name) {
		
		deleteByProperty(new String[]{NAME}, new String[]{name});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteByCreationDate(java.sql.Timestamp)
	 */
	@Override
	public void deleteByCreationDate(Timestamp date) {

		deleteByProperty(new String[]{CREATION_DATE}, new Object[]{date});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findBySessionIsActive(boolean)
	 */
	@Override
	public List<Session> findBySessionIsActive(boolean isActive) {

		return findByProperty(new String[]{IS_ACTIVE}, new Object[]{isActive});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteBySessionIsActive(boolean)
	 */
	@Override
	public void deleteBySessionIsActive(boolean isActive) {

		deleteByProperty(new String[]{IS_ACTIVE}, new Object[]{isActive});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#update(evoter.share.model.Session)
	 */
	@Override
	public int update(Session session) {
		
		String sql = "UPDATE " + TABLE_NAME + 
					" SET " + CREATION_DATE +"='" + session.getCreationDate()+"'" 
						+ " , " + IS_ACTIVE + "=" + session.isActive()
						+ " , " + NAME + "='" + session.getName()+"'"
						+ " , " + SUBJECT_ID + "=" + session.getSubjectId()
						+ " , " + USER_ID + "=" + session.getUserId()
						+ " WHERE " + ID + "=" + session.getId();
		
		return getJdbcTemplate().update(sql);
		
					
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#findBySessionUserId(long)
	 */
	@Override
	public List<Session> findBySessionUserId(long userId) {
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SessionDAO#deleteByUserId(long)
	 */
	@Override
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}



}
