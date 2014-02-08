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

import evoter.server.model.mapper.SubjectRowMapper;
import evoter.share.dao.SubjectDAO;
import evoter.share.model.Subject;
@Repository("subjectDAO")
public class SubjectDAOImpl extends JdbcDaoSupport implements SubjectDAO {

	
	
	@Override
	public long insert(final Subject subject) {
		
		final String sql = "INSERT INTO " + TABLE_NAME + "(" + TITLE + "," + CREATION_DATE + ")" + " VALUES(?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setString(1, subject.getTitle());
		            ps.setTimestamp(2, subject.getCreationDate());
		            return ps;

				}
		    }, keyHolder);
		
		return keyHolder.getKey().longValue();
					
	}

	
	
	@Override
	public List<Subject> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SubjectRowMapper());
	}

	
	
	@Override
	public List<Subject> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		
		return getJdbcTemplate().query(sql, propertyValues, new SubjectRowMapper());
		
	}
	
	
	@Override
	public List<Subject> findById(long id) {

		return findByProperty(new String[]{ID}, new Object[]{id});
	}
	
	
	@Override
	public List<Subject> findByTitle(String title) {
		
		return findByProperty(new String[]{TITLE}, new Object[]{title});
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
	public void deleteByTitle(String title) {
		
		deleteByProperty(new String[]{TITLE}, new String[]{title});
		
	}
	
	
	@Override
	public List<Subject> findByCreationDate(Timestamp date) {
		
		return findByProperty(new String[]{CREATION_DATE}, new Object[]{date});
	}
	
	
	@Override
	public void deleteByCreationDate(Timestamp date) {
		
		deleteByProperty(new String[]{CREATION_DATE}, new Object[]{date});
		
	}


	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.SubjectDAO#update(evoter.share.model.Subject)
	 */
	@Override
	public int update(Subject subject) {
		
		String sql = "UPDATE " + TABLE_NAME + 
				" SET " + CREATION_DATE +"='" + subject.getCreationDate()+"'" 
					+ " , " + TITLE + "='" + subject.getTitle()+"'"
					+ " WHERE " + ID + "=" + subject.getId();
		
		return getJdbcTemplate().update(sql);
	}

}
