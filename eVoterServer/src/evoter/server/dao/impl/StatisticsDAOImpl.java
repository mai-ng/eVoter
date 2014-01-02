package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import evoter.server.dao.StatisticsDAO;
import evoter.server.model.Statistics;
import evoter.server.model.mapper.StatisticsRowMapper;

public class StatisticsDAOImpl extends JdbcDaoSupport implements StatisticsDAO {

	@Override
	public int insert(Statistics statistics) {

		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + SESSION_ID + "," + STATISTICS_VALUE +")" + " VALUES(?,?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{statistics.getQuestionId(),
							statistics.getSessionId(), 
							statistics.getStatisticValue()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Statistics> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new StatisticsRowMapper());
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Statistics> findByProperty(String[] propertyNames,
			Object[] propertyValues) {

		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new StatisticsRowMapper());
	}

	@Override
	public List<Statistics> findBySessionId(long sessionId) {
		
		return findByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
	}

	@Override
	public List<Statistics> findByStatisticsValue(String statisticsValue) {

		return findByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
	}

	@Override
	public List<Statistics> findByQuestionId(long questionId) {

		return findByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
	}

	@Override
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues) {
		
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			if (i<len-1)
				sql += " AND ";
		}
		getJdbcTemplate().update(sql, propertyValues);
		
	}

	@Override
	public void deleteBySessionId(long sessionId) {
		
		deleteByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
		
	}

	@Override
	public void deleteByQuestionId(long questionId) {

		deleteByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
		
	}

	@Override
	public void deleteByStatisticsValue(String statisticsValue) {
		
		deleteByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
		
	}

}
