package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import evoter.server.model.mapper.UserSubjectRowMapper;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;
/**
 * 
 * This is an implementation of {@link UserSubjectDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("userSubjectDAO")
public class UserSubjectDAOImpl extends JdbcDaoSupport implements UserSubjectDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#insert(evoter.share.model.UserSubject)
	 */
	@Override
	public long insert(final UserSubject us) {
		
		final String sql = "INSERT INTO " + TABLE_NAME + "(" + USER_ID + "," + SUBJECT_ID +") VALUES(?,?)";
		return getJdbcTemplate().update(sql, new Object[]{us.getUserId(), us.getSubjectId()});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#findAll()
	 */
	@Override
	public List<UserSubject> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new UserSubjectRowMapper());
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#findByUserId(long)
	 */
	@Override
	public List<UserSubject> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#findBySubjectId(long)
	 */
	@Override
	public List<UserSubject> findBySubjectId(long subjectId) {

		return findByProperty(new String[]{SUBJECT_ID}, new Long[]{subjectId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.UserSubjectDAO#deleteByUserId(long)
	 */
	@Override
	public void deleteByUserId(long userId) {
		
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserSubjectDAO#deleteBySubjectId(long)
	 */
	@Override
	public void deleteBySubjectId(long subjectId) {
		
		deleteByProperty(new String[]{SUBJECT_ID}, new Long[] {subjectId});
		
	}

}
