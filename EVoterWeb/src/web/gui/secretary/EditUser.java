package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.UserGUIAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.UserAccountValidation;
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
		updateUser(currentUser.getUserTypeId());
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

	/**
	 * update edited information
	 */
	public void updateUser(final long userType) {
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Check valid input and send an update user request

				String fullName = txtFullName.getText();
				String email = txtEmail.getText();
				String username = txtUserName.getText();
				if (fullName.equals("")) {
					Utils.informDialog("\tFull name is empty! Please input again!");
				} else if (!UserAccountValidation.isValidUserName(username)) {
					Utils.informDialog("\tUser name is not valid! Please input again!");
				} else if (!UserAccountValidation.isValidEmail(email)) {
					Utils.informDialog("\tEmail is not valid! Please input again!");
				} else if(fullName.equals(currentUser.getFullName())&&email.equals(currentUser.getEmail())&&username.equals(currentUser.getUserName())){
					Utils.informDialog("\tNothing change!");
				}else{
					List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
							RunningTimeData.getCurrentUserKey()));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.USER_TYPE_ID, String
									.valueOf(userType)));
					teacherParams.add(new BasicNameValuePair(UserDAO.FULL_NAME,
							fullName));
					teacherParams.add(new BasicNameValuePair(UserDAO.EMAIL,
							email));
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_NAME,
							username));
					teacherParams.add(new BasicNameValuePair(UserDAO.ID, String
							.valueOf(currentUser.getId())));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.IS_APPROVED, String.valueOf(currentUser
									.isApproved())));
					String response = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.EDIT_USER),
							teacherParams);
					if (response == null) {
						Utils.informDialog("Cannot request to server!");
					} else {
						Utils.informDialog("Edit successfully!");
						dispose();
					}
				}
			}
		});
		
	}


}
