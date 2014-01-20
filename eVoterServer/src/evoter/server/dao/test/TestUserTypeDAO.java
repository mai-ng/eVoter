package evoter.server.dao.test;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@TransactionConfiguration
@Transactional
public class TestUserTypeDAO extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	UserTypeDAO userTypeDAO;
	
	@Before
	public void setUp() throws Exception {
		//userTypeDAO = (UserTypeDAO)BeanDAOFactory.getBean(UserTypeDAO.BEAN_NAME);
	}

	@After
	public void tearDown() throws Exception {
		//userTypeDAO = null;
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testInsert(){
		
		UserType insert = new UserType();
		insert.setUserTypeValue("test data");
		userTypeDAO.insert(insert);
	}

	
	/**
	 * @param args
	 */
/**	public static void main(String[] args) {
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
	*/
	

}
