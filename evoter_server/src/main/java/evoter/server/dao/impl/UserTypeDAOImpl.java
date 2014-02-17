package evoter.server.dao.impl;

import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import evoter.server.model.mapper.UserTypeRowMapper;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;

/**
 * This is an implementation of {@link UserTypeDAO} </br>
 * 
 * @author btdiem </br>
 *
 */
@Repository("userTypeDAO")
public class UserTypeDAOImpl extends JdbcDaoSupport implements UserTypeDAO {

	
	/*
	 * 	(non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#findAll()
	 */
	@SuppressWarnings({})
	@Override
	public List<UserType> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new UserTypeRowMapper());
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#findById(long)
	 */
	@Override
	public List<UserType> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#findByUserTypeValue(java.lang.String)
	 */
	@Override
	public List<UserType> findByUserTypeValue(String userTypeValue) {

		return findByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
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

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
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
	 * @see evoter.share.dao.UserTypeDAO#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		
		deleteByProperty(new String[]{ID}, new Long[]{id});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#deleteByUserTypeValue(java.lang.String)
	 */
	@Override
	public void deleteByUserTypeValue(String userTypeValue) {
		
		deleteByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.UserTypeDAO#insert(evoter.share.model.UserType)
	 */
	@Override
	public long insert(final UserType userType) {
		
		final String sql = "INSERT INTO " + TABLE_NAME +
				"("+USER_TYPE_VALUE + ") VALUES (?)";
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						java.sql.Connection connection) throws SQLException {
					
		            PreparedStatement ps = connection.prepareStatement(sql);
		            ps.setString(1, userType.getUserTypeValue());
		            return ps;

				}
		    }, keyHolder);
		
		return keyHolder.getKey().longValue();
	}

}
