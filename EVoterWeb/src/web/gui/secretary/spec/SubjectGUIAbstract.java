package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.EditUser;
import web.gui.secretary.ViewSubject;
import web.util.EVoterHTTPRequest;
import web.util.ReadFileByClick;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * extended by {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.<br>
 * Create common components, initialize them, and define a layout and user
 * interface. <br>
 * 
 * @author maint<br>
 * 
 */
public abstract class SubjectGUIAbstract extends GUIAbstract {

	private static final long serialVersionUID = 1L;

	protected JLabel lblTitle;
	protected JTextField txtTitle;
	protected JTextArea listTeacherView;
	protected JTextArea listStudentView;

	protected JButton btnAddTeacher;
	protected JButton btnAddStudent;
	protected ArrayList<String> listTeacherEmails;
	protected ArrayList<String> listStudentEmails;

	public SubjectGUIAbstract() {
		super();
		try {
			importFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		buildGUI();
	}

	/**
	 * initialize all components which are used in {@link AddSubject},
	 * {@link EditSubject}, {@link ViewSubject}.
	 */
	public void initComponents() {
		super.initComponents();
		lblTitle = new JLabel("Title: ");
		txtTitle = new JTextField();

		listTeacherView = new JTextArea();
		listStudentView = new JTextArea();

		btnAddStudent = new JButton("Import");
		btnAddTeacher = new JButton("Import");
	}

	/**
	 * design user interface for {@link AddSubject}, {@link EditSubject}, and
	 * {@link ViewSubject}.
	 */
	public void buildGUI() {
		// show frame
		setSize(700, 500);
		setLocationRelativeTo(null);
		setVisible(true);

		// Row 0: Title
		c.weightx = 0;
		this.add(lblTitle, c);
		c.weightx = 0.5;
		c.ipady = 10;
		this.add(txtTitle, c);

		// Row 1: Space
		c.gridy = 1;
		c.weightx = 0;
		this.add(new JLabel("  "), c);

		// setup Teacher panel
		c.weighty = 1;
		JPanel teacherPanel = new JPanel();
		teacherPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Teacher"));

		teacherPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		teacherPanel.add(new JScrollPane(listTeacherView,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		teacherPanel.add(btnAddTeacher, c);

		// Row 2: Teacher table
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(teacherPanel, c);

		// Setup student panel
		c.gridwidth = 1;
		c.weighty = 1;
		JPanel studentPanel = new JPanel();
		studentPanel.setBorder(BorderFactory.createTitledBorder("Student"));

		studentPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		studentPanel.add(new JScrollPane(listStudentView,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		studentPanel.add(btnAddStudent, c);

		// Row 3: Students table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(studentPanel, c);

		// Row 4: Create Button
		c.gridy = 5;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnSave, c);
	}

	/**
	 * import a file to {@link JTextArea} when click a button. This function is
	 * used in {@link AddSubject}, {@link EditSubject} classes.
	 * 
	 * @throws IOException
	 */
	public void importFile() throws IOException {
		btnAddTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ReadFileByClick.readFile(listTeacherView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		btnAddStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ReadFileByClick.readFile(listStudentView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	/**
	 * Check pre-condition to send request to server.<br>
	 * Used in {@link AddUser}, {@link EditUser} when click button "Add" or
	 * "Save".
	 * 
	 * @return true if: <li>full name field is valid. <li>user name field is
	 *         valid. <li>and email field is valid.<br>
	 *         else false.
	 */
	protected boolean readyToSendRequest() {
		if (lblTitle.getText().equals("")) {
			Utils.informDialog("Subject title is empty!");
			return false;
		} else {
			return checkListEmails();
		}
	}

	private boolean checkListEmails() {
		getListEmailUsers(UserType.STUDENT,listStudentEmails);
		getListEmailUsers(UserType.TEACHER,listTeacherEmails);
		if(!validListEmails(listStudentEmails,listStudentView)) return false;
		if(!validListEmails(listTeacherEmails,listTeacherView)) return false;
		return true;
	}

	private boolean validListEmails(ArrayList<String> listEmails,
			JTextArea textArea) {
		StringBuffer bf = new StringBuffer();
		String textAreaContent = textArea.getText();
		
		String[] arrayEmails = textAreaContent.split(", ");
		for(int i=0;i<arrayEmails.length;i++){
			String email = arrayEmails[i].replace(" ", "");
			if(!listEmails.contains(email)) bf.append(email+"\n");
		}
		if(!bf.toString().equals("")){
			Utils.informDialog("There are some email does not exist in system: "+bf.toString());
			return false;
		}
		return true;
	}

	private void getListEmailUsers(long userType,ArrayList<String> listEmails) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY, RunningTimeData
				.getCurrentUserKey()));
		params.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID, String
				.valueOf(userType)));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USER), params);
		if (listTeacherResponse == null) {
			Utils.informDialog("Get list students fail!!!!");
		} else {
			System.out.println(listTeacherResponse);
			JSONArray array = new JSONArray(listTeacherResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				listEmails.add(ob.getString(UserDAO.EMAIL));
			}
		}

	}
	/**
	 * @return the txtTitle is title field of a subject
	 */
	public JTextField getTxtTitle() {
		return txtTitle;
	}

}