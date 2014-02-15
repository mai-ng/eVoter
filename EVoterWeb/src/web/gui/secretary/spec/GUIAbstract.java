package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.EditUser;
import web.gui.secretary.ViewSubject;
import web.gui.secretary.ViewUser;

/**
 * @author maint<br>
 * extended by {@link SubjectGUIAbstract}, {@link UserGUIAbstract}.
 * Set layout for a frame, and initialize the button "Close" for all frames.
 */
public abstract class GUIAbstract extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static final String INVITE = "Invite";
	public static final String SAVE = "Save";
	public static final String IMPORT_TEACHER = "Import";
	public static final String IMPORT_STUDENT = "Import";
	public static final String CLOSE = "Close";
	
	/**
	 * button "Close" on a JFrame
	 */
	protected JButton btnClose;
	
//	protected JLabel lbMessage;
	
	/**
	 * layout of a frame- {@link GridBagLayout}
	 */
	protected GridBagLayout gridbag;
	protected GridBagConstraints c;
	
	
	public GUIAbstract(){
		//initialize components
		
		
		//initialize the layout
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
//		c.insets = new Insets(10, 20, 1, 5);
		c.insets = new Insets(5, 10, 5, 5);
//		initComponents();
	}

	/**
	 * initialize button "Close" which are used in 
	 *<li> {@link SubjectGUIAbstract}; and its instants, {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject} 
	 *<li> {@link UserGUIAbstract}; and its instants, {@link AddUser}, {@link EditUser}, and {@link ViewUser}
	 */
	protected void initComponents() {
		//button "Close"
		btnClose = new JButton(CLOSE);
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
