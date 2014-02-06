package evoter.server.dao.test;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.share.dao.StatisticsDAO;

public class TestStatisticsDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//test get DAO object
		StatisticsDAO statisticsDAO = (StatisticsDAO)BeanDAOFactory.getBean("statisticsDAO");
		//test find all
		System.out.println(statisticsDAO.findAll());

	}

}
