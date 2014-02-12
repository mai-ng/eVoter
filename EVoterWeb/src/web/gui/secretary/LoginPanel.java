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
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String SIGNIN = "Sign in";
	public static final String REGISTER = "New account";

	private JLabel lblError;

	private JTextField txtUserName;
	private JPasswordField txtPassword;

	/**
	 * button "Sign in"
	 */
	private JButton btnSignIn;

	/**
	 * button "Register"
	 */
	private JButton btnRegister;

	GridBagConstraints c;

	MainPage main;

	/**
	 * Create the panel.
	 */
	public LoginPanel(MainPage main) {
		this.main = main;
		this.main.showMenu(false);
		// this.password = password;

		initComponents();
		// design user's interface
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
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

		// Row 3,4: Create panel Buttons with BorderLayout
		c.gridy = 3;
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipady = 10;
		c.insets = new Insets(10, 40, 3, 20);
		this.add(btnSignIn, c);

		c.gridy = 4;
		c.gridx = 1;
		c.weightx = 0.5;
		c.ipady = 10;
		c.insets = new Insets(3, 40, 5, 20);
		this.add(btnRegister, c);
	}

	/**
	 * initialize fields email, password, and buttons "Sign in" and "Register"
	 */
	public void initComponents() {
		lblError = new JLabel();
		lblError.setForeground(Color.RED);

		// create text fields
		txtPassword = new JPasswordField();
		txtUserName = new JTextField();

		// create buttons
		btnSignIn = new JButton(SIGNIN);
		btnSignIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// String USER_KEY = null;
				// lblError.setText("After clicked button");
				// EVoterHttpClient client = new EVoterHttpClient();
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
							RunningTimeData.setCurrentUserKey(userkey);
							main.updateAccountName(i_Usrname);
							main.createGUI();
							main.showMenu(true);
							main.setContentPanel(new EmptyPage("Welcome "
									+ i_Usrname + " to eVoter System!"));
						}
					} else {
						lblError.setText("Login fail!!! ");
					}
					// Using HttpClient
					// int reponseStatus = client.post(
					// Configuration.get_urlLogin(), loginParams);
					// System.out.println(reponseStatus);

					// change to the page if "View list of subjects" if login
					// succeeded
					// if (reponseStatus == 200) {
					// String content = client.getResponseContent();
					// System.out.println(content);
					// try {
					// // JSONObject item = new JSONObject(content);
					// // USER_KEY = item.getString(UserDAO.USER_KEY);
					// Login.this.removeAll();
					// ViewListSubject listOfSubjects = new ViewListSubject();
					// Login.this.add(listOfSubjects);
					// Login.this.revalidate();
					// Login.this.repaint();
					// } catch (JSONException e1) {
					// e1.printStackTrace();
					// lblError.setText("No account with input username and password");
					// }
					// } else {
					// lblError.setText("Connect to server failed!");
					// }
				}

			}
		});
		btnRegister = new JButton(REGISTER);

	}

	/**
	 * @return the txtUserName
	 */
	public JTextField getTxtUserName() {
		return txtUserName;
	}

	/**
	 * @return the txtPassword
	 */
	public JTextField getTxtPassword() {
		return txtPassword;
	}
}
