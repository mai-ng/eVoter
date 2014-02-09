package web.gui.secretary;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.subcomponents.ItemOfListView;
import web.gui.secretary.subcomponents.MenuBarTop;
import web.util.Configuration;
import web.util.EVoterHTTPRequest;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;

public class ViewListSubject extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MenuBarTop menuBarTop;
	private JPanel teacherList;
	private ArrayList<ItemOfListView> listSubjects;
	
	/**
	 * Create the panel.
	 */
	public ViewListSubject() {
		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.setLayout(new BorderLayout());
		
		this.add(menuBarTop, BorderLayout.PAGE_START);
		
		this.add(teacherList, BorderLayout.PAGE_END);
	}
	
	private ArrayList<ItemOfListView> loadListSubject() {
		ArrayList<ItemOfListView> listsubject = new ArrayList<ItemOfListView>();
		
		List<NameValuePair> subjectParams = new ArrayList<NameValuePair>();
		subjectParams.add(new BasicNameValuePair(UserDAO.USER_KEY, RunningTimeData.getCurrentUserKey()
				));
		String listSubjectResponse = EVoterHTTPRequest.excutePost(Configuration.get_urlGetAllSubject(), subjectParams);
		if (listSubjectResponse == null) {
			System.out.println("Get list subject fail!!!!");
		}
		else {
			System.out.println(listSubjectResponse);
			JSONArray array = new JSONArray(listSubjectResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				ItemOfListView item = new ItemOfListView(ob.getString(SubjectDAO.TITLE), ob.getLong(SubjectDAO.ID), ItemOfListView.TYPE_SUBJECT);
				//				System.out.println(item.toString());
				listsubject.add(item);
			}
		}
		return listsubject;
	}
	
	public void initComponents() {
		listSubjects = new ArrayList<ItemOfListView>();
		listSubjects.addAll(loadListSubject());
		menuBarTop = new MenuBarTop();
		
		teacherList = new JPanel();
		for (int i = 0; i < listSubjects.size(); i++) {
			teacherList.add(listSubjects.get(i));
		}
		teacherList.setLayout(new BoxLayout(teacherList, BoxLayout.Y_AXIS));
		
	}
}
