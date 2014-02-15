/**
 * 
 */
package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.ItemViewAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * @author maint
 * 
 */
public class UserItem extends ItemViewAbstract {

	private static final long serialVersionUID = 1L;
	User user;
	JDialog inforDialog = new JDialog();
	JDialog confirmDialog = new JDialog();
	JLabel inforMsg = new JLabel();
	JButton btYes = new JButton("Yes");
	JButton btNo = new JButton("No");
	JPanel panel = new JPanel();
	JLabel lb = new JLabel();
	
	public UserItem(User u) {
		super();
		this.user = u;
		itemName.setText(u.getEmail());
	}
	
	public void actionPerformed(){
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int response = Utils.confirmDialog(user.getEmail());
				if (response == JOptionPane.YES_OPTION) {
			    	List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
					teacherParams.add(new BasicNameValuePair(
							UserDAO.USER_KEY, RunningTimeData
									.getCurrentUserKey()));
					teacherParams.add(new BasicNameValuePair(UserDAO.ID,
							String.valueOf(user.getId())));
					String res = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.DELETE_USER),
							teacherParams);
					if (res == null) {
						Utils.informDialog("Cannot request to server!");
					} else {
						if (res.equals(URIRequest.SUCCESS_MESSAGE)) {
							Utils.informDialog("Delete successfully!");
						} else {
							Utils.informDialog("Cannot delete!");
						}
					}

			    } else if ((response == JOptionPane.CLOSED_OPTION)||(response == JOptionPane.NO_OPTION)) {
			      System.out.println("JOptionPane closed");
			    }
			}
		});

		btnDetail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ViewUser teacher = new ViewUser(user);
			}
		});

		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditUser editTeacherView = new EditUser(user);
			}
		});
	}

}
