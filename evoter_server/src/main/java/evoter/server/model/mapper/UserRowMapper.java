package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.UserDAO;
import evoter.share.model.User;
/**
 * Mapping column values of {@link ResultSet} to {@link User} </br>
 * @author btdiem </br>
 *
 */
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		User user = new User();
		user.setId(rs.getLong(UserDAO.ID));
		user.setUserName(rs.getString(UserDAO.USER_NAME));
		user.setEmail(rs.getString(UserDAO.EMAIL));
		user.setPassWord(rs.getString(UserDAO.PASSWORD));
		user.setUserTypeId(rs.getLong(UserDAO.USER_TYPE_ID));
		user.setFullName(rs.getString(UserDAO.FULL_NAME));
		user.setApproved(rs.getBoolean(UserDAO.IS_APPROVED));
		return user;
	}

}
