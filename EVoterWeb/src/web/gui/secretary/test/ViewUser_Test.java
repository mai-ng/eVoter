/**
 * 
 */
package web.gui.secretary.test;

import web.gui.secretary.ViewUser;
import evoter.share.model.User;
import evoter.share.model.UserType;

/**
 * Test view
 * @author maint
 * 
 */
public class ViewUser_Test {
	static TestEnvironment login;

	public static void main(String[] args)
	{
		login = new TestEnvironment();
		login.dologin();
//		teacherView();
		studentView();
	}

	private static void studentView() {
		User student = login.getUser(UserType.STUDENT, "diepkhac@gmail.com");
		if(student!=null){
			ViewUser studentView = new ViewUser(student);
		}
	}

	private static void teacherView() {
		User teacher = login.getUser(UserType.TEACHER, "mainguyen@gabo.com");
		if(teacher!=null){
			ViewUser teacherView = new ViewUser(teacher);
		}
	}
}
