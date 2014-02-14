package evoter.server.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.UserDAOImpl;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
/**
 * Make test cases for {@link UserDAO} and {@link UserDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserDAO {

	@Autowired
	UserDAO userDAO;
	
	
	@Test
	@Rollback(false)
	public void testFindAll(){
		List<User> users = userDAO.findAll();
		assertTrue("testFindAll", users.size() > 0);
	}

	/**
	 * Test {@link UserDAO#findById(long)} </br>
	 * Search {@link User} by {@link UserDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindById(){
		List<User> users = userDAO.findById(2);
		assertTrue("testFindById", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByUserName(String)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByUserName(){
		List<User> users = userDAO.findByUserName("paul_gibson");
		assertTrue("testFindByUserName", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByUserTypeId(Long)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByUserTypeId(){
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("testFindByUserTypeId", users.size() > 0);
	}
	/**
	 * Test for {@link UserDAO#findByEmail(Long)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByEmail(){
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("testFindByEmail", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByFullName(Long)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByFullname(){
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("testFindByFullname", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByApproved(Boolean)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByApproved(){
		List<User> users = userDAO.findByApproved(true);
		assertTrue("testFindByApproved", users.size() > 0);
	}
	
	/**
	 * Test {@link UserDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link User} by {@link UserDAO#ID} and {@link UserDAO.USER_TYPE_ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByProperty(){
		List<User> users = userDAO.findByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID, UserDAO.IS_APPROVED}, 
				new Object[]{2, 2, true});
		
		assertTrue("testFindByProperty", users.size() == 1);
	}
	/**
	 * Test {@link UserDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
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
	 * Test {@link UserDAO#deleteById(long)} </br>
	 */
	@Test
	public void testDeleteById(){
		
		userDAO.deleteById(1);
		List<User> users = userDAO.findById(1);
		assertTrue("testDeleteById", users.size() == 0);
	}
	/**
	 * Test {@link UserDAO#deleteByUserTypeId(long)} </br>
	 */
	@Test
	public void testDeleteByUserTypeId(){
		
		userDAO.deleteByUserTypeId(2);
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("testDeleteById", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByEmail(String)} </br>
	 */
	@Test
	public void testDeleteByEmail(){
		
		userDAO.deleteByEmail("diemth@gmail.com");
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("testDeleteByEmail", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByFullName(String)} </br>
	 */
	@Test
	public void testDeleteByFullName(){
		
		userDAO.deleteByFullName("Paul Gibson");
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("testDeleteByFullName", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByApproved(String)} </br>
	 */
	@Test
	public void testDeleteByApproved(){
		
		userDAO.deleteByApproved(true);
		List<User> users = userDAO.findByApproved(true);
		assertTrue("testDeleteByApproved", users.size() == 0);
	}		
	/**
	 * Test for {@link UserDAO#deleteByUserName(String)} </br>
	 */
	@Test
	public void testDeleteByUserName(){
		
		userDAO.deleteByUserName("btdiem");
		List<User> users = userDAO.findByUserName("btdiem");
		assertTrue("testDeleteByUserName", users.size() == 0);
	}
	@Test
	public void testUpdate(){
		
		User user = userDAO.findById(28).get(0);
		//change email
		user.setEmail("sundy_3th2_agu@yahoo.com");
		user.setUserName("bui_thi_diem");
		user.setFullName("Bui Thi Diem");
		user.setPassWord("new_pass_12345");
		user.setUserTypeId(2);
		user.setApproved(false);
		userDAO.update(user);
		user = userDAO.findById(28).get(0);
		assertEquals("update() changes email", "sundy_3th2_agu@yahoo.com", user.getEmail());
		assertEquals("update() changes full name", "Bui Thi Diem", user.getFullName());
		assertEquals("update() changes password", "new_pass_12345", user.getPassWord());
		assertEquals("update() changes approved status", false, user.isApproved());
		assertEquals("update() changes user type", 2, user.getUserTypeId());
		//change username
		
//		user = userDAO.findById(28).get(0);
//		userDAO.update(user);
		
		
		
		
	}

}
