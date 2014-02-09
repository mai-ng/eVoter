package web.gui.secretary;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.subcomponents.ItemOfListView;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

public class SubjectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel subjectPanel;
	private JButton btnNewSubject;
	private ArrayList<ItemOfListView> listSubjects;

	/**
	 * Create the panel.
	 */
	public SubjectPanel() {
		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.setLayout(new BorderLayout());
		this.add(subjectPanel, BorderLayout.PAGE_END);
	}

	private ArrayList<ItemOfListView> loadListSubject() {
		ArrayList<ItemOfListView> listsubject = new ArrayList<ItemOfListView>();

		List<NameValuePair> subjectParams = new ArrayList<NameValuePair>();
		subjectParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		String listSubjectResponse = EVoterHTTPRequest
				.excutePost(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT),
						subjectParams);
		if (listSubjectResponse == null) {
			System.out.println("Get list subject fail!!!!");
		} else {
			System.out.println(listSubjectResponse);
			JSONArray array = new JSONArray(listSubjectResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				ItemOfListView item = new ItemOfListView(
						ob.getString(SubjectDAO.TITLE),
						ob.getLong(SubjectDAO.ID), ItemOfListView.TYPE_SUBJECT);
				// System.out.println(item.toString());
				listsubject.add(item);
			}
		}
		return listsubject;
	}

	public void initComponents() {
		this.btnNewSubject = new JButton("New Subject");
		this.btnNewSubject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AddSubject addSubject = new AddSubject();
					addSubject.setSize(800, 600);
					addSubject.setLocationRelativeTo(null);
					addSubject.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		listSubjects = new ArrayList<ItemOfListView>();
		listSubjects.addAll(loadListSubject());

		subjectPanel = new JPanel();
		for (int i = 0; i < listSubjects.size(); i++) {
			subjectPanel.add(listSubjects.get(i));
		}
		subjectPanel.add(btnNewSubject);
		subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));

	}
}
