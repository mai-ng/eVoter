package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author maint<br>
 * extended by {@link GUISubjectAbstract}, {@link GUITeacherAbstract}.
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
	
	/**
	 * layout of a frame- {@link GridBagLayout}
	 */
	protected GridBagLayout gridbag;
	protected GridBagConstraints c;
	
	
	public GUIAbstract(){
		//initialize components
		initComponents();
		
		//initialize the layout
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 20, 1, 5);
	}

	/**
	 * initialize button "Close" which are used in 
	 *<li> {@link GUISubjectAbstract}; and its instants, {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject} 
	 *<li> {@link GUITeacherAbstract}; and its instants, {@link AddTeacher}, {@link EditTeacher}, and {@link ViewTeacher}
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
