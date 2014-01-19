package evoter.server.dao.test;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;

public class TestUserSubjectDAO {

	public static void main(String[] args) {
		
		//test init DAO subject
		UserSubjectDAO usDao = (UserSubjectDAO)BeanDAOFactory.getBean("userSubjectDAO");
		
		//test insert
		UserSubject us = new UserSubject();
		us.setSubjectId(4);
		us.setUserId(21);
		usDao.insert(us);
		
		us.setSubjectId(8);
		us.setUserId(23);
		usDao.insert(us);
		
		us.setSubjectId(13);
		us.setUserId(23);
		usDao.insert(us);
		
		//test find all
		System.out.println(usDao.findAll());
		//test find by userId
		System.out.println(usDao.findByUserId(3));
		//test find by subjectId
		System.out.println(usDao.findBySubjectId(2));
		//test find by multi-property 
		System.out.println(usDao.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID},  
				new Object[]{21,4}));
		
		//test delete by userId
		usDao.deleteBySubjectId(4);
		
		//test delete by subjectId
		usDao.deleteByUserId(23);
	}
	

}
