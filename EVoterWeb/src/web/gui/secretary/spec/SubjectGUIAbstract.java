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
import web.util.ReadFileToTextArea;
import web.util.RequestConfig;
import web.util.Utils;
import web.util.UserAccountValidation;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * extended by {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.<br>
 * Create common components, initialize them, and define a layout and design user
 * interface.
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
	/**
	 * list teachers' emails in databse.
	 */
	protected ArrayList<String> listTeacherEmails;
	/**
	 * list students' emails in database.
	 */
	protected ArrayList<String> listStudentEmails;
	/**
	 * list both students and teachers emails get from input.
	 */
	protected ArrayList<String> listInputEmails;

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

		listTeacherEmails = new ArrayList<String>();
		listStudentEmails = new ArrayList<String>();
		listInputEmails = new ArrayList<String>();
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
					ReadFileToTextArea.readFile(listTeacherView);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		btnAddStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ReadFileToTextArea.readFile(listStudentView);
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

	/**
	 * Check validation list input email
	 * @return <br>false if list input email of teacher is not valid
	 * <br>false if list input email of student is not valid
	 * <br> true if both are valid.
	 */
	private boolean checkListEmails() {
		listStudentEmails.clear();
		listTeacherEmails.clear();
		listInputEmails.clear();
		if (!validListEmails(UserType.STUDENT)){
			return false;
		}
		if (!validListEmails(UserType.TEACHER)){
			return false;
		}
		return true;
	}

	/**
	 * Valid input email of user
	 * @param userTypeID
	 * @return <br> userTypeID = {@link UserType#TEACHER} Check list input emails of teachers, if there is any email which is not a teacher's email of system, return false; 
	 * <br> userTypeID = {@link UserType#STUDENT} Check list input emails of students, if there is any email which is not a student's email of system, return false;
	 */
	private boolean validListEmails(long userTypeID) {
		getListEmailUsers(userTypeID);
		ArrayList<String> listExtractEmails = getListEmailFromTextView(userTypeID);
		if(listExtractEmails==null) {
			Utils.informDialog("Cannot get list email from text are");
			return false;
		}
		for (int i = 0; i < listExtractEmails.size(); i++) {
			if (userTypeID == UserType.TEACHER) {
				if (!listTeacherEmails.contains(listExtractEmails.get(i)))
				{
					Utils.informDialog(listExtractEmails.get(i)+" is not a professor of system");
					return false;
				}
				else
					listInputEmails.add(listExtractEmails.get(i));
			} else if (userTypeID == UserType.STUDENT) {
				if (!listStudentEmails.contains(listExtractEmails.get(i)))
					{
					Utils.informDialog(listExtractEmails.get(i)+" is not a student of system");
					return false;
					}
				else
					listInputEmails.add(listExtractEmails.get(i));
			}
		}
		return true;
	}

	/**
	 * Get list email of user from textarea
	 * @param userTypeID
	 * @return <br> userTypeID = {@link UserType#TEACHER} List email of teacher get from input of {@link SubjectGUIAbstract#listTeacherView}
	 *<br> userTypeID = {@link UserType#STUDENT} List email of student get from input of {@link SubjectGUIAbstract#listStudentView}
	 */
	private ArrayList<String> getListEmailFromTextView(long userTypeID) {
		ArrayList<String> listExtractEmails = new ArrayList<String>();
		String textAreas = null;
		if (userTypeID == UserType.TEACHER) {
			textAreas = listTeacherView.getText();
		} else if (userTypeID == UserType.STUDENT) {
			textAreas = listStudentView.getText();
		}
		if (textAreas != null) {
			String[] array = textAreas.split("\n");
			for(int i=0;i<array.length;i++){
				String email = array[i].replace(" ", "").replace("\t", "");
				if(UserAccountValidation.isValidEmail(email)) listExtractEmails.add(email);
				else {
					return null;
				}
			}
		}
		return listExtractEmails;
	}

	/**
	 * Get list users' emails of subject from database: <br>
	 * - userTypeID = {@link UserType#TEACHER} get list email of teacher
	 * <br>- userTypeID = {@link UserType#STUDENT} get list email of student 
	 * @param userTypeID is integer which represent for teacher or student.
	 */
	private void getListEmailUsers(long userTypeID) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY, RunningTimeData
				.getCurrentUserKey()));
		params.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID, String
				.valueOf(userTypeID)));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USER), params);
		if (listTeacherResponse == null) {
			Utils.informDialog("Get list students fail!!!!");
		} else {
			System.out.println(listTeacherResponse);
			JSONArray array = new JSONArray(listTeacherResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				if (userTypeID == UserType.TEACHER) {
					listTeacherEmails.add(ob.getString(UserDAO.EMAIL));
				} else if (userTypeID == UserType.STUDENT) {
					listStudentEmails.add(ob.getString(UserDAO.EMAIL));
				}
			}
		}

	}

	/**
	 * @return the txtTitle is title field of a subject
	 */
	public JTextField getTxtTitle() {
		return txtTitle;
	}

	/**
	 * @return the lblTitle
	 */
	public JLabel getLblTitle() {
		return lblTitle;
	}

	/**
	 * @return the listTeacherView
	 */
	public JTextArea getListTeacherView() {
		return listTeacherView;
	}

	/**
	 * @return the listStudentView
	 */
	public JTextArea getListStudentView() {
		return listStudentView;
	}

	/**
	 * @return the btnAddTeacher
	 */
	public JButton getBtnAddTeacher() {
		return btnAddTeacher;
	}

	/**
	 * @return the btnAddStudent
	 */
	public JButton getBtnAddStudent() {
		return btnAddStudent;
	}

}