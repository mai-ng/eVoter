package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.share.dao.*;
import evoter.share.model.*;

public class StatisticsRowMapper implements RowMapper<Statistics> {

	@Override
	public Statistics mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Statistics st = new Statistics();
		st.setQuestionId(rs.getLong(StatisticsDAO.QUESTION_ID));
		st.setSessionId(rs.getLong(StatisticsDAO.SESSION_ID));
		st.setStatisticValue(rs.getString(StatisticsDAO.STATISTICS_VALUE));
		return st;
	}

}
