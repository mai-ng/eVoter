/**
 * 
 */
package web.gui.secretary.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewTeacher;
import evoter.share.model.User;

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

	public TeacherItem(User u) {
		super();
		this.user = u;
		itemName.setText(u.getEmail());
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Send a delete user request

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
