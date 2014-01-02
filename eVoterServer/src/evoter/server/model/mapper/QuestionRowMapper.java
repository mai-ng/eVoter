package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.QuestionDAO;
import evoter.server.model.Question;

public class QuestionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Question qe = new Question();
		qe.setId(rs.getLong(QuestionDAO.ID));
		qe.setQuestionText(rs.getString(QuestionDAO.QUESTION_TEXT));
		qe.setQuestionTypeId(rs.getLong(QuestionDAO.QUESTION_TYPE_ID));
		qe.setSessionId(rs.getLong(QuestionDAO.SESSION_ID));
		return qe;
	}

}
