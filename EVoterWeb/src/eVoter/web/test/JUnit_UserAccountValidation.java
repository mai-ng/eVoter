/**
 * 
 */
package eVoter.web.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eVoter.web.util.UserAccountValidation;

/**
 * @author maint
 * test {@link UserAccountValidation} class to check the valid of user name, password and email.
 */
public class JUnit_UserAccountValidation {

	/**
	 *  UserName is OK when:
	 * <li> length from 6 - 15
	 * <li> contains characters a-z, A-Z, 0-9, _, and -
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidUserName(java.lang.String)}.
	 */
	@Test
	public void testIsValidUserName_OK() {
		assertTrue(UserAccountValidation.isValidUserName("maint89"));
		assertTrue(UserAccountValidation.isValidUserName("maint_89"));
		assertTrue(UserAccountValidation.isValidUserName("maint-89"));
		assertTrue(UserAccountValidation.isValidUserName("maintsebk"));
		assertTrue(UserAccountValidation.isValidUserName("maintSEBK"));
		assertTrue(UserAccountValidation.isValidUserName("maintSEBK89"));
		assertTrue(UserAccountValidation.isValidUserName("123456789"));		
	}
	
	/**
	 *  UserName is KO when:
	 * <li> length less than 6
	 * <li> length more than 15
	 * <li> there is at least a character or symbol not belong to a-z, A-Z, 0-9, _, or -
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidUserName(java.lang.String)}.
	 */
	@Test
	public void testIsValidUserName_KO() {
		assertFalse(UserAccountValidation.isValidUserName("maint"));
		assertFalse(UserAccountValidation.isValidUserName("maintsebkhn2007-2012"));
		assertFalse(UserAccountValidation.isValidUserName("maint@gmail.com"));
	}

	/**
	 * Password is OK when:
	 * <li> length from 6 - 15
	 * <li> contains characters a-z, A-Z, 0-9
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidPassword(java.lang.String)}.
	 */
	@Test
	public void testIsValidPassword_OK() {
		assertTrue(UserAccountValidation.isValidPassword("maint89"));
		assertTrue(UserAccountValidation.isValidPassword("maintsebk"));
		assertTrue(UserAccountValidation.isValidPassword("maintSEBK"));
		assertTrue(UserAccountValidation.isValidPassword("MAINTSEBK"));
		assertTrue(UserAccountValidation.isValidPassword("123456789"));
	}
	
	/**
	 *  Password is KO when:
	 * <li> length less than 6
	 * <li> length more than 15
	 * <li> there is at least a character or symbol not belong to a-z, A-Z, 0-9
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidPassword(java.lang.String)}.
	 */
	@Test
	public void testIsValidPassword_KO() {
		assertFalse(UserAccountValidation.isValidPassword("maint"));
		assertFalse(UserAccountValidation.isValidPassword("maintsebkhnk522012"));
		assertFalse(UserAccountValidation.isValidPassword("maint_89"));
		assertFalse(UserAccountValidation.isValidPassword("maint-BK"));
		assertFalse(UserAccountValidation.isValidPassword("@BKHN"));
	}

	/**
	 * Email is OK if:
	 * <li> First part characters and symbols, a-z, A-Z, 0-9, _, .
	 * <li> and followed by @, 
	 * <li> and last part characters and symbols, a-z, A-Z, .
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidEmail(java.lang.String)}.
	 */
	@Test
	public void testIsValidEmail_OK() {
		assertTrue(UserAccountValidation.isValidEmail("maint89@gmail.com"));
		assertTrue(UserAccountValidation.isValidEmail("maint_89@gmail.com"));
		assertTrue(UserAccountValidation.isValidEmail("maint.89@gmail.com"));
		assertTrue(UserAccountValidation.isValidEmail("maint.89@gmail.COM"));
		assertTrue(UserAccountValidation.isValidEmail("maint.BK_89@gmail.com"));
	}
	
	/**
	 * Email is KO if:
	 * <li> First part, there is at least a character or symbol not a-z, A-Z, 0-9, _, .
	 * <li> or don't have symbol @, 
	 * <li> or last part, there is at least a character or symbol not a-z, A-Z, .
	 * Test method for {@link eVoter.web.util.UserAccountValidation#isValidEmail(java.lang.String)}.
	 */
	@Test
	public void testIsValidEmail_KO() {
		assertFalse(UserAccountValidation.isValidEmail("maint-89@gmail.com"));
		assertFalse(UserAccountValidation.isValidEmail("maint89gmail.com"));
		assertFalse(UserAccountValidation.isValidEmail("maint89@telecom-sudparis.com"));
		assertFalse(UserAccountValidation.isValidEmail("maint-89@telecom-sudparis.com"));
	}

}
