package eVoter.web.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import eVoter.web.gui.secretary.AddSubject;


public class MainApplet extends JApplet{

	private static final long serialVersionUID = 1L;
	private AddSubject addSubject;

   @Override
   public void init () {
	   setSize(600, 600);
	   addSubject = new AddSubject();
      try {
      
         SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
            	setContentPane(addSubject);
            }
         });
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
