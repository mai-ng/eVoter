package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import evoter.server.model.mapper.UserSubjectRowMapper;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;

public class UserSubjectDAOImpl extends JdbcDaoSupport implements UserSubjectDAO {

	@Override
	public int insert(UserSubject us) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" + USER_ID + "," + SUBJECT_ID +") VALUES(?,?)";
		return getJdbcTemplate().update(sql, new Object[]{us.getUserId(), us.getSubjectId()});
	}

	@Override
	public List<UserSubject> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new UserSubjectRowMapper());
	}

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

	@Override
	public List<UserSubject> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	@Override
	public List<UserSubject> findBySubjectId(long subjectId) {

		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
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
	public void deleteByUserId(long userId) {
		
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
		
	}

	@Override
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[] {subjectId});
		
	}

}
