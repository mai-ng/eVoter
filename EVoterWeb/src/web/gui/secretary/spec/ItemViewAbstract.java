package web.gui.secretary.spec;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import web.gui.secretary.SubjectItem;
import web.gui.secretary.UserItem;
import web.util.Utils;

/**
 * Used in {@link MenuTabAbstract} as list of items in its content.<br>
 * Extends by {@link SubjectItem} and {@link UserItem}. <br>
 * Present a line in the list of Teachers, Students, or Subjects. <br>
 * Each item contains: 
 * <li>title: teacher's email, student's email, or title of a subject
 * <li>button: edit 
 * <li>button: delete 
 * <li>button: detail <br>
 * Display: "Title------btnEdit---btnDelete---btnDetail".<br>
 * 
 * @author maint<br>
 */
public abstract class ItemViewAbstract extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * title of item: email of teacher/student, or title of a subject.
	 */
	protected JLabel itemName;

	/**
	 * button edit to edit a teacher, student, or subject if it is pressed.
	 */
	protected JButton btnEdite;
	/**
	 * button delete to delete a teacher, student, or subject if it is pressed.
	 */
	protected JButton btnDelete;
	/**
	 * button detail to view detail information a teacher, student, or subject if it is pressed.
	 */
	protected JButton btnDetail;


	public ItemViewAbstract() {
		initComponents();
		buildGUI();
		actionPerformed();
	}

	/**
	 * user interface of each item.
	 */
	public void buildGUI() {
		setBackground(Color.white);
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(1, 5, 10, 100);
		add(itemName, c);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
	 * initialize all components:
	 *  <li>label of the item 
	 *  <li>button "Edit"
	 *  <li>button "Delete" 
	 *  <li>button "Detail"
	 */
	public void initComponents() {
		itemName = new JLabel("item name");

		ImageIcon editIcon = Utils.findImageIcon("/resource/edit.png");
		btnEdite = new JButton(editIcon);
		btnEdite.setContentAreaFilled(false);
		btnEdite.setOpaque(false);
		btnEdite.setBorderPainted(false);

		ImageIcon deleteIcon = Utils.findImageIcon("/resource/delete.png");
		btnDelete = new JButton(deleteIcon);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setOpaque(false);
		btnDelete.setBorderPainted(false);

		ImageIcon detailIcon = Utils.findImageIcon("/resource/detail.gif");
		btnDetail = new JButton(detailIcon);
		btnDetail.setContentAreaFilled(false);
		btnDetail.setOpaque(false);
		btnDetail.setBorderPainted(false);
	}

	/**
	 * create actions for button "Edit", "Detail", and "Delete"
	 */
	public abstract void actionPerformed();

}
