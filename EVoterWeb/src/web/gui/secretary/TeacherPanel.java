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
import web.gui.secretary.subcomponents.ListItems;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

public class TeacherPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel teacherPanel;
	private JButton btnNewTeacher;
	private ArrayList<ListItems> listTeachers;

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
		
		//call add subject function
		addTeacher();
		
	}

	private ArrayList<ListItems> loadTeachers() {
		ArrayList<ListItems> list_teachers = new ArrayList<ListItems>();

		List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		String listTeacherResponse = EVoterHTTPRequest
				.excutePost(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT),
						teacherParams);
		if (listTeacherResponse == null) {
			System.out.println("Get list subject fail!!!!");
		} else {
			System.out.println(listTeacherResponse);
			JSONArray array = new JSONArray(listTeacherResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				ListItems item = new ListItems(
						ob.getString(SubjectDAO.TITLE),
						ob.getLong(SubjectDAO.ID), ListItems.TYPE_TEACHER);
				// System.out.println(item.toString());
				list_teachers.add(item);
			}
		}
		return list_teachers;
	}

	public void initComponents() {
		btnNewTeacher = new JButton("New teacher");
		listTeachers = new ArrayList<ListItems>();
		listTeachers.addAll(loadTeachers());

		teacherPanel = new JPanel();
		teacherPanel.setLayout(new BoxLayout(teacherPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < listTeachers.size(); i++) {
//			listSubjects.get(i).setAlignmentX(CENTER_ALIGNMENT);
//			listSubjects.get(i).setSize(subjectPanel.getWidth(), btnNewSubject.getHeight());
			teacherPanel.add(listTeachers.get(i));
		}
	}
	
	/**
	 * add a new subject when click {@link #btnNewTeacher}
	 */
	public void addTeacher(){
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
