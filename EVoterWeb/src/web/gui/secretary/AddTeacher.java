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
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * @author maint<br>
 *         a JFrame to add a new teacher. extends {@link GUITeacherAbstract}
 *         class.
 */
public class AddTeacher extends GUITeacherAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * set the title of the frame, and initialize its components
	 */
	public AddTeacher() {
		super();
		this.setTitle("Add new teacher");
		btnSave.setText("Add");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(AddTeacher.this);
				dialog.setTitle("Invalid input");
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
				} else {
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
					// teacherParams.add(new BasicNameValuePair(UserDAO.ID,
					// String
					// .valueOf(currentUser.getId())));
					teacherParams.add(new BasicNameValuePair(
							UserDAO.IS_APPROVED, String.valueOf(true)));
					String response = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.CREATE_USER),
							teacherParams);
					if (response == null) {
						msg.setText("Cannot request to server!");
						dialog.setVisible(true);
					} else {
						msg.setText("Add successfully!");
						dialog.setVisible(true);
						AddTeacher.this.setVisible(false);
					}
				}
			}
		});
	}
}
