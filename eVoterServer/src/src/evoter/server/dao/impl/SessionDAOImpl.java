package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.SessionRowMapper;
import evoter.share.dao.SessionDAO;
import evoter.share.model.Session;

/**
 * 
 * @author btdiem
 *
 */
public class SessionDAOImpl extends JdbcDaoSupport implements SessionDAO {

	@Transactional
	@Rollback(true)
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
		            ps.setDate(3, session.getCreationDate());
		            ps.setBoolean(4, session.isActive());
		            ps.setLong(5, session.getUserId());
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();
//		return getJdbcTemplate().update(sql, new Object[]{session.getSubjectId(),session.getName(), session.getCreationDate(), session.isActive()});

	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SessionRowMapper());

	}

	@Transactional
	@Rollback(false)
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

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findBySubjectId(long subjectId) {
		
		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findByName(String name) {
		
		return findByProperty(new String[]{NAME}, new String[]{name});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findByCreationDate(String date) {
		
		return findByProperty(new String[]{CREATION_DATE}, new String[]{date});
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
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
		
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByName(String name) {
		
		deleteByProperty(new String[]{NAME}, new String[]{name});
		
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByCreationDate(String date) {

		deleteByProperty(new String[]{CREATION_DATE}, new String[]{date});
		
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findBySessionIsActive(boolean isActive) {

		return findBySessionIsActive(isActive);
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteBySessionIsActive(boolean isActive) {

		deleteBySessionIsActive(isActive);
		
	}

	@Transactional
	@Rollback(true)
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

	@Transactional
	@Rollback(false)
	@Override
	public List<Session> findBySessionUserId(long userId) {
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}



}
