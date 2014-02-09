package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public MainPage() {

		init();
		menuGUI();
		contentGUI();

		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.insets = new Insets(5, 1, 1, 5);
		c.gridwidth = 1;
		this.add(menu, c);

		c.gridx = 1;
		c.gridwidth = 4;
		this.add(content, c);

	}

	// initialize elements
	public void init() {

		// initialize panels
		menu = new JPanel();
		content = new JPanel();
		if (RunningTimeData.getCurrentUserName() == null) {
			accountName = new JLabel("Guess");
		} else {
			accountName = new JLabel(RunningTimeData.getCurrentUserName());
		}
		// initialize buttons
		// btnHome = new JButton("Home");
		// btnHome.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// JPanel home = new JPanel();
		// home.add(new JLabel(
		// "Welcome to eVoter System! This is home page"));
		// setContent(home);
		// }
		// });
		btnLogout = new JButton("Disconnect");
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setContent(new LoginPanel(MainPage.this));
			}
		});
		btnStudent = new JButton("Student");
		btnStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel home = new JPanel();
				home.add(new JLabel(
						"Welcome to eVoter System! This is student page"));
				setContent(home);
			}
		});
		btnSubject = new JButton("Subject");
		btnSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JPanel home = new JPanel();
				// home.add(new JLabel(
				// "Welcome to eVoter System! This is subject page"));
				SubjectPanel subjectTab = new SubjectPanel();
				setContent(subjectTab);

			}
		});
		btnTeacher = new JButton("Teacher");
		btnTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel home = new JPanel();
				home.add(new JLabel(
						"Welcome to eVoter System! This is Teacher page"));
				setContent(home);

			}
		});

	}

	// create a menu
	private void menuGUI() {
		menu.setLayout(new GridLayout(0, 1, 10, 0));
		menu.add(accountName);
		menu.add(btnSubject);
		menu.add(btnTeacher);
		menu.add(btnStudent);
		menu.add(btnLogout);

	}

	// create content of page
	private void contentGUI() {
		content.removeAll();
		content.add(new LoginPanel(MainPage.this));
		content.revalidate();
	}

	public void setContent(JPanel p) {
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
