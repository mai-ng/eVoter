package web.gui.secretary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import web.applet.RunningTimeData;

/**
 * @author maint
 *
 */
public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel menu;
	private JPanel content;

	private JLabel userName;
	private JButton btnTeacher;
	private JButton btnStudent;
	private JButton btnSubject;
	private JButton btnLogout;

	/**
	 * constructor to initialize components
	 */
	public MainPanel(){
		init();
	}
	
	/**
	 * initialize all components, set layout for this {@link MainPanel}.
	 * But only contains {@link LoginPanel} as a components.
	 */
	public void init() {
		menu = new JPanel();
		content = new LoginPanel(MainPanel.this);
		
		if (RunningTimeData.getCurrentUserName() == null) {
			userName = new JLabel("User: Guess");
		} else {
			userName = new JLabel("User: " + RunningTimeData.getCurrentUserName());
		}
		userName.setForeground(Color.YELLOW);
		
		btnLogout = new JButton("Log out");
		btnLogout.setFont(new Font("Serif", Font.BOLD, 22));		
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorderPainted(false);
		
		btnSubject = new JButton("Subject");
		btnSubject.setFont(new Font("Serif", Font.BOLD, 22));
		btnSubject.setContentAreaFilled(false);
		btnSubject.setBorderPainted(false);
		
		btnTeacher = new JButton("Teacher");
		btnTeacher.setFont(new Font("Serif", Font.BOLD, 22));
		btnTeacher.setContentAreaFilled(false);
		btnTeacher.setBorderPainted(false);
		
		btnStudent = new JButton("Student");
		btnStudent.setFont(new Font("Serif", Font.BOLD, 22));
		btnStudent.setContentAreaFilled(false);
		btnStudent.setBorderPainted(false);
		
		setLayout( new BorderLayout(10, 30));
		this.add(content);
//		actionPerformed();

	}

	/**
	 * repaint {@link MainPanel}, change its content by removing all current components and reinitializing it.
	 */
	public void restart(){
		revalidate();
		removeAll();
		init();
	}
	
	/**
	 * create GUI for {@link MainPanel}. 
	 * It contains 2 sub panels: one is menu panel and other is the content panel 
	 * (this panel can be repainted)
	 */
	public void createGUI(){
		createMenu();
		add(menu,BorderLayout.PAGE_START);
		add(content,BorderLayout.CENTER);
		actionPerformed();
	}

	/**
	 * create actions for buttons
	 */
	public void actionPerformed() {
		
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		
		btnStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel home = new JPanel();
				home.add(new JLabel(
						"Welcome to eVoter System! This is student page"));
				setContentPanel(home);
				resetButton();
            	btnStudent.setForeground(Color.BLUE);
			}
		});
		
		btnSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SubjectPanel subjectTab = new SubjectPanel();
				setContentPanel(subjectTab);
				resetButton();
				btnSubject.setForeground(Color.BLUE);
			}
		});
		
		btnTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TeacherPanel teacher = new TeacherPanel();
				setContentPanel(teacher);
				resetButton();
				btnTeacher.setForeground(Color.BLUE);
			}
		});

	}

	/**
	 * reset foreground (text) of all buttons to black
	 */
	private void resetButton(){
		btnStudent.setForeground(Color.black);
		btnSubject.setForeground(Color.black);
		btnTeacher.setForeground(Color.black);
	}
	
	// create a menu panel
	private void createMenu() {
		menu.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		menu.add(btnSubject);
		menu.add(btnTeacher);
		menu.add(btnStudent);
		menu.add(btnLogout);
		menu.add(userName);
		menu.setBackground(Color.lightGray);
	}
	
	

	/**
	 * use to repaint the {@link #content} panel.
	 * @param p is a panel
	 */
	public void setContentPanel(JPanel p) {
		content.removeAll();
		content.add(p);
		content.revalidate();
	}

	public void updateAccountName(String acc) {
		userName.setText(acc);
	}

	public void showMenu(boolean show) {
		menu.setVisible(show);
	}
}
