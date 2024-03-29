package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.QuestionTypeDAO;
import evoter.share.model.QuestionType;

/**
 * 
 * Mapping column values of row of {@link ResultSet} to {@link QuestionType} </br>
 * @author btdiem </br>
 *
 */
public class QuestionTypeRowMapper implements RowMapper<QuestionType> {

	@Override
	public QuestionType mapRow(ResultSet rs, int rowIndex) throws SQLException {
		
		QuestionType qeType = new QuestionType();
		qeType.setId(rs.getLong(QuestionTypeDAO.ID));
		qeType.setQuestionTypeValue(rs.getString(QuestionTypeDAO.QUESTION_TYPE_VALUE));
		return qeType;
	}


}
