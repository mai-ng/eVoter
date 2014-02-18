package web.gui.secretary;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.SubjectGUIAbstract;
import web.util.RequestConfig;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**
 * Add a new subject by inserting information of a subject such as the title, teachers, students of it.<br> 
 * extends {@link SubjectGUIAbstract} class.
 * @author maint<br>
 */
public class AddSubject extends SubjectGUIAbstract {

	private static final long serialVersionUID = 1L;
	

	/**
	 * set the title of the frame, and set properties for its components.<br>
	 */
	public AddSubject() {
		super();
		setTitle("Add new subject");
	}

	/**
	 * set text for button "Add"
	 */
	public void initComponents() {
		super.initComponents();
		btnSave.setText("Add");
	}

	
	/**
	 * url request is {@link URIRequest#CREATE_SUBJECT}. 
	 */
	protected String getURLRequest() {
		return RequestConfig.getURL(URIRequest.CREATE_SUBJECT);
	}

	/**
	 * build params for {@link URIRequest#CREATE_SUBJECT}. 
	 */
	protected List<NameValuePair> buildRequestParameters() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		params.add(new BasicNameValuePair(SubjectDAO.TITLE,txtTitle.getText()));
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		params.add(new BasicNameValuePair(SubjectDAO.CREATION_DATE,ts.toString()));
		for(int i=0;i<listInputEmails.size();i++){
			params.add(new BasicNameValuePair(SubjectDAO.EMAIL_LIST,listInputEmails.get(i)));
		}
		return params;
	}

}