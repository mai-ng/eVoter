/**
 * 
 */
package web.gui.secretary.subcomponents;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewTeacher;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * @author maint
 * 
 */
public class TeacherItem extends ItemViewAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User user;
	JDialog inforDialog = new JDialog();
	JDialog confirmDialog = new JDialog();
	JLabel inforMsg = new JLabel();
	JButton btYes = new JButton("Yes");
	JButton btNo = new JButton("No");
	JPanel panel = new JPanel();
	JLabel lb = new JLabel();
	public TeacherItem(User u) {
		super();
		this.user = u;
		itemName.setText(u.getEmail());
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				inforDialog.setTitle("Information");
				inforDialog.setSize(new Dimension(400, 100));
				inforDialog.setModal(true);
				inforDialog.setLocationRelativeTo(null);

				inforDialog.add(inforMsg);

				confirmDialog.setTitle("Confirm");
				confirmDialog.setSize(new Dimension(400, 100));
				confirmDialog.setModal(true);
				confirmDialog.setLocationRelativeTo(null);
				
				lb.setText("Do you really want to delete user: "
						+ user.getEmail());
				panel.add(lb);
				panel.add(btYes);
				panel.add(btNo);
				btYes.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
						teacherParams.add(new BasicNameValuePair(
								UserDAO.USER_KEY, RunningTimeData
										.getCurrentUserKey()));
						teacherParams.add(new BasicNameValuePair(UserDAO.ID,
								String.valueOf(user.getId())));
						String response = EVoterHTTPRequest.excutePost(
								RequestConfig.getURL(URIRequest.DELETE_USER),
								teacherParams);
						if (response == null) {
							inforMsg.setText("Cannot request to server!");
							inforDialog.setVisible(true);
							confirmDialog.setVisible(false);
						} else {
							if (response.equals(URIRequest.SUCCESS_MESSAGE)) {
								inforMsg.setText("Delete successfully!");
								inforDialog.setVisible(true);
								confirmDialog.setVisible(false);
							} else {
								confirmDialog.setVisible(false);
								inforMsg.setText("Cannot delete!");
								inforDialog.setVisible(true);
							}
						}

					}
				});
				btNo.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						confirmDialog.setVisible(false);
					}
				});
				confirmDialog.getContentPane().add(panel);
				confirmDialog.setVisible(true);
			}
		});

		btnDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ViewTeacher teacher = new ViewTeacher(user);
				teacher.setSize(600, 400);
				teacher.setLocationRelativeTo(null);
				teacher.setVisible(true);
			}
		});

		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditTeacher editTeacherView = new EditTeacher(user);
				editTeacherView.setSize(600, 400);
				editTeacherView.setLocationRelativeTo(null);
				editTeacherView.setVisible(true);
			}
		});
	}

}
