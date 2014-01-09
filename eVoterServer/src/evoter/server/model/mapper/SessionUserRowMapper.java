package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.SessionUserDAO;
import evoter.server.model.SessionUser;

public class SessionUserRowMapper implements RowMapper<SessionUser> {

	@Override
	public SessionUser mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		SessionUser su = new SessionUser();
		su.setAcceptSession(rs.getBoolean(SessionUserDAO.ACCEPT_SESSION));
		su.setDeleteIndicator(rs.getBoolean(SessionUserDAO.DELETE_INDICATOR));
		su.setSessionId(rs.getLong(SessionUserDAO.SESSION_ID));
		su.setUserId(rs.getLong(SessionUserDAO.USER_ID));
		return su;
	}

}
