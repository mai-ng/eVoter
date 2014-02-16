package web.gui.secretary.test;

import web.gui.secretary.spec.UserGUIAbstract;
import evoter.share.model.User;
import evoter.share.model.UserType;

/**
 * Test properties of components such as visible, editable...
 * Extends in {@link AddUser_Test}, {@link ViewUser_Test}, {@link EditUser_Test}.
 * @author maint
 *
 */
public abstract class UserGUI_TestAbstract extends GUI_TestAbstract{

protected static User userTest;;
	
	public void setUp() {
		super.setUp();
		userTest = enParams.getUser(UserType.TEACHER, "paul.gibson@telecom-sudparis.eu");
	}

	@Override
	public void test_GetLable() {
		assertEquals("Full name", ((UserGUIAbstract) frame).getLblFullName().getText());
		assertEquals("User name", ((UserGUIAbstract) frame).getLblUserName().getText());
		assertEquals("Email", ((UserGUIAbstract) frame).getLblEmail().getText());
	}

	@Override
	public void test_IsShowingLable() {
		assertTrue(((UserGUIAbstract) frame).getLblFullName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getLblUserName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getLblEmail().isShowing());
	}

	@Override
	public void test_IsShowingTextField() {
		assertTrue(((UserGUIAbstract) frame).getTxtFullName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getTxtUserName().isShowing());
		assertTrue(((UserGUIAbstract) frame).getTxtEmail().isShowing());		
	}

	@Override
	public void test_IsShowingButtons() {
		assertTrue(frame.getBtnSave().isShowing());
		assertTrue(((UserGUIAbstract) frame).getBtnClose().isShowing());
		
	}


}
