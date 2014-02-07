package evoter.server.dao.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.UserDAOImpl;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.User;
/**
 * Make test cases for {@link UserDAO} and {@link UserDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestUserDAO {

	@Autowired
	UserDAO userDAO;
	
	
	@Test
	public void testFindAll(){
		List<User> users = userDAO.findAll();
		assertTrue("testFindAll", users.size() > 0);
	}

	/**
	 * Test {@link UserTypeDAO#findById(long)} </br>
	 * Search {@link User} by {@link UserTypeDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindById(){
		List<User> users = userDAO.findById(2);
		assertTrue("testFindById", users.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByUserName(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByUserName(){
		List<User> users = userDAO.findByUserName("paul_gibson");
		assertTrue("testFindByUserName", users.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByUserTypeId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByUserTypeId(){
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("testFindByUserTypeId", users.size() > 0);
	}
	/**
	 * Test for {@link UserTypeDAO#findByEmail(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByEmail(){
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("testFindByEmail", users.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByFullName(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByFullname(){
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("testFindByFullname", users.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByApproved(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByApproved(){
		List<User> users = userDAO.findByApproved(true);
		assertTrue("testFindByApproved", users.size() > 0);
	}
	
	/**
	 * Test {@link UserTypeDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link User} by {@link UserDAO#ID} and {@link UserDAO.USER_TYPE_ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByProperty(){
		List<User> users = userDAO.findByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID, UserDAO.IS_APPROVED}, 
				new Object[]{2, 2, true});
		
		assertTrue("testFindByProperty", users.size() == 1);
	}
	/**
	 * Test {@link UserTypeDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByProperty(){
		
		userDAO.deleteByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID}, 
				new Object[]{2,2});
		
		List<User> users = userDAO.findByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID}, 
				new Object[]{2,2});
		
		assertTrue("testDeleteByProperty", users.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteById(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteById(){
		
		userDAO.deleteById(1);
		List<User> users = userDAO.findById(1);
		assertTrue("testDeleteById", users.size() == 0);
	}
	/**
	 * Test {@link UserTypeDAO#deleteByUserTypeId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByUserTypeId(){
		
		userDAO.deleteByUserTypeId(2);
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("testDeleteById", users.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteByEmail(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByEmail(){
		
		userDAO.deleteByEmail("diemth@gmail.com");
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("testDeleteByEmail", users.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteByFullName(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByFullName(){
		
		userDAO.deleteByFullName("Paul Gibson");
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("testDeleteByFullName", users.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteByApproved(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByApproved(){
		
		userDAO.deleteByApproved(true);
		List<User> users = userDAO.findByApproved(true);
		assertTrue("testDeleteByApproved", users.size() == 0);
	}		
	/**
	 * Test for {@link UserTypeDAO#deleteByUserName(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByUserName(){
		
		userDAO.deleteByUserName("btdiem");
		List<User> users = userDAO.findByUserName("btdiem");
		assertTrue("testDeleteByUserName", users.size() == 0);
	}

}
