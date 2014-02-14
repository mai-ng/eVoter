package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * @author maint<br>
 *         a JFrame to view detail a subject . extends
 *         {@link GUISubjectAbstract} class.
 */
public class ViewSubject extends GUISubjectAbstract {

	private static final long serialVersionUID = 1L;
	private Subject subject;

	/**
	 * title of a subject
	 */
	private JLabel lblTitle;

	/**
	 * set the title of the frame, and initialize its components.<br>
	 * Design user interface ( {@link JFrame} ) to view a subject.
	 */
	public ViewSubject(Subject sb) {
		this.subject = sb;
		this.setTitle("View subject information");
		initComponents();

		// user interface of viewing detail subject
		// Row 0: Title
		c.weightx = 0;
		this.add(new JLabel("  "));
		this.add(new JLabel("Subject: "));
		c.weightx = 0.5;
		this.add(lblTitle, c);

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

		c.gridheight = 1;

		// Row 3: Teacher table
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
		c.gridheight = 1;

		// Row 4: Students table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(studentPanel, c);

		// Row 5: Create Button
		c.gridy = 5;
		c.gridx = 2;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnClose, c);
		setSize(700, 500);
		setLocationRelativeTo(null);
		lblTitle.setText(subject.getTitle());
		loadUsers();
		// setVisible(true);
	}

	private void loadUsers() {
		List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		teacherParams.add(new BasicNameValuePair(SubjectDAO.ID, String
				.valueOf(subject.getId())));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USERS_OF_SUBJECT),
				teacherParams);
		if (listTeacherResponse == null) {
			System.out.println("Get list subject fail!!!!");
		} else {
			System.out.println(listTeacherResponse);
			JSONArray array = new JSONArray(listTeacherResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				User u = new User(ob.getString(UserDAO.USER_NAME),
						ob.getString(UserDAO.PASSWORD),
						ob.getString(UserDAO.EMAIL),
						ob.getLong(UserDAO.USER_TYPE_ID),
						ob.getString(UserDAO.FULL_NAME),
						ob.getBoolean(UserDAO.IS_APPROVED));
				if (u.getUserTypeId() == UserType.TEACHER) {
					listTeacherView.append(u.getEmail() + "\n");
				} else if (u.getUserTypeId() == UserType.STUDENT) {
					listStudentView.append(u.getEmail() + "\n");
				}
			}
		}

	}

	/**
	 * initialize additional components which are not defined in
	 * {@link GUISubjectAbstract}. Components of only {@link ViewSubject}
	 */
	protected void initComponents() {
		super.initComponents();
		lblTitle = new JLabel("here is the title of the subject");
		listTeacherView.setEditable(false);
		listStudentView.setEditable(false);
		btnClose.setVisible(false);

	}
}
