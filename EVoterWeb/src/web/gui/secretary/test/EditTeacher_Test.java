/**
 * 
 */
package web.gui.secretary.test;

import static org.junit.Assert.*;

import org.junit.Test;

import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewTeacher;
import evoter.share.model.User;

/**
 * @author maint
 *
 */
public class EditTeacher_Test {

	
	public static void main(String[] args)
	{
		User usr = new User();
		EditTeacher teacher = new EditTeacher(usr);
	}
}
