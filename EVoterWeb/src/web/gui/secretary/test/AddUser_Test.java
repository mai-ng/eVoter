package web.gui.secretary.test;

import evoter.share.model.UserType;
import web.gui.secretary.AddUser;


/**
 * Test add
 * @author maint
 *
 */
public class AddUser_Test extends UserGUI_TestAbstract{
	
	public void setUp(){
		super.setUp();
		frame = new AddUser(UserType.TEACHER);
	}

	@Override
	public void test_EditableTextField() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test_EmptyTextField() {
		// TODO Auto-generated method stub
		
	}



}
