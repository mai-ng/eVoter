package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.SubjectRowMapper;
import evoter.share.dao.SubjectDAO;
import evoter.share.model.Subject;

public class SubjectDAOImpl extends JdbcDaoSupport implements SubjectDAO {

	@Transactional
	@Rollback(true)
	@Override
	public int insert(Subject subject) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" + TITLE + "," + CREATION_DATE + ")" + " VALUES(?,?)";
		return getJdbcTemplate().update(sql, new Object[]{subject.getTitle(), subject.getCreationDate()});
					
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Subject> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new SubjectRowMapper());
	}

	@Transactional
	@Rollback(false)
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
	@Transactional
	@Rollback(false)
	@Override
	public List<Subject> findById(long id) {

		return findByProperty(new String[]{ID}, new Object[]{id});
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<Subject> findByTitle(String title) {
		
		return findByProperty(new String[]{TITLE}, new Object[]{title});
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
	public void deleteByTitle(String title) {
		
		deleteByProperty(new String[]{TITLE}, new String[]{title});
		
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<Subject> findByCreationDate(String date) {
		
		return findByProperty(new String[]{CREATION_DATE}, new String[]{date});
	}
	@Transactional
	@Rollback(true)
	@Override
	public void deleteByCreationDate(String date) {
		
		deleteByProperty(new String[]{CREATION_DATE}, new String[]{date});
		
	}

}
