package evoter.server.dao.test;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.share.dao.SessionUserDAO;

public class TestSessionUserDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//test get DAO object
		SessionUserDAO sesUserDao = (SessionUserDAO)BeanDAOFactory.getBean("sessionUserDAO");
		//test insert
/**		SessionUser sesUser = new SessionUser();
		sesUser.setAcceptSession(false);
		sesUser.setDeleteIndicator(false);
		sesUser.setSessionId(4);
		sesUser.setUserId(23);
		sesUserDao.insert(sesUser);
		//the second
		sesUser.setSessionId(5);
		sesUserDao.insert(sesUser);
		//the third
		sesUser.setUserId(21);
		sesUserDao.insert(sesUser);
		*/
		//test find all
//		System.out.println(sesUserDao.findAll());
		//test find by user id
//		System.out.println(sesUserDao.findByUserId(2));
		//test find by session id
//		System.out.println(sesUserDao.findBySessionId(5));
		//test find by delete indicator
//		System.out.println(sesUserDao.findByDeleteIndicator(false));
		//test find by accept session
//		System.out.println(sesUserDao.findByAcceptSession(false));
//		System.out.println(sesUserDao.findByAcceptSession(true));
		
		//test delete by userId
		sesUserDao.deleteByProperty(new String[]{SessionUserDAO.SESSION_ID, 
												SessionUserDAO.USER_ID}, 
									new Object[]{5,23});
	}

}
