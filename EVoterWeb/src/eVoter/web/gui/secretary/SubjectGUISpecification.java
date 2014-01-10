package eVoter.web.gui.secretary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SubjectGUISpecification extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final String INVITE = "Invite";
	public static final String IMPORT_TEACHER = "Import";
	public static final String IMPORT_STUDENT = "Import";
	public static final String CLOSE = "Close";

	public JTextField txtTitle;

	private JButton btnAddTeacher;
	private JButton btnAddStudent;
//	private JButton btnInvite;
	private JButton btnClose;
	
	public JTable tblTeacher;
	public JTable tblStudent;
	public DefaultTableModel modelTeacher;
	public DefaultTableModel modelStudent;
	GridBagConstraints c;

	public SubjectGUISpecification() {
		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 5);

		// Row 0: Title |
		c.weightx = 0;
		this.add(new JLabel("Title"));
		c.weightx = 0.5;
		this.add(txtTitle, c);
		

		// Row 1
		c.gridy = 1;
		c.weightx = 0;
		this.add(new JLabel("  "),c);
		
		

		// Setup teacher panel
		c.weighty = 1;
		JPanel opPanel = new JPanel();
		opPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Teacher"));

		opPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		opPanel.add(new JScrollPane(tblTeacher,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		opPanel.add(btnAddTeacher, c);
		

		// Row 3: Operations table
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(opPanel, c);

		// Setup student panel
		c.gridwidth = 1;
		c.weighty = 1;
		JPanel evPanel = new JPanel();
		evPanel.setBorder(BorderFactory.createTitledBorder("Student"));

		evPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		evPanel.add(new JScrollPane(tblStudent,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		evPanel.add(btnAddStudent, c);

		// Row 4: student table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(evPanel, c);

		// Row 5: Create Button
		c.gridy = 5;
//		c.fill = 0;
//		c.weighty = 0.2;
//		this.add(btnInvite, c);
		
		c.gridx = 2;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(btnClose, c);


	}

	/**
	 * Reset fields: device name, operations and events Keep information about
	 * IP address, manufacturer, ...
	 * 
	 */
	public void reset() {
		this.txtTitle.setText("");
		while (modelTeacher.getRowCount() > 0) {
			modelTeacher.removeRow(0);
		}

		while (modelStudent.getRowCount() > 0) {
			modelStudent.removeRow(0);
		}
	}

	/**
	 * This is the
	 * 
	 * @param a
	 *            asdf asd
	 */
	private JTable createTable(DefaultTableModel model) {
		JTable table = new JTable(model);
		table.setFillsViewportHeight(true);
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);

			if (i == 2) {
				column.setPreferredWidth(100);
			} else {
				column.setPreferredWidth(10);
			}
		}
		return table;
	}

	private void initComponents() {
		txtTitle = new JTextField();

		btnAddTeacher = new JButton(IMPORT_TEACHER);
		btnAddStudent = new JButton(IMPORT_STUDENT);
//		btnInvite = new JButton(INVITE);
		btnClose = new JButton(CLOSE);

		modelTeacher = new DefaultTableModel();
		tblTeacher = createTable(modelTeacher);
		modelStudent = new DefaultTableModel();
		tblStudent = createTable(modelStudent);

	}

}
