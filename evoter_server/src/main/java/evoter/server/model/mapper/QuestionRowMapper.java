package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.QuestionDAO;
import evoter.share.model.Question;
/**
 * 
 * Mapping column value of row of {@link ResultSet} to {@link Question} </br>
 * @author btdiem </br>
 *
 */
public class QuestionRowMapper implements RowMapper<Question> {

	@Override
	public Question mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Question question = new Question();
		question.setId(rs.getLong(QuestionDAO.ID));
		question.setQuestionText(rs.getString(QuestionDAO.QUESTION_TEXT));
		question.setQuestionTypeId(rs.getLong(QuestionDAO.QUESTION_TYPE_ID));
		question.setUserId(rs.getLong(QuestionDAO.USER_ID));
		question.setCreationDate(rs.getTimestamp(QuestionDAO.CREATION_DATE));
		question.setParentId(rs.getLong(QuestionDAO.PARENT_ID));
		question.setStatus(rs.getInt(QuestionDAO.STATUS));
		
		return question;
	}

}
