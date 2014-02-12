package web.gui.secretary.subcomponents;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import web.gui.secretary.EditSubject;
import web.gui.secretary.EditTeacher;
import web.gui.secretary.ViewSubject;
import web.gui.secretary.ViewTeacher;
import web.util.Utils;

/**
 * @author maint<br>
 *         present a line in the list of Teachers, Students, or Subjects.
 *         Each item contains: <li>title: teacher's name, student's name, or
 *         title of a subject <li>button: edit <li>button: delete <li>button:
 *         view detail <br>
 *         Display: "Title------btnEdit---btnDelete---btnDetail"
 */
public class ListItems extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * type = 1 if this item is a teacher.
	 */
	public static int TYPE_TEACHER = 1;
	/**
	 * type = 2 if it is a subject.
	 */
	public static int TYPE_SUBJECT = 2;
	
	private long itemID;
	
	private JLabel itemName;
	
	private JButton btnEdite;
	private JButton btnDelete;
	private JButton btnDetail;
	
	GridBagConstraints c;
	
	private int type;
	
	/**
	 * display an item in the list of items (Teachers, Students, Subject).
	 * The type of this item is defined by {@link #TYPE_TEACHER}, or
	 * {@link #TYPE_SUBJECT}.
	 * 
	 * @param label
	 * @param id
	 */
	public ListItems(String label, long id, int type) {
		this.itemID = id;
		initComponents(label);
		editButton();
		detailButton();
		this.type = type;
	
//		 setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		setBackground(Color.white);
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(1, 5, 10, 100);
		add(itemName, c);

		JPanel btnPanel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
		btnPanel.setBackground(Color.white);
		btnPanel.add(btnEdite);
		btnPanel.add(btnDelete);
		btnPanel.add(btnDetail);
		
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		c.insets = new Insets(1, 80, 10, 0);
		add(btnPanel, c);
	}

	/**
	 * initialize all components: <li>label of the item <li>button "Edit" <li>
	 * button "Delete" <li>button "Detail"
	 * 
	 * @param label
	 */
	public void initComponents(String label) {
		itemName = new JLabel(label);
		
		ImageIcon editIcon = Utils.createImageIcon("/resource/edit.png");
		btnEdite = new JButton(editIcon);
		btnEdite.setContentAreaFilled(false);
		btnEdite.setOpaque(false);
		btnEdite.setBorderPainted(false);
		
		ImageIcon deleteIcon = Utils.createImageIcon("/resource/delete.png");
		btnDelete = new JButton(deleteIcon);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setOpaque(false);
		btnDelete.setBorderPainted(false);
		
		ImageIcon detailIcon = Utils.createImageIcon("/resource/detail.gif");
		btnDetail = new JButton(detailIcon);
		btnDetail.setContentAreaFilled(false);
		btnDetail.setOpaque(false);
		btnDetail.setBorderPainted(false);
	}
	
	/**
	 * @return id if this item
	 */
	public long getItemID() {
		return itemID;
	}
	
	/**
	 * click button "Edit".
	 * Appear a frame of information of this item in order to edit it.
	 * The frame can be {@link EditSubject}, or {@link EditTeacher}.
	 */
	public void editButton() {
		btnEdite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					//item is a subject
					if (type == TYPE_SUBJECT) {
						EditSubject editSub = new EditSubject();
						editSub.setSize(700, 500);
						editSub.setLocationRelativeTo(null);
						editSub.getTxtTitle().setText(itemName.getText());
						editSub.setVisible(true);
					}
					
					//item is a teacher
					if (type == TYPE_TEACHER) {
						EditTeacher editTeacher = new EditTeacher();
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

	/**
	 * click button "Detail".
	 * Appear a frame of information of this item in order to edit it.
	 * The frame can be {@link EditSubject}, or {@link EditTeacher}.
	 */
	public void detailButton() {
		btnDetail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
					//detail about a subject
					if (type == TYPE_SUBJECT) {
						ViewSubject Sub = new ViewSubject();
						Sub.setSize(700, 500);
						Sub.setLocationRelativeTo(null);
//						Sub.getTxtTitle().setText(itemName.getText());
						Sub.setVisible(true);
					}
					
					//detail about a teacher
					if (type == TYPE_TEACHER) {
						ViewTeacher teacher = new ViewTeacher();
						teacher.setSize(600, 400);
						teacher.setLocationRelativeTo(null);
						teacher.getTxtEmail().setText(itemName.getText());
						teacher.setVisible(true);
					}
			
				
			}
		});
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemOfListView [itemID=" + itemID + ", itemName=" + itemName.getText() + ", type=" + type + "]";
	}
	
	
}
