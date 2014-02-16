/**
 * 
 */
package web.gui.secretary.test;

import static org.junit.Assert.*;

import org.junit.Test;

import web.gui.secretary.EditUser;
import web.gui.secretary.ViewUser;
import evoter.share.model.User;

/**
 * @author maint
 *
 */
public class EditTeacher_Test {

	
	public static void main(String[] args)
	{
		User usr = new User();
		EditUser teacher = new EditUser(usr);
	}
}
