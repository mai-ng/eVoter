package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.SubjectDAO;
import evoter.share.model.Subject;

public class SubjectRowMapper implements RowMapper<Subject> {


	@Override
	public Subject mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		Subject sub = new Subject();
		sub.setId(rs.getLong(SubjectDAO.ID));
		sub.setTitle(rs.getString(SubjectDAO.TITLE));
		sub.setCreationDate(rs.getTimestamp(SubjectDAO.CREATION_DATE));
		return sub;
	}

}
