package web.gui.secretary.testJUnit;

import web.gui.secretary.AddUser;
import web.gui.secretary.spec.UserGUIAbstract;
import evoter.share.model.UserType;

/**
 * Test properties of components such as visible, editable of {@link AddUser}.
 * @see JUnit_UserAbstract
 * @author maint
 */
public class JUnit_AddUser extends JUnit_UserAbstract{
	
	public void setUp(){
		super.setUp();
		frame = new AddUser(UserType.TEACHER);
	}

	/**
	 * Test is editable for {@link AddUser#getTxtFullName()},
	 * {@link AddUser#getTxtUserName()},
	 * {@link AddUser#getTxtEmail()}
	 */
	public void test_EditableTextField() {
		assertTrue(((UserGUIAbstract) frame).getTxtFullName().isEditable());
		assertTrue(((UserGUIAbstract) frame).getTxtUserName().isEditable());
		assertTrue(((UserGUIAbstract) frame).getTxtEmail().isEditable());
	}

	/**
	 * Test is empty for {@link AddUser#getTxtFullName()},
	 * {@link AddUser#getTxtUserName()},
	 * {@link AddUser#getTxtEmail()}
	 */
	public void test_EmptyTextField() {
		assertTrue(((UserGUIAbstract) frame).getTxtFullName().getText().isEmpty());
		assertTrue(((UserGUIAbstract) frame).getTxtUserName().getText().isEmpty());
		assertTrue(((UserGUIAbstract) frame).getTxtEmail().getText().isEmpty());
		
	}



}
