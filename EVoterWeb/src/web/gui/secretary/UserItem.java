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
import web.gui.secretary.spec.UserGUIAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * Extends {@link ItemViewAbstract}.<br>
 * Used in {@link UserTab} as its content.
 * @author maint
 * @see ItemViewAbstract
 */
public class UserItem extends ItemViewAbstract {

	private static final long serialVersionUID = 1L;
	private User user;
	private UserGUIAbstract targetUser;
	
	public UserItem(User u) {
		super();
		this.user = u;
		itemName.setText(u.getEmail());
	}
	
	public void buttonEvent(){
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
						// TODO Auto-generated catch block
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
				setTargetUser( new ViewUser(user));
			}
		});

		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTargetUser( new EditUser(user));
			}
		});
	}

	/**
	 * @return the targetUser
	 */
	public UserGUIAbstract getTargetUser() {
		return targetUser;
	}

	/**
	 * @param targetUser the targetUser to set
	 */
	public void setTargetUser(UserGUIAbstract targetUser) {
		this.targetUser = targetUser;
	}

}
