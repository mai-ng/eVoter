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

	/**
	 * Create a {@link UserDAOImpl} instance
	 */
	@Autowired
	UserDAO userDAO;
	
	/**
	 * Test for {@link UserDAO#findAll()} </br>
	 * Select all Users in user table </br>
	 * Expect returning a list of {@link User} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<User> users = userDAO.findAll();
		assertTrue("test_findAll", users.size() > 0);
	}

	/**
	 * Test {@link UserDAO#findById(long)} </br>
	 * Search all {@link User} by user id=2 and expect returning a list of {@link User}</br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		List<User> users = userDAO.findById(2);
		assertTrue("test_findById", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByUserName(String)} </br>
	 * Search all {@link User} by user name="paul_gibson" in user table </br>
	 * Expect returning one record </br> 
	 */
	@Test
	@Rollback(false)
	public void test_findByUserName(){
		List<User> users = userDAO.findByUserName("paul_gibson");
		assertTrue("test_findByUserName", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByUserTypeId(Long)} </br>
	 * Select all users by user type id=2 in user table </br>
	 * Expect returning a list of {@link User} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByUserTypeId(){
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("test_findByUserTypeId", users.size() > 0);
	}
	/**
	 * Test for {@link UserDAO#findByEmail(Long)} </br>
	 * Search all users by email="diemth@gmail.com" in user table </br>
	 * Expect returning one record </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByEmail(){
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("test_findByEmail", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByFullName(Long)} </br>
	 * Search all users by full name="Paul Gibson" in user table </br>
	 * Expect returning one record </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByFullname(){
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("test_findByFullname", users.size() == 1);
	}
	/**
	 * Test for {@link UserDAO#findByApproved(Boolean)} </br>
	 * Search all users by is_approved=true in user table </br>
	 * Expect returning a list of {@link User} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByApproved(){
		List<User> users = userDAO.findByApproved(true);
		assertTrue("test_findByApproved", users.size() > 0);
	}
	
	/**
	 * Test {@link UserDAO#findByProperty(String[], Object[])} </br>
	 * Search all {@link User} by user ID=2, user type ID=2 and approved status =true in user table</br>
	 * Expect returning a list of {@link User} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByProperty(){
		List<User> users = userDAO.findByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID, 
							UserDAO.IS_APPROVED}, 
				new Object[]{2, 2, true});
		
		assertTrue("test_findByProperty", users.size() == 1);
	}
	/**
	 * Test {@link UserDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete all users by id=2 and user type id=2 </br>
	 * Search all users by these conditions and expect returning an empty list </br>
	 * 
	 */
	@Test
	public void test_deleteByProperty(){
		
		userDAO.deleteByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID}, 
				new Object[]{2,2});
		
		List<User> users = userDAO.findByProperty(
				new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID}, 
				new Object[]{2,2});
		
		assertTrue("test_deleteByProperty", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteById(long)} </br>
	 * Delete user by id=1 in user table </br>
	 * Search user by user id=1 and returning nothing </br>
	 */
	@Test
	public void test_deleteById(){
		
		userDAO.deleteById(1);
		List<User> users = userDAO.findById(1);
		assertTrue("test_deleteById", users.size() == 0);
	}
	/**
	 * Test {@link UserDAO#deleteByUserTypeId(long)} </br>
	 * Delete all users by user type id=2 in user table </br>
	 * SEarch all users by user type id=2 and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByUserTypeId(){
		
		userDAO.deleteByUserTypeId(2);
		List<User> users = userDAO.findByUserTypeId(2);
		assertTrue("test_deleteById", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByEmail(String)} </br>
	 * Delete all users by email="diemth@gmail.com" in user table </br>
	 * Search all users by this email and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByEmail(){
		
		userDAO.deleteByEmail("diemth@gmail.com");
		List<User> users = userDAO.findByEmail("diemth@gmail.com");
		assertTrue("test_deleteByEmail", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByFullName(String)} </br>
	 * Delete all users by full name="Paul Gibson" in user table </br>
	 * Search all users by this condition and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByFullName(){
		
		userDAO.deleteByFullName("Paul Gibson");
		List<User> users = userDAO.findByFullName("Paul Gibson");
		assertTrue("test_deleteByFullName", users.size() == 0);
	}	
	/**
	 * Test {@link UserDAO#deleteByApproved(String)} </br>
	 * Delete all users by approved status = true in user table</br>
	 * Search all users by this condition and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByApproved(){
		
		userDAO.deleteByApproved(true);
		List<User> users = userDAO.findByApproved(true);
		assertTrue("test_deleteByApproved", users.size() == 0);
	}		
	/**
	 * Test for {@link UserDAO#deleteByUserName(String)} </br>
	 * Delete all users by user name=btdiem in user table </br>
	 * Search all users by this condition and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByUserName(){
		
		userDAO.deleteByUserName("btdiem");
		List<User> users = userDAO.findByUserName("btdiem");
		assertTrue("test_deleteByUserName", users.size() == 0);
	}
	/**
	 * Test for {@link UserDAO#update(User)} </br>
	 * Update email, username, full name, password, user type and approved status for user id=28 in user table </br>
	 * Search user by id=28 in user table and expect the changes are updated </br>
	 */
	@Test
	public void test_update(){
		
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
	}

}
