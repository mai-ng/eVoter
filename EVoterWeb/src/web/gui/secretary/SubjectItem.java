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
import web.gui.secretary.spec.SubjectGUIAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.utils.URIRequest;


/**
 * Extends {@link ItemViewAbstract}.<br>
 * Used in {@link SubjectTab} as its content.
 * @author maint
 * @see ItemViewAbstract
 */
public class SubjectItem extends ItemViewAbstract{

	private static final long serialVersionUID = 1L;
	private Subject subject;
	private SubjectGUIAbstract targetSubject;
	
	public SubjectItem(Subject sub) {
		subject = sub;
		itemName.setText(subject.getTitle());
	}

	public void buttonEvent(){
		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTargetSubject(new EditSubject(subject));
			}
		});
		
		btnDelete.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = Utils.confirmDialog(subject.getTitle());
				if (response == JOptionPane.YES_OPTION) {
			    	List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
					teacherParams.add(new BasicNameValuePair(
							UserDAO.USER_KEY, RunningTimeData
									.getCurrentUserKey()));
					teacherParams.add(new BasicNameValuePair(SubjectDAO.ID,
							String.valueOf(subject.getId())));
					String res = EVoterHTTPRequest.excutePost(
							RequestConfig.getURL(URIRequest.DELETE_SUBJECT),
							teacherParams);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Response: " + res);
					if (res == null) {
						Utils.informDialog("Cannot request to server!");
					} else {
						if (res.contains(URIRequest.SUCCESS_MESSAGE)) {
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
				setTargetSubject(new ViewSubject(subject));
			}
		});
	}

	/**
	 * @return the targetSubject
	 */
	public SubjectGUIAbstract getTargetSubject() {
		return targetSubject;
	}

	/**
	 * @param targetSubject the targetSubject to set
	 */
	public void setTargetSubject(SubjectGUIAbstract targetSubject) {
		this.targetSubject = targetSubject;
	}
}
