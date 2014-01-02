package evoter.server.dao.test;

import java.sql.Date;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.SessionDAO;
import evoter.server.model.Session;

public class TestSessionDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//test get DAO object
		SessionDAO sessionDao = (SessionDAO)BeanDAOFactory.getBean("sessionDAO");
		//test insert
		Session session = new Session();
		session.setCreationDate(new Date(System.currentTimeMillis()));
		session.setName("subject_4_session_1");
		session.setSubjectId(4);
		sessionDao.insert(session);

		//test find all
		System.out.println(sessionDao.findAll());
		//test find by id
		System.out.println(sessionDao.findById(3));
		//test find by subject id
		System.out.println(sessionDao.findBySubjectId(3));
		//test find by name
		System.out.println(sessionDao.findByName("subject_1_session_1"));
		//test find by creation date
		System.out.println(sessionDao.findByCreationDate("2014-01-01 00:00:00"));

		//test delete by id
		sessionDao.deleteById(10);
		sessionDao.deleteByCreationDate("2014-01-01 01:20:00");
		sessionDao.deleteBySubjectId(4);
		sessionDao.deleteByName("subject_4_session_1");
		
	}

}
