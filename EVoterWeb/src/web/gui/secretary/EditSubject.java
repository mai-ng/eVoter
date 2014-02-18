package web.gui.secretary;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.SubjectGUIAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * Edit information of a subject such as the title, teachers, students of it.<br> 
 * extends {@link SubjectGUIAbstract} class.
 * @author maint<br>
 */
public class EditSubject extends SubjectGUIAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * target subject
	 */
	private Subject currentSubject;

	/**
	 * Define the target subject.<br>
	 * Load information of the subject<br>
	 * Set title for the frame.
	 * @param sb is the subject going to be edited.
	 */
	public EditSubject(Subject sb){
		super();
		currentSubject = sb;
		setTitle("Edit a subject");
		loadInfor();
	}

	/**
	 * load information of title, list of students and list of teachers of a subject.
	 */
	public void loadInfor(){
		txtTitle.setText(currentSubject.getTitle());
		loadUsers();
	}
	/**
	 * set text for button "Update"
	 */
	public void initComponents() {
		super.initComponents();
		btnSave.setText("Update");
	}
	
	/**
	 * load list of students and teachers of a subject.
	 */
	private void loadUsers() {
		List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		teacherParams.add(new BasicNameValuePair(SubjectDAO.ID, String
				.valueOf(currentSubject.getId())));
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
	 * url request is {@link URIRequest#UPDATE_SUBJECT}. 
	 */
	protected String getURLRequest() {
		return RequestConfig.getURL(URIRequest.UPDATE_SUBJECT);
	}

	/**
	 * build params for {@link URIRequest#UPDATE_SUBJECT}. 
	 */
	protected List<NameValuePair> buildRequestParameters() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		params.add(new BasicNameValuePair(SubjectDAO.TITLE,txtTitle.getText()));
		params.add(new BasicNameValuePair(SubjectDAO.ID,String.valueOf(currentSubject.getId())));
		for(int i=0;i<listInputEmails.size();i++){
			params.add(new BasicNameValuePair(SubjectDAO.EMAIL_LIST,listInputEmails.get(i)));
		}
		return params;
	}

	/**
	 * @return the currentSubject
	 */
	public Subject getCurrentSubject() {
		return currentSubject;
	}

	
}
