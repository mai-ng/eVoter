package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.UserSubjectRowMapper;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;

public class UserSubjectDAOImpl extends JdbcDaoSupport implements UserSubjectDAO {

	@Transactional
	@Rollback(true)
	@Override
	public int insert(UserSubject us) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" + USER_ID + "," + SUBJECT_ID +") VALUES(?,?)";
		return getJdbcTemplate().update(sql, new Object[]{us.getUserId(), us.getSubjectId()});
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserSubject> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new UserSubjectRowMapper());
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserSubject> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new UserSubjectRowMapper());
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserSubject> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserSubject> findBySubjectId(long subjectId) {

		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
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
	public void deleteByUserId(long userId) {
		
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
		
	}
	@Transactional
	@Rollback(true)
	@Override
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[] {subjectId});
		
	}

}
