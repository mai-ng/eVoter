package evoter.server.dao.test;


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

import evoter.server.dao.impl.UserTypeDAOImpl;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserSubject;
import evoter.share.model.UserType;

/**
 * Make test cases for {@link UserTypeDAO} and {@link UserTypeDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserTypeDAO{

	/**
	 * Create {@link UserTypeDAOImpl} 
	 */
	@Autowired
	UserTypeDAO userTypeDAO;
	/**
	 * Test for {@link UserTypeDAO#findAll()} </br>
	 * Select all {@link UserType} in user_type table </br>
	 * Expect returning a list of {@link UserType} </br>
	 */
	@Test
	public void test_findAll(){
		List<UserType> userTypes = userTypeDAO.findAll();
		assertTrue("test_findAll", userTypes.size() > 0);
	}

	/**
	 * Test {@link UserTypeDAO#findById(long)} </br>
	 * Search all {@link UserType} by id=2 in user_type table and expect returning an list of {@link UserType} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		List<UserType> userTypes = userTypeDAO.findById(2);
		assertTrue("test_findById", userTypes.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByUserTypeValue(String)} </br>
	 * Search all {@link UserType} by value="Teacher" in user_type </br>
	 * Expect returning a list of {@link UserType} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByUserTypeValue(){
		List<UserType> userTypes = userTypeDAO.findByUserTypeValue("Teacher");
		assertTrue("test_findByUserTypeValue", userTypes.size() == 1);
	}
	/**
	 * Test {@link UserTypeDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link UserType} by id=2 and value=Teacher in user_type table </br>
	 * Expect returning a list of {@link UserType} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByProperty(){
		List<UserType> userTypes = userTypeDAO.findByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		assertTrue("test_findByProperty", userTypes.size() == 1);
	}
	/**
	 * Test {@link UserTypeDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete all {@link UserType} by id=2 and value=Teacher in user_type table </br>
	 * Search all records by these conditons and expect returning an empty list </br>
	 * 
	 */
	@Test
	public void test_deleteByProperty(){
		
		userTypeDAO.deleteByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		List<UserType> userTypes = userTypeDAO.findByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		assertTrue("test_deleteByProperty", userTypes.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteById(long)} </br>
	 * Delete all {@link UserType} by id=1 in user_type table </br>
	 * Search all {@link UserType} by id=1 and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteById(){
		
		userTypeDAO.deleteById(1);
		List<UserType> userTypes = userTypeDAO.findById(1);
		assertTrue("test_deleteById", userTypes.size() == 0);
	}

	/**
	 * Test {@link UserTypeDAO#deleteByUserTypeValue(String)} </br>
	 * Delete all {@link UserType} by value = teacher in user_type table </br>
	 * Search All {@link UserType} by value=teacher and expect returning an empty array </br>
	 */
	@Test
	public void test_deleteByUserTypeValue(){
		
		String userTypeValue = "Teacher";
		userTypeDAO.deleteByUserTypeValue(userTypeValue);
		
		List<UserType> userTypes = userTypeDAO.findByUserTypeValue(userTypeValue);
		assertTrue("test_deleteByUserTypeValue", userTypes.size() == 0);		
	}
	/**
	 * Test for {@link UserTypeDAO#insert(UserType)} </br>
	 * Insert a new {@link UserSubject} to user_type table </br>
	 */
	@Test
	public void test_insert(){
		
		UserType insert = new UserType("user type value for testing");
		long id = userTypeDAO.insert(insert);
		List<UserType> userTypes = userTypeDAO.findById(id);
		assertTrue(userTypes.size() == 1);
	}


}
