package web.applet;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.LoginPanel;
import web.gui.secretary.MainPanel;

public class StartApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	// private Login login;
	private MainPanel mainpage;
	LoginPanel login;

	@Override
	public void init() {
		setSize(800, 600);
		mainpage = new MainPanel();
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
