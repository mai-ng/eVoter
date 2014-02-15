package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
/**
 * 
 * @author btdiem
 *
 */
public class AnswerRowMapper implements RowMapper<Answer> {

	@Override
	public Answer mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Answer answer = new Answer();
		answer.setId(rs.getLong(AnswerDAO.ID));
		answer.setAnswerText(rs.getString(AnswerDAO.ANSWER_TEXT));
		answer.setQuestionId(rs.getLong(AnswerDAO.QUESTION_ID));
		answer.setStatistics(rs.getString(AnswerDAO.STATISTICS));
		return answer;
	}

}
