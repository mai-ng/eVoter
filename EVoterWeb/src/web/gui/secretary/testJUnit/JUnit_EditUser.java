package web.gui.secretary.testJUnit;
import web.gui.secretary.EditUser;
import web.gui.secretary.spec.UserGUIAbstract;

/**
 * Test properties of components such as visible, editable of {@link EditUser}.
 * @see JUnit_AddUser
 * @author maint
 */
public class JUnit_EditUser extends JUnit_AddUser {
	public void setUp(){
		super.setUp();
		frame = new EditUser(userTest);
	}

	/**
	 * Test is empty for {@link EditUser#getTxtFullName()},
	 * {@link EditUser#getTxtUserName()},
	 * {@link EditUser#getTxtEmail()}
	 */
	public void test_EmptyTextField() {
		assertFalse(((UserGUIAbstract) frame).getTxtFullName().getText().isEmpty());
		assertFalse(((UserGUIAbstract) frame).getTxtUserName().getText().isEmpty());
		assertFalse(((UserGUIAbstract) frame).getTxtEmail().getText().isEmpty());
		
	}
}
