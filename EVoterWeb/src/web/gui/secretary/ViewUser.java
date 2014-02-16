package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * View detail information of full name, username and email of a user . <br>
 * If user is a student, approve him/her if he/she isn't approved.<br>
 * If user is a teacher, only view detail information.<br>
 * Extends {@link EditUser} class.
 * @author maint
 */
public class ViewUser extends EditUser{

	private static final long serialVersionUID = 1L;

	/**
	 * initialize components.<br>
	 * set title for the frame. <br>
	 * try to approve a student if  he/she isn't approved.<br>
	 * @param user is a student or teacher.
	 */
	public ViewUser(User user) {
		super(user);
		
		if(currentUser.getUserTypeId()==UserType.STUDENT){
			setTitle("View information of a student");	
			if(currentUser.isApproved()){
				btnSave.setText("Approved");
				btnSave.setEnabled(false);
			}
			else {
				btnSave.setText("Approve");
				approveStudent();
			}
		}else if(currentUser.getUserTypeId()==UserType.TEACHER){
			setTitle("View information of a teacher");	
			btnSave.setEnabled(false);
			btnSave.setVisible(false);

			btnClose.setEnabled(false);
			btnClose.setVisible(false);
		}
			
	}

	/**
	 * set full name, user name, and email fields are not editable.<br>
	 */
	public void initComponents() {
		super.initComponents();
		txtFullName.setEditable(false);
		txtUserName.setEditable(false);
		txtEmail.setEditable(false);
	}
	
	public void approveStudent(){
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<NameValuePair> studentParams = new ArrayList<NameValuePair>();
				studentParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
						RunningTimeData.getCurrentUserKey()));
				studentParams.add(new BasicNameValuePair(
						UserDAO.ID, String
								.valueOf(currentUser.getId())));
				studentParams.add(new BasicNameValuePair(UserDAO.IS_APPROVED,
						String.valueOf(true)));

				String response = EVoterHTTPRequest.excutePost(
						RequestConfig.getURL(URIRequest.CHANGE_APPROVE_USER),
						studentParams);
				if (response == null) {
					Utils.informDialog("Cannot request to server!");
				} else {
					Utils.informDialog("Approve successfully!");
					dispose();
				}				
			}
		});
	}
	
}
