package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.UserTypeRowMapper;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;

public class UserTypeDAOImpl extends JdbcDaoSupport implements UserTypeDAO {

	@Transactional
	@Rollback(false)	
	@SuppressWarnings({})
	@Override
	public List<UserType> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new UserTypeRowMapper());
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserType> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserType> findByUserTypeValue(String userTypeValue) {

		return findByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
	}
	@Transactional
	@Rollback(false)
	@Override
	public List<UserType> findByProperty(String[] propertyNames, Object[] propertyValues) {

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}

		return  (List<UserType>)getJdbcTemplate().query(sql, propertyValues, new UserTypeRowMapper());
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
	public void deleteByUserTypeValue(String userTypeValue) {
		
		deleteByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
		
	}
	@Transactional
	@Rollback(true)
	@Override
	public int insert(UserType userType) {
		
		String sql = "INSERT INTO " + TABLE_NAME +
				"("+USER_TYPE_VALUE + ") VALUES (?)";
		return getJdbcTemplate().update(sql, new Object[]{userType.getUserTypeValue()});
	}

}
