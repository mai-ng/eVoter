package web.gui.secretary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * the main framework (a {@link JPanel}) for the whole web application.
 * Contains 2 parts: menu {@link JPanel} and content {@link JPanel}.
 * <li> menu part: fixed except that it isn't appeared along with {@link LoginPanel}.
 * <li> content part: changeable when an action is called. 
 * @author maint
 *
 */
public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * fixed part which contains Subject, Teacher, Student and Log out tabs.
	 */
	private JPanel menu;
	
	/**
	 * changeable part which can be {@link SubjectTab}, {@link UserTab}, or {@link LoginPanel}.
	 */
	private JPanel content;

	/**
	 * current user name
	 */
	private JLabel userName;
	
	/**
	 * Teacher tab on menu
	 */
	private JButton btnTeacher;
	
	/**
	 * Student tab on menu
	 */
	private JButton btnStudent;
	
	/**
	 * Subject tab on menu
	 */
	private JButton btnSubject;
	
	/**
	 * Log out tab on menu
	 */
	private JButton btnLogout;

	/**
	 * constructor to initialize components,
	 * create the first page of web app which is the "Log in" page.
	 */
	public MainPanel(){
		init();
	}
	
	/**
	 * initialize all components, set layout for this {@link MainPanel}.
	 * But only contains {@link LoginPanel} as a component.
	 */
	public void init() {
		
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
		
		menu = new JPanel();
		content = new LoginPanel(MainPanel.this);
		setLayout( new BorderLayout(10, 30));
		this.add(content);
	}

	
	/**
	 * create GUI for {@link MainPanel}. 
	 * It contains 2 sub panels: one is menu panel and other is the content panel.
	 */
	public void buildGUI(){
		createMenu();
		add(menu,BorderLayout.PAGE_START);
		add(content,BorderLayout.CENTER);
		actionPerformed();
	}

	/**
	 * create actions for menu tabs: Subject, Teacher, Student, Log out tabs.
	 * <li> Click Subject tab -> the content panel is {@link SubjectTab}.
	 * <li> Click Teacher tab -> the content panel is {@link UserTab} with users are teachers.
	 * <li> Click Student tab -> the content panel is {@link UserTab} with users are students.
	 * <li> Click Log out tab -> the page change to {@link LoginPanel}.
	 */
	public void actionPerformed() {
		
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doLogout(RunningTimeData.getCurrentUserKey());
				restart();
			}
		});
		
		btnStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserTab teacher = new UserTab(UserType.STUDENT);
				setContentPanel(teacher);
				resetButton();
				btnStudent.setForeground(Color.BLUE);
			}
		});
		
		btnSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SubjectTab subjectTab = new SubjectTab();
				setContentPanel(subjectTab);
				resetButton();
				btnSubject.setForeground(Color.BLUE);
			}
		});
		
		btnTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserTab teacher = new UserTab(UserType.TEACHER);
				setContentPanel(teacher);
				resetButton();
				btnTeacher.setForeground(Color.BLUE);
			}
		});

	}
	

	/**
	 * repaint {@link MainPanel}, change its content by removing all current components and 
	 * change it back to {@link LoginPanel} as the beginning.
	 * Used for Log out action.
	 */
	private void restart(){
		revalidate();
		removeAll();
		init();
	}

	/**
	 * reset foreground (text) of all buttons to black.
	 * Used when click a tab on the menu, this tab color changes to blue and others are black.
	 */
	private void resetButton(){
		btnStudent.setForeground(Color.black);
		btnSubject.setForeground(Color.black);
		btnTeacher.setForeground(Color.black);
	}
	
	/**
	 * create a menu panel which contains "Subject", "Teacher", "Student", "Log out" tabs 
	 * and user name field.
	 */
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
		content.revalidate();
		content.removeAll();
		content.add(p);
	}

	/**
	 * Use when change user using system. <br>
	 * Use in {@link LoginPanel#doLogin()}.
	 * @param acc
	 */
	public void updateAccountName(String acc) {
		userName.setText(acc);
	}
	
	/**
	 *Send a logout request to server <br>
	 *<li> {@link MainPanel#btnLogout} click
	 *<li> {@link LoginPanel#doLogin()} in case of a teacher or student try to login web app
	 * @param userkey
	 */
	protected void doLogout(String userkey) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				userkey));
			String response = EVoterHTTPRequest.excutePost(
					RequestConfig.getURL(URIRequest.LOGOUT), params);
			System.out.println(response);
	}
}
