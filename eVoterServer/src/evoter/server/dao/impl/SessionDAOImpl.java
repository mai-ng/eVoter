package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import evoter.server.dao.SessionDAO;
import evoter.server.model.Session;
import evoter.server.model.mapper.SessionRowMapper;


public class SessionDAOImpl extends JdbcDaoSupport implements SessionDAO {

	@Override
	public long insert(final Session session) {
		
		final String sql = "INSERT INTO " + TABLE_NAME + 
				"(" + SUBJECT_ID 
				+ "," + NAME + "," 
				+ CREATION_DATE + "," 
				+ IS_ACTIVE + ")" 
				+ " VALUES(?,?,?,?)";
		
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
		            return ps;

				}
		    }, keyHolder);
		
		 return keyHolder.getKey().longValue();
//		return getJdbcTemplate().update(sql, new Object[]{session.getSubjectId(),session.getName(), session.getCreationDate(), session.isActive()});

	}

	@Override
	public List<Session> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SessionRowMapper());

	}

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

	@Override
	public List<Session> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	@Override
	public List<Session> findBySubjectId(long subjectId) {
		
		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
	}

	@Override
	public List<Session> findByName(String name) {
		
		return findByProperty(new String[]{NAME}, new String[]{name});
	}

	@Override
	public List<Session> findByCreationDate(String date) {
		
		return findByProperty(new String[]{CREATION_DATE}, new String[]{date});
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
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
		
	}

	@Override
	public void deleteByName(String name) {
		
		deleteByProperty(new String[]{NAME}, new String[]{name});
		
	}

	@Override
	public void deleteByCreationDate(String date) {

		deleteByProperty(new String[]{CREATION_DATE}, new String[]{date});
		
	}

	@Override
	public List<Session> findBySessionIsActive(boolean isActive) {

		return findBySessionIsActive(isActive);
	}

	@Override
	public void deleteBySessionIsActive(boolean isActive) {

		deleteBySessionIsActive(isActive);
		
	}

	@Override
	public int update(Session session) {
		
		String sql = "UPDATE " + TABLE_NAME + 
					" SET " + CREATION_DATE +"='" + session.getCreationDate()+"'" 
						+ " , " + IS_ACTIVE + "=" + session.isActive()
						+ " , " + NAME + "='" + session.getName()+"'"
						+ " , " + SUBJECT_ID + "=" + session.getSubjectId()
						+ " WHERE " + ID + "=" + session.getId();
		
		return getJdbcTemplate().update(sql);
		
					
		
	}



}
