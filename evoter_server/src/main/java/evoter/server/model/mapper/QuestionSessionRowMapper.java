/**
 * 
 */
package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.QuestionSessionDAO;
import evoter.share.model.QuestionSession;

/**
 * Mapping column values of row of {@link ResultSet} to {@link QuestionSession} </br>
 * @author btdiem </br>
 *
 */
public class QuestionSessionRowMapper implements RowMapper<QuestionSession> {

	@Override
	public QuestionSession mapRow(ResultSet rs, int arg1) throws SQLException {
		QuestionSession quesSess = new QuestionSession();
		quesSess.setQuestionId(rs.getLong(QuestionSessionDAO.QUESTION_ID));
		quesSess.setSessionId(rs.getLong(QuestionSessionDAO.SESSION_ID));
		return quesSess;
	}

}
