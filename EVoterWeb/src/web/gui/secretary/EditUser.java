package web.gui.secretary;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.UserGUIAbstract;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * Edit information of a user (Teacher/Student) such as full name, user name, and email.<br> 
 * extends {@link UserGUIAbstract} class.
 * @author maint<br>
 */
public class EditUser extends UserGUIAbstract {

	private static final long serialVersionUID = 1L;
	protected User currentUser;

	/**
	 * set title for the frame.<br>
	 * load information of the user.<br>
	 * edit information of a student/teacher.<br>
	 * update information.
	 * @param user is a student or teacher.
	 */
	public EditUser(User user) {
		super();		
		currentUser = user;
		if(currentUser.getUserTypeId()==UserType.STUDENT){
			setTitle("Edit student's information");
		}else if(currentUser.getUserTypeId()==UserType.TEACHER){
			setTitle("Edit teacher's information");
		}
		
		loadInfo();
	}
	
	/**
	 * set text for button "Update"
	 */
	public void initComponents() {
		super.initComponents();
		btnSave.setText("Update");
	}

	/**
	 * load information of a teacher
	 */
	public void loadInfo(){
		txtEmail.setText(currentUser.getEmail());
		txtFullName.setText(currentUser.getFullName());
		txtUserName.setText(currentUser.getUserName());
	}

	@Override
	protected String getURLRequest() {
		return RequestConfig.getURL(URIRequest.EDIT_USER);
	}

	@Override
	protected List<NameValuePair> buildRequestParameters() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		params.add(new BasicNameValuePair(
				UserDAO.USER_TYPE_ID, String
						.valueOf(currentUser.getUserTypeId())));
		params.add(new BasicNameValuePair(UserDAO.FULL_NAME,
				txtFullName.getText()));
		params.add(new BasicNameValuePair(UserDAO.EMAIL,
				txtEmail.getText()));
		params.add(new BasicNameValuePair(UserDAO.USER_NAME,
				txtUserName.getText()));
		params.add(new BasicNameValuePair(UserDAO.ID, String
				.valueOf(currentUser.getId())));
		params.add(new BasicNameValuePair(
				UserDAO.IS_APPROVED, String.valueOf(currentUser
						.isApproved())));
		return params;
	}

	/* (non-Javadoc)
	 * @see web.gui.secretary.spec.UserGUIAbstract#readyToSendRequest()
	 */
	@Override
	protected boolean readyToSendRequest() {
		String fullName = txtFullName.getText();
		String email = txtEmail.getText();
		String username = txtUserName.getText();
		if(fullName.equals(currentUser.getFullName())&&email.equals(currentUser.getEmail())&&username.equals(currentUser.getUserName())){
				Utils.informDialog("\tNothing change!");
				return false;
			}
		return super.readyToSendRequest();
	}

}
