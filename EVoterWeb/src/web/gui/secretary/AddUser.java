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
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * Add a new user (Teacher or Student) with information of full name, user name , and email.<br> 
 * extends {@link UserGUIAbstract} class.
 * @author maint<br>
 */
public class AddUser extends UserGUIAbstract {

	private static final long serialVersionUID = 1L;
	private long userTypeId;
	
	/**
	 * set title for the frame.<br>
	 * add a new student/teacher.<br>
	 * @param user_type_id is a student or teacher.
	 * <li> if <code>user_type_id = UserType.STUDENT</code>, he is a student.
	 * <li> if <code>user_type_id = UserType.TEACHER</code>, he is a teacher.
	 */
	public AddUser(long user_type_id) {
		super();
		userTypeId = user_type_id;
		if(userTypeId==UserType.STUDENT){
			setTitle("Add new student");
		}else if(userTypeId==UserType.TEACHER){
			setTitle("Add new teacher");
		}
		
		addNewUser();
	}

	/**
	 * set text for button "Add"
	 */
	public void initComponents() {
		super.initComponents();
		btnSave.setText("Add");
	}
	
	/**
	 * create and add a new teacher to database
	 */
	public void addNewUser() {
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		
				String fullName = txtFullName.getText();
				String email = txtEmail.getText();
				String username = txtUserName.getText();

				if (fullName.equals("")) {
					Utils.informDialog("\tFull name is empty! Please input again!");
				} else if (!UserAccountValidation.isValidUserName(username)) {
					Utils.informDialog("\tUser name is not valid! Please input again!");
				} else if (!UserAccountValidation.isValidEmail(email)) {
					Utils.informDialog("\tEmail is not valid! Please input again!");
				} else {
					List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
							RunningTimeData.getCurrentUserKey()));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.USER_TYPE_ID, String
									.valueOf(userTypeId)));
					teacherParams.add(new BasicNameValuePair(UserDAO.FULL_NAME,
							fullName));
					teacherParams.add(new BasicNameValuePair(UserDAO.EMAIL,
							email));
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_NAME,
							username));
					if(userTypeId==UserType.STUDENT){
					teacherParams.add(new BasicNameValuePair(
							UserDAO.IS_APPROVED, String.valueOf(false)));
					}else{
						teacherParams.add(new BasicNameValuePair(
								UserDAO.IS_APPROVED, String.valueOf(true)));
					}
					teacherParams.add(new BasicNameValuePair(
							UserDAO.PASSWORD, UserAccountValidation.DEFAULT_PASSWORD));
					String response = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.CREATE_USER),
							teacherParams);
					if (response == null) {
						Utils.informDialog("Cannot request to server!");
					} else {
						Utils.informDialog("Add successfully!");
						dispose();
					}
				}
			}
		});
		
	}
}
