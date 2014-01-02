package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.SubjectDAO;
import evoter.server.model.Subject;

public class SubjectRowMapper implements RowMapper {


	@Override
	public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		Subject sub = new Subject();
		sub.setId(rs.getLong(SubjectDAO.ID));
		sub.setTitle(rs.getString(SubjectDAO.TITLE));
		sub.setCreationDate(rs.getDate(SubjectDAO.CREATION_DATE));
		return sub;
	}

}
