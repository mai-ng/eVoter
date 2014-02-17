package evoter.server.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import evoter.server.model.mapper.StatisticsRowMapper;
import evoter.share.dao.StatisticsDAO;
import evoter.share.model.Statistics;
/**
 * This class is an implementation of {@link StatisticsDAO} </br>
 * @author btdiem </br>
 *
 */
@Repository("statisticsDAO")
public class StatisticsDAOImpl extends JdbcDaoSupport implements StatisticsDAO {

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#insert(evoter.share.model.Statistics)
	 */
	@Override
	public int insert(Statistics statistics) {

		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ QUESTION_ID + "," + SESSION_ID + "," + STATISTICS_VALUE +")" + " VALUES(?,?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{statistics.getQuestionId(),
							statistics.getSessionId(), 
							statistics.getStatisticValue()});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#findAll()
	 */
	@Override
	public List<Statistics> findAll() {

		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new StatisticsRowMapper());
	
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#findByProperty(java.lang.String[], java.lang.Object[])
	 */
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

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#findBySessionId(long)
	 */
	@Override
	public List<Statistics> findBySessionId(long sessionId) {
		
		return findByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#findByStatisticsValue(java.lang.String)
	 */
	@Override
	public List<Statistics> findByStatisticsValue(String statisticsValue) {

		return findByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#findByQuestionId(long)
	 */
	@Override
	public List<Statistics> findByQuestionId(long questionId) {

		return findByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#deleteByProperty(java.lang.String[], java.lang.Object[])
	 */
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

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#deleteBySessionId(long)
	 */
	@Override
	public void deleteBySessionId(long sessionId) {
		
		deleteByProperty(new String[]{SESSION_ID}, new Long[]{sessionId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#deleteByQuestionId(long)
	 */
	@Override
	public void deleteByQuestionId(long questionId) {

		deleteByProperty(new String[]{QUESTION_ID}, new Long[]{questionId});
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.share.dao.StatisticsDAO#deleteByStatisticsValue(java.lang.String)
	 */
	@Override
	public void deleteByStatisticsValue(String statisticsValue) {
		
		deleteByProperty(new String[]{STATISTICS_VALUE}, new String[]{statisticsValue});
		
	}

}
