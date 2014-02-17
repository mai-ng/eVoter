package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;
/**
 * 
 * Mapping column values of row of {@link ResultSet} to {@link UserSubject} </br>
 * @author btdiem </br>
 *
 */
public class UserSubjectRowMapper implements RowMapper<UserSubject> {

	@Override
	public UserSubject mapRow(ResultSet rs, int rowIndex) throws SQLException {
		UserSubject us = new UserSubject();
		us.setUserId(rs.getLong(UserSubjectDAO.USER_ID));
		us.setSubjectId(rs.getLong(UserSubjectDAO.SUBJECT_ID));
		return us;
	}

}
