package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;

public class UserTypeRowMapper implements RowMapper<UserType> {

	@Override
	public UserType mapRow(ResultSet rs, int arg1) throws SQLException {
		UserType userType = new UserType();
		userType.setId(rs.getLong(UserTypeDAO.ID));
		userType.setUserTypeValue(rs.getString(UserTypeDAO.USER_TYPE_VALUE));
		return userType;
	}

}
