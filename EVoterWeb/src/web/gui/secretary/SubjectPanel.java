package web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
import web.gui.secretary.subcomponents.SubjectItem;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.utils.URIRequest;

public class SubjectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel subjectPanel;
	private JButton btnNewSubject;
	private ArrayList<ItemViewAbstract> listSubjects;

	public SubjectPanel() {
		initComponents();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1, 10, 30, 0);
		
		c.gridwidth = 2;
		add(subjectPanel, c);
		
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridwidth = 1;
		c.ipady = 30;
		add(btnNewSubject, c);
		
		//call add subject function
		addSubject();
		
	}

	private ArrayList<ItemViewAbstract> loadListSubject() {
		ArrayList<ItemViewAbstract> listsubject = new ArrayList<ItemViewAbstract>();

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
				Subject sb = new Subject(ob.getLong(SubjectDAO.ID), ob.getString(SubjectDAO.TITLE), Utils.convertToDate(ob.getString(SubjectDAO.CREATION_DATE)));
				listsubject.add(new SubjectItem(sb));
			}
		}
		return listsubject;
	}

	public void initComponents() {
		btnNewSubject = new JButton("New Subject");
		listSubjects = new ArrayList<ItemViewAbstract>();
		listSubjects.addAll(loadListSubject());

		subjectPanel = new JPanel();
		subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < listSubjects.size(); i++) {
//			listSubjects.get(i).setAlignmentX(CENTER_ALIGNMENT);
//			listSubjects.get(i).setSize(subjectPanel.getWidth(), btnNewSubject.getHeight());
			subjectPanel.add(listSubjects.get(i));
		}
	}
	
	/**
	 * add a new subject when click {@link #btnNewSubject}
	 */
	public void addSubject(){
		btnNewSubject.addActionListener(new ActionListener() {

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

	}
}
