package evoter.server.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import evoter.server.dao.StatisticsDAO;
import evoter.server.model.Statistics;

public class StatisticsRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {

		Statistics st = new Statistics();
		st.setQuestionId(rs.getLong(StatisticsDAO.QUESTION_ID));
		st.setSessionId(rs.getLong(StatisticsDAO.SESSION_ID));
		st.setStatisticValue(rs.getString(StatisticsDAO.STATISTICS_VALUE));
		return st;
	}

}
