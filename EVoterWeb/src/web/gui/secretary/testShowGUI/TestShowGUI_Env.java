package web.gui.secretary.testShowGUI;

import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.testJUnit.TestEnvironment;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserType;

/**
 * create environment to skip log in step in purpose of showing user interface for:<br>
 * {@link AddSubject}, {@link AddUser},
 * {@link EditSubject},{@link EditUser},
 * {@link ViewSubject}, {@link ViewUser}.
 * @author maint
 *
 */
public abstract class TestShowGUI_Env {
	
	protected static TestEnvironment enParams;
	protected static Subject subTest;
	protected static User userTest;
	
	public static void setUpLogIn() {
		enParams = new TestEnvironment();
		enParams.dologin();
	}
	
	public static void setUpSubject() {
		setUpLogIn();
		subTest = enParams.getSubject("Object");
	}
	
	public static void setUpUser() {
		setUpLogIn();
		userTest = enParams.getUser(UserType.STUDENT, "nguyen.van@telecom-sudparis.eu");
	}
}
