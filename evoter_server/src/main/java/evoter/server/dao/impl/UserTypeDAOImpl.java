package evoter.server.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.UserTypeRowMapper;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;
@Repository("userTypeDAO")
public class UserTypeDAOImpl extends JdbcDaoSupport implements UserTypeDAO {

	
		
	@SuppressWarnings({})
	@Override
	public List<UserType> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		return getJdbcTemplate().query(sql, new UserTypeRowMapper());
	}
	
	
	@Override
	public List<UserType> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}
	
	
	@Override
	public List<UserType> findByUserTypeValue(String userTypeValue) {

		return findByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
	}
	
	
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
	public void deleteByUserTypeValue(String userTypeValue) {
		
		deleteByProperty(new String[]{USER_TYPE_VALUE}, new String[]{userTypeValue});
		
	}
	
	
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
