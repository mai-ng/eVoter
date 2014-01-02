package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.QuestionTypeDAO;
import evoter.server.model.QuestionType;

public class QuestionTypeRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		QuestionType qeType = new QuestionType();
		qeType.setId(rs.getLong(QuestionTypeDAO.ID));
		qeType.setQuestionTypeValue(rs.getString(QuestionTypeDAO.QUESTION_TYPE_VALUE));
		return qeType;
	}


}
