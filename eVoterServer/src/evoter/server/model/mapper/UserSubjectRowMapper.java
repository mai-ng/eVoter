package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.UserSubjectDAO;
import evoter.server.model.UserSubject;

public class UserSubjectRowMapper implements RowMapper<UserSubject> {

	@Override
	public UserSubject mapRow(ResultSet rs, int rowIndex) throws SQLException {
		UserSubject us = new UserSubject();
		us.setUserId(rs.getLong(UserSubjectDAO.USER_ID));
		us.setSubjectId(rs.getLong(UserSubjectDAO.SUBJECT_ID));
		return us;
	}

}
