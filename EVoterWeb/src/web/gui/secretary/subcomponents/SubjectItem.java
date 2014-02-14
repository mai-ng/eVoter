/**
 * 
 */
package web.gui.secretary.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import web.gui.secretary.ViewSubject;
import evoter.share.model.Subject;

/**
 * @author louisnguyen
 *
 */
public class SubjectItem extends ItemViewAbstract{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Subject subject;
	/**
	 * 
	 */
	public SubjectItem(Subject su) {
		// TODO Auto-generated constructor stub
		this.subject = su;
		itemName.setText(subject.getTitle());
		btnDetail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewSubject subView = new ViewSubject(subject);
				subView.setVisible(true);
			}
		});
	}

}
