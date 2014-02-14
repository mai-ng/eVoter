package web.gui.secretary;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.UserAccountValidation;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * @author maint<br>
 *         a JFrame to edit teacher's information. extends
 *         {@link GUITeacherAbstract} class.
 */
public class EditTeacher extends GUITeacherAbstract {

	private static final long serialVersionUID = 1L;
	private User currentUser;

	/**
	 * set the title of the frame, and initialize its components
	 */
	public EditTeacher(User us) {
		super();
		this.setTitle("Edit teacher's information");
		this.currentUser = us;
		txtEmail.setText(currentUser.getEmail());
		txtFullName.setText(currentUser.getFullName());
		txtUserName.setText(currentUser.getUserName());
		txtPassword.setText(currentUser.getPassWord());
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Check valid input and send an update user request

				JDialog dialog = new JDialog(EditTeacher.this);
				dialog.setTitle("Dialog");
				dialog.setSize(new Dimension(400, 100));
				dialog.setLocationRelativeTo(null);
				dialog.setModal(true);
				JLabel msg = new JLabel();
				dialog.add(msg);

				String fullName = txtFullName.getText();
				String email = txtEmail.getText();
				String username = txtUserName.getText();
				String password = txtPassword.getText();
				if (fullName.equals("")) {
					msg.setText("\tFull name is empty! Please input again!");
					dialog.setVisible(true);
				} else if (!UserAccountValidation.isValidUserName(username)) {
					msg.setText("\tUser name is not valid! Please input again!");
					dialog.setVisible(true);
				} else if (!UserAccountValidation.isValidEmail(email)) {
					msg.setText("\tEmail is not valid! Please input again!");
					dialog.setVisible(true);
				} else if (!UserAccountValidation.isValidPassword(password)) {
					msg.setText("\tPassword is not valid! Please input again!");
					dialog.setVisible(true);
				} else if(fullName.equals(currentUser.getFullName())&&email.equals(currentUser.getEmail())&&username.equals(currentUser.getUserName())&&password.equals(currentUser.getPassWord())){
					msg.setText("\tNothing change!");
					dialog.setVisible(true);
				}else{
					List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
							RunningTimeData.getCurrentUserKey()));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.USER_TYPE_ID, String
									.valueOf(UserType.TEACHER)));
					teacherParams.add(new BasicNameValuePair(UserDAO.FULL_NAME,
							fullName));
					teacherParams.add(new BasicNameValuePair(UserDAO.EMAIL,
							email));
					teacherParams.add(new BasicNameValuePair(UserDAO.USER_NAME,
							username));
					teacherParams.add(new BasicNameValuePair(UserDAO.PASSWORD,
							password));
					teacherParams.add(new BasicNameValuePair(UserDAO.ID, String
							.valueOf(currentUser.getId())));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.IS_APPROVED, String.valueOf(currentUser
									.isApproved())));
					String response = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.EDIT_USER),
							teacherParams);
					if (response == null) {
						msg.setText("Cannot request to server!");
						dialog.setVisible(true);
					} else {
						msg.setText("Edit successfully!");
						dialog.setVisible(true);
						EditTeacher.this.setVisible(false);
					}
				}
			}
		});
	}

}
