package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.subcomponents.ItemViewAbstract;
import web.gui.secretary.subcomponents.TeacherItem;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

public class TeacherPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel teacherPanel;
	private JButton btnNewTeacher;
	private ArrayList<ItemViewAbstract> listTeachers;

	public TeacherPanel() {
		initComponents();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1, 10, 30, 0);

		c.gridwidth = 2;
		add(teacherPanel, c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridwidth = 1;
		c.ipady = 30;
		add(btnNewTeacher, c);

		// call add subject function
		addTeacher();

	}

	private ArrayList<ItemViewAbstract> loadTeachers() {
		ArrayList<ItemViewAbstract> list_teachers = new ArrayList<ItemViewAbstract>();

		List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID,String.valueOf(UserType.TEACHER)));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USER), teacherParams);
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
				list_teachers.add(new TeacherItem(u));
			}
		}
		return list_teachers;
	}

	public void initComponents() {
		btnNewTeacher = new JButton("New teacher");
		listTeachers = new ArrayList<ItemViewAbstract>();
		listTeachers.addAll(loadTeachers());

		teacherPanel = new JPanel();
		teacherPanel.setLayout(new BoxLayout(teacherPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < listTeachers.size(); i++) {
			// listSubjects.get(i).setAlignmentX(CENTER_ALIGNMENT);
			// listSubjects.get(i).setSize(subjectPanel.getWidth(),
			// btnNewSubject.getHeight());
			teacherPanel.add(listTeachers.get(i));
		}
	}

	/**
	 * add a new subject when click {@link #btnNewTeacher}
	 */
	public void addTeacher() {
		btnNewTeacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddTeacher addedTeacher = new AddTeacher();
				addedTeacher.setSize(600, 400);
				addedTeacher.setLocationRelativeTo(null);
				addedTeacher.setVisible(true);
			}
		});

	}
}
