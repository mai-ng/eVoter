package web.gui.secretary.subcomponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import web.gui.secretary.EditSubject;
import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewTeacher;

/**
 * @author maint<br>
 * present a line in the list of Teachers, Students, or Subjects.
 * Each item contains:
 * <li> title: teacher's name, student's name, or title of a subject
 * <li> button: edit
 * <li> button: delete
 * <li> button: view detail <br>
 * Display: "Title		btnEdit	 btnDelete	btnDetail"
 */
public class ItemOfListView extends JPanel {

private static final long serialVersionUID = 1L;

	/**
	 * type = 1 if this item is a teacher.
	 */
	private static int TYPE_TEACHER = 1;
	/**
	 * type = 2 if it is a subject.
	 */
	private static int TYPE_SUBJECT = 2;
	
	private int itemID;

	private JLabel itemName;
	
	private JButton btnEdite;
	private JButton btnDelete;
	private JButton btnDetail;
	
	GridBagConstraints c;
	
	private int type;
	

	/**
	 * display an item in the list of items (Teachers, Students, Subject).
	 * The type of this item is defined by {@link #TYPE_TEACHER}, or {@link #TYPE_SUBJECT}.
	 * @param label
	 * @param id
	 */
	public ItemOfListView(String label,int id, int type) {
		this.itemID = id;
		initComponents(label);
		editButton();
		this.type = type;
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		//col 0: Name
		c.gridheight = 3;
		c.weightx = 3;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(itemName, c);
		
		//col 1: edit button
		c.gridx = 1;
		c.weightx = 0.5;
		this.add(btnEdite, c);
		
		//col 2: delete button
		c.gridx = 2;
		c.weightx = 0.5;
		this.add(btnDelete, c);
		
		//col 3: detial button
		c.gridx = 3;
		this.add(btnDetail, c);
		

	}

	/**
	 * initialize all components:
	 * <li> label of the item
	 * <li> button "Edit"
	 * <li> button "Delete"
	 * <li> button "Detail"
	 * @param label
	 */
	public void initComponents(String label){
		itemName = new JLabel(label);
		
		btnEdite = new JButton("Edit");
		btnDelete = new JButton("Delete");
		btnDetail = new JButton("Detail");
	}
	
	/**
	 * @return id if this item
	 */
	public int getItemID() {
		return itemID;
	}
	
	/**
	 * click button "Edit". 
	 * Appear a frame of information of this item in order to edit it.
	 * The frame can be {@link EditSubject}, or {@link EditTeacher}.
	 */
	public void editButton(){
		btnEdite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					//item is a subject
					if (type == TYPE_SUBJECT) {
						EditSubject editSub = new EditSubject();
						editSub.setSize(800, 600);
						editSub.setLocationRelativeTo(null);
						editSub.getTxtTitle().setText(itemName.getText());
						editSub.setVisible(true);
					}
					
					//item is a teacher
					if ( type == TYPE_TEACHER){
						ViewTeacher editTeacher = new ViewTeacher();
						editTeacher.setSize(600, 400);
						editTeacher.setLocationRelativeTo(null);
						editTeacher.getTxtEmail().setText(itemName.getText());
						editTeacher.setVisible(true);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
