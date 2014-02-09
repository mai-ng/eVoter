package web.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.Login;

public class StartApplet extends JApplet {
	
	private static final long serialVersionUID = 1L;
	
	private Login login;
	
	@Override
	public void init() {
		setSize(600, 600);
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
