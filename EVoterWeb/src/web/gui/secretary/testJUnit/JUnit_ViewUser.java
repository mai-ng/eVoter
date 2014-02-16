/**
 * 
 */
package web.gui.secretary.testJUnit;

import web.gui.secretary.ViewUser;
import web.gui.secretary.spec.UserGUIAbstract;
import evoter.share.model.UserType;

/**
 * Test properties of components such as visible, editable of {@link ViewUser}.
 * @see JUnit_EditUser
 * @author maint
 */
public class JUnit_ViewUser extends JUnit_EditUser{
	public void setUp(){
		super.setUp();
		frame = new ViewUser(userTest);
	}
	
	/**
	 * Test is not editable for {@link ViewUser#getTxtFullName()},
	 * {@link ViewUser#getTxtUserName()},
	 * {@link ViewUser#getTxtEmail()}
	 */
	public void test_EditableTextField() {
		assertFalse(((UserGUIAbstract) frame).getTxtFullName().isEditable());
		assertFalse(((UserGUIAbstract) frame).getTxtUserName().isEditable());
		assertFalse(((UserGUIAbstract) frame).getTxtEmail().isEditable());
	}
	
	/**
	 * Test NOT is showing for {@link ViewUser#getBtnClose()},
	 * {@link ViewUser#getBtnSave()} when user is a teacher.
	 */
	public void test_IsShowingButtons() {
		userTest = enParams.getUser(UserType.TEACHER, "paul.gibson@telecom-sudparis.eu");
		frame = new ViewUser(userTest);
		assertFalse(frame.getBtnSave().isShowing());
		assertFalse(((UserGUIAbstract) frame).getBtnClose().isShowing());
		
	}
}
