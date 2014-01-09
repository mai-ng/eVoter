/**
 * 
 */
package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.QuestionSessionDAO;
import evoter.server.model.QuestionSession;

/**
 * @author btdiem
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
