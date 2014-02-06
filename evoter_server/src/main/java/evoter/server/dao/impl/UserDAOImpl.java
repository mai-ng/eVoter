package evoter.server.dao.impl;


import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.UserRowMapper;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

	
	
	@Override
	public List<User> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});

	}
	
	
	@Override
	public int insert(User user) {
		
		String sql = "INSERT INTO " + TABLE_NAME +
				"(" + USER_TYPE_ID + "," + 
				EMAIL + "," +
				USER_NAME + "," +
				PASSWORD + ") VALUES (?, ?, ?, ?)";
				 
		return	getJdbcTemplate().update(sql, new Object[] { user.getUserTypeId(),
														user.getEmail(),
														user.getUserName(),
														user.getPassWord()});
		
	}
	
	
	@SuppressWarnings({})
	@Override
	public List<User> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new UserRowMapper());
		
	}

	
		
	@Override
	public List<User> findByUserName(String userName) {

		return findByProperty(new String[]{USER_NAME}, new String[]{userName});
	}
	
	
	@Override
	public List<User> findByUserTypeId(long userTypeId) {

		return findByProperty(new String[]{USER_TYPE_ID}, new Long[]{userTypeId});

	}
	
	
	@Override
	public List<User> findByEmail(String email) {

		return findByProperty(new String[]{EMAIL}, new String[]{email});

	}
	
	
	@Override
	public List<User> findByPassword(String password) {

		return findByProperty(new String[]{PASSWORD}, new String[]{password});

	}
	
	
	@Override
	public List<User> findByProperty(String[] propertyNames, Object[] propertyValues) {

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}

		return (List<User>)getJdbcTemplate().query(sql, propertyValues, new UserRowMapper());

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
	public void deleteByUserName(String userName) {
		
		deleteByProperty(new String[]{USER_NAME}, new String[]{userName});
	}
	
	
	@Override
	public void deleteByUserTypeId(long userTypeId) {
		
		deleteByProperty(new String[]{USER_TYPE_ID}, new Long[]{userTypeId});
	}
	
	
	@Override
	public void deleteByEmail(String email) {
		
		deleteByProperty(new String[]{EMAIL}, new String[]{email});
	}
	
	
	@Override
	public void deleteByPassword(String password) {
		
		deleteByProperty(new String[]{PASSWORD}, new String[]{password});
	}


	@Override
	public List<User> findByFullName(String fullName) {
		
		return findByProperty(new String[]{FULL_NAME}, new String[]{fullName});
	}


	@Override
	public List<User> findByApproved(boolean isApproved) {
		
		return findByProperty(new String[]{IS_APPROVED}, new Boolean[]{isApproved});
	}


	@Override
	public void deleteByFullName(String fullName) {
		
		deleteByProperty(new String[]{FULL_NAME}, new String[]{fullName});
	}


	@Override
	public void deleteByApproved(boolean isApproved) {
		
		deleteByProperty(new String[]{IS_APPROVED}, new Boolean[]{isApproved});
		
	}
	
}
