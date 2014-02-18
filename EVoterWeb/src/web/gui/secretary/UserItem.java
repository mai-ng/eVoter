/**
 * 
 */
package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
 * Extends {@link ItemViewAbstract}.<br>
 * Used in {@link UserTab} which contains a list of {@link UserItem}.
 * @author maint
 * @see ItemViewAbstract
 */
public class UserItem extends ItemViewAbstract {

	private static final long serialVersionUID = 1L;
	/**
	 * target user.
	 */
	private User user;
	
	/**
	 * Get user's email as name of item.
	 * @param u is target user.
	 */
	public UserItem(User u) {
		super();
		this.user = u;
		itemName.setText(u.getEmail());
	}
	
	/**
	 * create actions for button "Edit", "Detail", and "Delete".<br>
	 * <li> Click button "Delete" -> open a window  {@link Utils#confirmDialog(String)} to ask the confirmation.
	 * <li> Click button "Edit" -> open a window {@link EditUser} to edit information.
	 * <li> Click button "Detail" -> open a window {@link ViewUser} to see detail about a user.
	 */
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
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
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
				 new ViewUser(user);
			}
		});

		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new EditUser(user);
			}
		});
	}
}
