/**
 * 
 */
package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import web.gui.secretary.spec.ItemViewAbstract;
import evoter.share.model.Subject;


public class SubjectItem extends ItemViewAbstract{

	private static final long serialVersionUID = 1L;
	Subject subject;
	
	public SubjectItem(Subject su) {
		subject = su;
		itemName.setText(subject.getTitle());
		
	}

	public void actionPerformed(){
		btnEdite.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditSubject editSub = new EditSubject(subject);
			}
		});
		
		btnDelete.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		btnDetail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewSubject subView = new ViewSubject(subject);
			}
		});
	}
}
