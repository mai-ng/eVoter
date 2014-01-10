package eVoter.web.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import eVoter.web.gui.secretary.AddSubject;
import eVoter.web.gui.secretary.AddTeacher;
import eVoter.web.gui.secretary.EditSubject;
import eVoter.web.gui.secretary.EditTeacher;
import eVoter.web.gui.secretary.Login;
import eVoter.web.gui.secretary.MenuBarTop;
import eVoter.web.gui.secretary.ViewListTeacher;
import eVoter.web.gui.secretary.ViewSubject;
import eVoter.web.gui.secretary.ViewTeacher;


public class MainApplet extends JApplet{

	private static final long serialVersionUID = 1L;
	
	private AddSubject addSubject;
	private EditSubject editSubject;
	private ViewSubject viewSubject;
	
	private AddTeacher addTeacher;
	private EditTeacher editTeacher;
	private ViewTeacher viewTeacher;
	
	private ViewListTeacher viewlist;
	
	private Login login;

   @Override
   public void init () {
	   setSize(600, 600);
	   
	   addSubject = new AddSubject();
	   editSubject = new EditSubject();
	   viewSubject = new ViewSubject();
	   
	   addTeacher = new AddTeacher();
	   editTeacher = new EditTeacher();
	   viewTeacher = new ViewTeacher();
	   
	   viewlist = new ViewListTeacher();
	   
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
