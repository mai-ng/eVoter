package web.applet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.LoginPanel;
import web.gui.secretary.MainPage;

public class StartApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	// private Login login;
	private MainPage mainpage;

	@Override
	public void init() {
		setSize(600, 600);
		mainpage = new MainPage();
		LoginPanel login = new LoginPanel(mainpage);
		mainpage.setContent(login);
		// login = new Login();
		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					setContentPane(mainpage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
