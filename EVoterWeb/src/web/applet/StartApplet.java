package web.applet;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import web.gui.secretary.LoginPanel;
import web.gui.secretary.MainPanel;

/**
 * Start point of the whole system.<br>
 * Applet program which contains {@link MainPanel}.
 * @author maint
 *
 */
public class StartApplet extends JApplet {

	private static final long serialVersionUID = 1L;

	public static final int APPLET_WITH = 800;

	public static final int APPLET_HEIGHT = 600;

	/**
	 * main panel 
	 */
	private MainPanel mainpanel;
	/**
	 * log in panel
	 */
	LoginPanel login;

	@Override
	public void init() {
		setSize(APPLET_WITH, APPLET_HEIGHT);
		
		mainpanel = new MainPanel();
		mainpanel.setLocation(10, 10);
		login = new LoginPanel(mainpanel);
		try {

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					getContentPane().add(mainpanel, BorderLayout.NORTH);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
