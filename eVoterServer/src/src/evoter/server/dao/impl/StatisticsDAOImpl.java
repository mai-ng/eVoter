package evoter.server.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.model.mapper.StatisticsRowMapper;
import evoter.share.dao.StatisticsDAO;
import evoter.share.model.Statistics;

public class StatisticsDAOImpl extends JdbcDaoSupport implements StatisticsDAO {

	@Transactional
	@Rollback(true)
	@Override
	public int insert(Statistics statistics) {

		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + SESSION_ID + "," + STATISTICS_VALUE +")" + " VALUES(?,?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{statistics.getQuestionId(),
							statistics.getSessionId(), 
							statistics.getStatisticValue()});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Statistics> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new StatisticsRowMapper());
	
	}

	@Transactional
	@Rollback(false)
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

	@Transactional
	@Rollback(false)
	@Override
	public List<Statistics> findBySessionId(long sessionId) {
		
		return findByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Statistics> findByStatisticsValue(String statisticsValue) {

		return findByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
	}

	@Transactional
	@Rollback(false)
	@Override
	public List<Statistics> findByQuestionId(long questionId) {

		return findByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
	}

	@Transactional
	@Rollback(true)
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

	@Transactional
	@Rollback(true)
	@Override
	public void deleteBySessionId(long sessionId) {
		
		deleteByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
		
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByQuestionId(long questionId) {

		deleteByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
		
	}

	@Transactional
	@Rollback(true)
	@Override
	public void deleteByStatisticsValue(String statisticsValue) {
		
		deleteByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
		
	}

}
