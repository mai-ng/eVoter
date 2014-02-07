package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.QuestionDAO;
import evoter.share.model.Question;

public class QuestionRowMapper implements RowMapper<Question> {

	@Override
	public Question mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Question qe = new Question();
		qe.setId(rs.getLong(QuestionDAO.ID));
		qe.setQuestionText(rs.getString(QuestionDAO.QUESTION_TEXT));
		qe.setQuestionTypeId(rs.getLong(QuestionDAO.QUESTION_TYPE_ID));
//		qe.setSessionId(rs.getLong(QuestionDAO.SESSION_ID));
		qe.setUserId(rs.getLong(QuestionDAO.USER_ID));
		qe.setCreationDate(rs.getTimestamp(QuestionDAO.CREATION_DATE));
		qe.setParentId(rs.getLong(QuestionDAO.PARENT_ID));
		return qe;
	}

}
