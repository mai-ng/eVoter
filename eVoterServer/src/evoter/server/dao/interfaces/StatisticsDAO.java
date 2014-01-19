package evoter.server.dao.interfaces;

import java.util.List;


import evoter.server.model.Statistics;

public interface StatisticsDAO {
	
	public static final String SESSION_ID = "SESSION_ID";
	public static final String QUESTION_ID = "QUESTION_ID";
	public static final String STATISTICS_VALUE = "STATISTICS_VALUE";
	public static final String TABLE_NAME = "STATISTICS";
	
	public int insert (Statistics Statistics);
	public List<Statistics> findAll();
	/**
	 * Search {@link Statistics} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Statistics} </br>
	 */
	public List<Statistics> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<Statistics> findBySessionId(long sessionId);
	public List<Statistics> findByStatisticsValue(String statisticsValue);
	public List<Statistics> findByQuestionId(long questionId);
	/**
	 * 
	 * Delete {@link Statistics} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Statistics} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteBySessionId(long sessionId);
	public void deleteByQuestionId(long questionId);
	public void deleteByStatisticsValue(String StatisticsValue);

}
