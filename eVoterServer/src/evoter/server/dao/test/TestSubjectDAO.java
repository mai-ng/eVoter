package evoter.server.dao.test;

import java.sql.Date;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.SubjectDAO;
import evoter.server.model.Subject;

public class TestSubjectDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Subject sub = new Subject();
		sub.setTitle("test subject lalala");
		sub.setCreationDate(new Date(System.currentTimeMillis()));
		SubjectDAO subjectDao = (SubjectDAO) BeanDAOFactory.getBean("subjectDAO");

		Subject sub1 = new Subject();
		sub1.setTitle("test subject lalala xxx");
		sub1.setCreationDate(new Date(System.currentTimeMillis()+100));

		
		// test insert
		subjectDao.insert(sub);
		subjectDao.insert(sub1);
		//test find all
		System.out.println(subjectDao.findAll().size());
		//test find by id
		System.out.println(subjectDao.findById(1));
		//test find by title
		System.out.println(subjectDao.findByTitle("Testing Metrics"));
		//test find by creation date
		System.out.println(subjectDao.findByCreationDate("2013-12-28 12:50:24"));
		//test delete by id
		subjectDao.deleteById(10);
		//test delete by creation date
		subjectDao.deleteByCreationDate("5962-06-21 00:00:00");
		//test delete by title
		subjectDao.deleteByTitle("test subject lalala xxx");
		
		
	}	

}
