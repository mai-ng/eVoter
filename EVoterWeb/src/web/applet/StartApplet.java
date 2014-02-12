package web.applet;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.LoginPanel;
import web.gui.secretary.MainPage;

public class StartApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	// private Login login;
	private MainPage mainpage;
	LoginPanel login;

	@Override
	public void init() {
		setSize(600, 600);
		mainpage = new MainPage();
		mainpage.setLocation(10, 10);
		login = new LoginPanel(mainpage);
		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
//					setContentPane(mainpage);
					getContentPane().add(mainpage,BorderLayout.NORTH); 
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
