package web.gui.secretary;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SIGNIN = "Sign in";
	
	/**
	 * display error
	 */
	private JLabel lblError;
	
	/**
	 * user name field
	 */
	private JTextField txtUserName;
	
	/**
	 * password field
	 */
	private JPasswordField txtPassword;
	
	/**
	 * button "Sign in"
	 */
	private JButton btnSignIn;
	
	/**
	 * the main framework (a JPanel) of the whole web app.
	 * It contains two parts: menu and content panels.
	 */
	private MainPanel mainPanel;
	
	/**
	 * constructor log in panel where to enter user name and password to sign
	 * in.
	 * 
	 * @param main
	 */
	public LoginPanel(MainPanel main) {
		this.mainPanel = main;
		initComponents();
		buildGUI();
		actionPerformed();
	}
	
	/**
	 * design user's interface
	 */
	public void buildGUI() {
		setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 1);
		
		// Row 0: Error alert
		c.gridy = 0;
		c.weightx = 0;
		c.gridx = 1;
		c.insets = new Insets(5, 15, 5, 0);
		this.add(lblError, c);
		
		// Row 1: Password
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0;
		c.insets = new Insets(5, 15, 5, 0);
		this.add(new JLabel("User name"), c);
		
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtUserName, c);
		
		// Row 2: Email
		c.gridy = 2;
		
		c.gridx = 0;
		c.weightx = 0;
		c.insets = new Insets(5, 15, 5, 0);
		this.add(new JLabel("Password"), c);
		
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 5, 20);
		this.add(txtPassword, c);
		
		// Row 3: Create panel Buttons with BorderLayout
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipady = 30;
		c.insets = new Insets(10, 40, 3, 20);
		this.add(btnSignIn, c);
	}
	
	/**
	 * initialize fields email, password, and buttons "Sign in"
	 */
	public void initComponents() {
		//set up error field
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		
		// set up user name and password fields
		txtPassword = new JPasswordField();
		txtUserName = new JTextField();
		
		// create button log in
		btnSignIn = new JButton(SIGNIN);
	}
	
	/**
	 * create action for button "Log in"
	 */
	public void actionPerformed() {
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final String i_Usrname = getTxtUserName().getText();
				RunningTimeData.setCurrentUserName(i_Usrname);
				final String i_Password = getTxtPassword().getText();
				
				// lblError.setText("Before prepare parameters");
				// get input
				List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
				loginParams.add(new BasicNameValuePair(UserDAO.USER_NAME,
						i_Usrname));
				loginParams.add(new BasicNameValuePair(UserDAO.PASSWORD,
						i_Password));
				
				// lblError.setText("Before validate");
				// Check validation of inputs
				if (i_Usrname.equals("")) {
					System.out.print("username " + i_Usrname);
					lblError.setText("Please input your username");
				} else if (i_Password.equals("")) {
					lblError.setText("Please input your password");
				} else if (!UserValidation.isValidUserName(i_Usrname)) {
					lblError.setText("Input username is not valid");
				} else if (!UserValidation.isValidPassword(i_Password)) {
					lblError.setText("Input password is not valid");
				} else {
					// lblError.setText("Before send request");
					// Using HttpURLConnection
					String response = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.LOGIN), loginParams);
					if (response != null) {
						lblError.setText("Login successful with response: "
								+ response + ". Go to next page");
						org.json.JSONObject userkeyJson = new org.json.JSONObject(
								response);
						String userkey = userkeyJson
								.getString(UserDAO.USER_KEY);
						if (userkey != null) {
							System.out.println(userkey);
							if (extractUserType(userkey) != (int) UserType.SECRETARY) {
								lblError.setText("Only secretary has permisson to login!");
								mainPanel.doLogout(userkey);
							} else {
								RunningTimeData.setCurrentUserKey(userkey);
								mainPanel.updateAccountName(i_Usrname);
								mainPanel.buildGUI();
								mainPanel.setContentPanel(new EmptyPage("Welcome "
										+ i_Usrname + " to eVoter System!"));
							}
						}
					} else {
						lblError.setText("Login fail!!! ");
					}
				}
				
			}
		});
	}

	/**
	 * Extract user type from user_key
	 * @param userkey
	 * @return
	 */
	protected int extractUserType(String userkey) {
		String[] array = userkey.split("_");
		return Integer.parseInt(array[array.length-1]);
	}

	/**
	 * @return txtUserName is user name field
	 */
	public JTextField getTxtUserName() {
		return txtUserName;
	}
	
	/**
	 * @return txtPassword is password field
	 */
	public JTextField getTxtPassword() {
		return txtPassword;
	}
}
