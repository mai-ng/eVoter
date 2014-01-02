package evoter.server.dao.test;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.UserTypeDAO;
import evoter.server.model.UserType;

public class TestUserTypeDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserTypeDAO userTypeDao = (UserTypeDAO)BeanDAOFactory.getBean("userTypeDAO");
		
		//test findAll
		System.out.println(userTypeDao.findAll());
		
		//test insert
		UserType insert = new UserType();
		insert.setUserTypeValue("test data");
		System.out.println(userTypeDao.insert(insert));
		
		//test find
		System.out.println(userTypeDao.findById(100));
		System.out.println(userTypeDao.findByUserTypeValue("test data"));
	
		//test delete
		userTypeDao.deleteById(10);
		System.out.println(userTypeDao.findAll());
		userTypeDao.deleteByUserTypeValue("test data");
		System.out.println(userTypeDao.findAll());
	}
	
	

}
