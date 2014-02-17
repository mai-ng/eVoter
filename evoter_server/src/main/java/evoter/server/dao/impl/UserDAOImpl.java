package evoter.server.dao.impl;


import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import evoter.server.model.mapper.UserRowMapper;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;

/**
 * 
 * This is an implementation of {@link UserDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("userDAO")
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findById(long)
	 */
	@Override
	public List<User> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});

	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#insert(evoter.share.model.User)
	 */
	@Override
	public int insert(User user) {
		
		String sql = "INSERT INTO " + TABLE_NAME +
				"(" + USER_TYPE_ID + "," + 
				EMAIL + "," +
				USER_NAME + "," +
				PASSWORD + "," +
				FULL_NAME + "," +
				IS_APPROVED + 
				") VALUES (?, ?, ?, ?, ?, ?)";
				 
		return	getJdbcTemplate().update(sql, new Object[] { user.getUserTypeId(),
														user.getEmail(),
														user.getUserName(),
														user.getPassWord(),
														user.getFullName(),
														user.isApproved()});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findAll()
	 */
	@SuppressWarnings({})
	@Override
	public List<User> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new UserRowMapper());
		
	}

	
	/*
	 * 	(non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByUserName(java.lang.String)
	 */
	@Override
	public List<User> findByUserName(String userName) {

		return findByProperty(new String[]{USER_NAME}, new String[]{userName});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByUserTypeId(long)
	 */
	@Override
	public List<User> findByUserTypeId(long userTypeId) {

		return findByProperty(new String[]{USER_TYPE_ID}, new Long[]{userTypeId});

	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByEmail(java.lang.String)
	 */
	@Override
	public List<User> findByEmail(String email) {

		return findByProperty(new String[]{EMAIL}, new String[]{email});

	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.UserDAO#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		
		deleteByProperty(new String[]{ID}, new Long[]{id});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#deleteByUserName(java.lang.String)
	 */
	@Override
	public void deleteByUserName(String userName) {
		
		deleteByProperty(new String[]{USER_NAME}, new String[]{userName});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#deleteByUserTypeId(long)
	 */
	@Override
	public void deleteByUserTypeId(long userTypeId) {
		
		deleteByProperty(new String[]{USER_TYPE_ID}, new Long[]{userTypeId});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#deleteByEmail(java.lang.String)
	 */
	@Override
	public void deleteByEmail(String email) {
		
		deleteByProperty(new String[]{EMAIL}, new String[]{email});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByFullName(java.lang.String)
	 */
	@Override
	public List<User> findByFullName(String fullName) {
		
		return findByProperty(new String[]{FULL_NAME}, new String[]{fullName});
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#findByApproved(boolean)
	 */
	@Override
	public List<User> findByApproved(boolean isApproved) {
		
		return findByProperty(new String[]{IS_APPROVED}, new Boolean[]{isApproved});
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#deleteByFullName(java.lang.String)
	 */
	@Override
	public void deleteByFullName(String fullName) {
		
		deleteByProperty(new String[]{FULL_NAME}, new String[]{fullName});
	}

/*
 * (non-Javadoc)
 * @see evoter.share.dao.UserDAO#deleteByApproved(boolean)
 */
	@Override
	public void deleteByApproved(boolean isApproved) {
		
		deleteByProperty(new String[]{IS_APPROVED}, new Boolean[]{isApproved});
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserDAO#update(evoter.share.model.User)
	 */
	@Override
	public int update(User user) {

		String sql="UPDATE " + TABLE_NAME + " SET "
		+ EMAIL +"='" + user.getEmail() + "',"
		+ FULL_NAME + "='" + user.getFullName() + "',"
		+ IS_APPROVED + "=" + user.isApproved() + ","
		+ PASSWORD + "='" + user.getPassWord() + "',"
		+ USER_NAME + "='" + user.getUserName() + "',"
		+ USER_TYPE_ID + "=" + user.getUserTypeId()
		+ " WHERE " + ID + "=" + user.getId();
		return getJdbcTemplate().update(sql);
	}
	
	
	
}
