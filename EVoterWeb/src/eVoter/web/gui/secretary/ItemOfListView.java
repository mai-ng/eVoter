package eVoter.web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author maint<br>
 * present a line in the list of Teacher, Student, or Subject.
 * Each item contains:
 * <li> title: teacher's name, student's name, or title of a subject
 * <li> button: edit
 * <li> button: delete
 * <li> button: view detail
 * Display: "Title		btnEdit	btnDelete	btnDetail"
 */
public class ItemOfListView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int itemID;

	private JLabel itemName;
	
	private JButton btnEdite;
	private JButton btnDelete;
	private JButton btnDetail;
	
	GridBagConstraints c;
	

	public ItemOfListView(String label,int id) {
		this.itemID = id;
		initComponents(label);
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
	 * initialize all components
	 * @param label
	 */
	public void initComponents(String label){
		itemName = new JLabel(label);
		
		btnEdite = new JButton("Edit");
		btnDelete = new JButton("Delete");
		btnDetail = new JButton("Detail");
	}
	
	public int getItemID() {
		return itemID;
	}
}
