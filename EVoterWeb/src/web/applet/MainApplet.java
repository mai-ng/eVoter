package web.applet;

import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.AddSubject;
import web.gui.secretary.AddTeacher;
import web.gui.secretary.EditSubject;
import web.gui.secretary.EditTeacher;
import web.gui.secretary.Login;
import web.gui.secretary.ViewListSubject;
import web.gui.secretary.ViewListTeacher;
import web.gui.secretary.ViewSubject;
import web.gui.secretary.ViewTeacher;


public class MainApplet extends JApplet {

	private static final long serialVersionUID = 1L;

//	private AddSubject addSubject;
//	private EditSubject editSubject;
//	private ViewSubject viewSubject;
//
//	private AddTeacher addTeacher;
//	private EditTeacher editTeacher;
//	private ViewTeacher viewTeacher;
//
//	private ViewListSubject viewlist;
//	private ViewListTeacher listTeachers;

	private Login login;

	@Override
	public void init() {
		setSize(600, 600);

//		try {
//			addSubject = new AddSubject();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	
//		try {
//			editSubject = new EditSubject();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		viewSubject = new ViewSubject();
//
//		addTeacher = new AddTeacher();
//		editTeacher = new EditTeacher();
//		viewTeacher = new ViewTeacher();
//
//		viewlist = new ViewListSubject();
//		listTeachers = new ViewListTeacher();

		login = new Login();
		
		
		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					setContentPane(login);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
