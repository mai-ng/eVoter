package mai;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import mai.gui.MyMenu;

public class MenuTest extends JApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MyMenu menu = new MyMenu();
	
	 @Override
	   public void init () {
		   setSize(400, 400);
	      try {
	         // Run GUI codes in the Event-Dispatcher thread for thread safety
	         // Use invokeAndWait() so that init() does not exit before GUI constructed
	         SwingUtilities.invokeAndWait(new Runnable() {
	            @Override
	            public void run() {
	            	MenuTest.this.setContentPane(menu);
//	               final Login login = new Login();
//	               LoginPage.this.setContentPane(login);
//	               JButton button = login.getBtnLogIn();
//	               button.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						Welcome welcome = new Welcome();
//						welcome.setLblUsername(login.getTxtUserName());
//						LoginPage.this.repaint();
//						LoginPage.this.setContentPane(welcome);
//						
//					}
//				});
	            }
	         });
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

}
