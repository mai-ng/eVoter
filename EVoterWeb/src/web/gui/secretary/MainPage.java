package web.gui.secretary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import web.applet.RunningTimeData;

public class MainPage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel menu;
	private JPanel content;

	private JLabel accountName;
	private JButton btnTeacher;
	private JButton btnStudent;
	private JButton btnSubject;
	private JButton btnLogout;

	private GridBagLayout gridbag;
	private GridBagConstraints c;

	public MainPage(){
		init();
		
	}
	public void init() {
		menu = new JPanel();
		content = new LoginPanel(MainPage.this);
		if (RunningTimeData.getCurrentUserName() == null) {
			accountName = new JLabel("User: Guess");
		} else {
			accountName = new JLabel("User: " + RunningTimeData.getCurrentUserName());
		}
		accountName.setForeground(Color.YELLOW);
		
		ImageIcon icon = createImageIcon("/resource/icon.jpeg");
		btnLogout = new JButton(icon);
		btnSubject = new JButton("Subject",icon);
		btnTeacher = new JButton("Teacher",icon);
		btnStudent = new JButton("Student",icon);
		
		setLayout(new BorderLayout());
		this.add(content);
		actionPerformed();

	}

	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MainPage.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
  
	public void restart(){
		revalidate();
		removeAll();
		init();
	}
	
	public void createGUI(){
		createMenu();
		setLayout( new BorderLayout(10, 30));
		add(menu,BorderLayout.PAGE_START);
		add(content,BorderLayout.CENTER);
//		gridbag = new GridBagLayout();
//		c = new GridBagConstraints();
//		this.setLayout(gridbag);
//
//		c.insets = new Insets(1, 1, 1, 0);
//		c.anchor=GridBagConstraints.LINE_START;
//		c.fill = GridBagConstraints.NONE;
//		this.add(menu, c);
//
//		c.gridy = 1;
//		this.add(content, c);
	}

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
			}
		});
		
		btnSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SubjectPanel subjectTab = new SubjectPanel();
				setContentPanel(subjectTab);

			}
		});
		
		btnTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TeacherPanel teacher = new TeacherPanel();
				setContentPanel(teacher);

			}
		});

	}

	// create a menu panel
	private void createMenu() {
//		menu.setLayout(new GridLayout(1, 0, 10, 0));
		menu.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		menu.add(accountName);
		menu.add(btnSubject);
		menu.add(btnTeacher);
		menu.add(btnStudent);
		menu.add(btnLogout);
		menu.setBackground(Color.lightGray);
	}

	//content panel
	public void setContentPanel(JPanel p) {
		content.removeAll();
		content.add(p);
		content.revalidate();
	}

	public void updateAccountName(String acc) {
		accountName.setText(acc);
	}

	public void showMenu(boolean show) {
		menu.setVisible(show);
	}
}
