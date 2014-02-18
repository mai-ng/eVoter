package web.gui.secretary.testJUnit;

import web.gui.secretary.spec.UserGUIAbstract;
import evoter.share.model.User;
import evoter.share.model.UserType;

/**.
 * Test properties of components such as visible, editable...<br
 * Extends in {@link JUnit_AddUser}, {@link JUnit_ViewUser}, {@link JUnit_EditUser}.
 * @author maint
 *
 */
public abstract class JUnit_UserAbstract extends JUnit_GUIAbstract{

protected static User userTest;;
	
	public void setUp() {
		super.setUp();
		userTest = enParams.getUser(UserType.STUDENT, "anna@gmail.com");
	}

	/**
	 * Test get text for {@link UserGUIAbstract#getLblFullName()},
	 * {@link UserGUIAbstract#getLblUserName()},
	 * {@link UserGUIAbstract#getLblEmail()}
	 */
	public void test_GetLable() {
		assertEquals("Full name", ((UserGUIAbstract) frame).getLblFullName().getText());
		assertEquals("Username", ((UserGUIAbstract) frame).getLblUserName().getText());
		assertEquals("Email", ((UserGUIAbstract) frame).getLblEmail().getText());
	}

	/**
	 * Test is showing for {@link UserGUIAbstract#getLblFullName()},
	 * {@link UserGUIAbstract#getLblUserName()},
	 * {@link UserGUIAbstract#getLblEmail()}
	 */
	public void test_IsShowingLable() {
		assertTrue(((UserGUIAbstract) frame).getLblFullName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getLblUserName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getLblEmail().isShowing());
	}

	/**
	 * Test is showing for {@link UserGUIAbstract#getTxtFullName()},
	 * {@link UserGUIAbstract#getTxtUserName()},
	 * {@link UserGUIAbstract#getTxtEmail()}
	 */
	public void test_IsShowingTextField() {
		assertTrue(((UserGUIAbstract) frame).getTxtFullName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getTxtUserName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getTxtEmail().isShowing());		
	}

	/**
	 * Test is showing for {@link UserGUIAbstract#getBtnClose()},
	 * {@link UserGUIAbstract#getBtnSave()},
	 */
	public void test_IsShowingButtons() {
		assertTrue(frame.getBtnSave().isShowing());
		assertTrue(((UserGUIAbstract) frame).getBtnClose().isShowing());
		
	}


}
