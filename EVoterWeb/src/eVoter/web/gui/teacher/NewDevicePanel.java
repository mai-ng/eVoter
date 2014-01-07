package eVoter.web.gui.teacher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * New device panel
 * 
 * @author Son Han
 * @date 2013/09/20
 * @version 2.0
 */
@SuppressWarnings("serial")
public class NewDevicePanel extends JPanel {

	// public static final String DEFAULT_IPADDRESS = "127.0.0.1";
	// public static final String DEFAULT_NAMESPACE =
	// "http://telecom-sudparis.eu/";
	// public static final String DEFAULT_D_PORT = "4567";
	// public static final String DEFAULT_S_PORT = "6789";
	// public static final String DEFAULT_MANUFACTURER = "Telecom SudParis";

	public static final String CREATE_DEVICE = "Create Device";
	public static final String ADD_OPERATION = "Add Operation";
	public static final String DEL_OPERATION = "Delete Operation";
	public static final String ADD_EVENT = "Add Event";
	public static final String DEL_EVENT = "    Delete Event   ";

	public JTextField nameField;
	public JTextField manField;
	public JTextField ipField;
	public JTextField devicePortField;
	public JTextField servicePortField;
	public JTextField nsField;

	public JTextArea errorNotice;

	private JButton addOpButton;
	private JButton delOpButton;
	private JButton addEvButton;
	private JButton delEvButton;
	private JButton createButton;
	public JTable opTable;
	public JTable evTable;
	public DefaultTableModel opModel;
	public DefaultTableModel evModel;
	GridBagConstraints c;

	public NewDevicePanel() {
		initComponents();
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		GridBagLayout gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);
		errorNotice = new JTextArea();
		errorNotice.enableInputMethods(false);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 5);

		// Row 0: Device Name | | Manufacturer | |
		c.weightx = 0;
		this.add(new JLabel("Device Name"));
		c.weightx = 0.5;
		this.add(nameField, c);
		c.weightx = 0;
		this.add(new JLabel("Manufacturer"));

		c.weightx = 0.5;
		this.add(manField, c);

		// Row 1
		c.gridy = 1;

		c.weightx = 0;
		this.add(new JLabel("Namespace"), c);
		c.weightx = 0.5;
		this.add(nsField, c);
		c.weightx = 0;
		this.add(new JLabel("IP Address"), c);
		c.weightx = 0.5;
		this.add(ipField, c);

		// Row 2
		c.gridy = 2;
		c.weightx = 0;
		this.add(new JLabel("Device Port"), c);
		c.weightx = 0.5;
		this.add(devicePortField, c);
		c.weightx = 0;
		this.add(new JLabel("Service Port"), c);
		c.weightx = 0.5;
		this.add(servicePortField, c);

		// Setup operations panel: Operations table + Add button + Delete button
		c.weighty = 1;
		JPanel opPanel = new JPanel();
		opPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Operations"));

		opPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		opPanel.add(new JScrollPane(opTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		opPanel.add(addOpButton, c);
		c.gridy = 1;
		opPanel.add(delOpButton, c);

		// Row 3: Operations table
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(opPanel, c);

		// Setup events panel: Events table + Add button + Delete button
		c.gridwidth = 1;
		c.weighty = 1;
		JPanel evPanel = new JPanel();
		evPanel.setBorder(BorderFactory.createTitledBorder("Events"));

		evPanel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1.0;
		evPanel.add(new JScrollPane(evTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), c);

		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridheight = 1;
		evPanel.add(addEvButton, c);
		c.gridy = 1;
		evPanel.add(delEvButton, c);

		// Row 4: Events table
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.5;
		this.add(evPanel, c);

		// Row 5: Create Button
		c.gridy = 5;
		c.fill = 0;
		c.weighty = 0.2;
		this.add(createButton, c);
		// Row 6: Error infor
		c.gridy = 6;
		c.fill = 1;
		this.add(errorNotice, c);

	}

	/**
	 * Reset fields: device name, operations and events Keep information about
	 * IP address, manufacturer, ...
	 * 
	 */
	public void reset() {
		this.nameField.setText("");
		this.errorNotice.setText("");
		// for (int i = 0; i < opModel.getRowCount(); i++) opModel.removeRow(i);
		// for (int i = 0; i < evModel.getRowCount(); i++) evModel.removeRow(i);
		while (opModel.getRowCount() > 0) {
			opModel.removeRow(0);
		}

		while (evModel.getRowCount() > 0) {
			evModel.removeRow(0);
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
		nameField = new JTextField();
		manField = new JTextField("Telecom SudParis");
		nsField = new JTextField("http://telecom-sudparis.eu");
		ipField = new JTextField("127.0.0.1");
		devicePortField = new JTextField("4567");
		servicePortField = new JTextField("6789");

		addOpButton = new JButton(ADD_OPERATION);
		delOpButton = new JButton(DEL_OPERATION);
		addEvButton = new JButton(ADD_EVENT);
		delEvButton = new JButton(DEL_EVENT);
		createButton = new JButton(CREATE_DEVICE);

		opModel = new DefaultTableModel(null, new Object[] { "Name",
				"Parameter", "Status Image Location" });
		opTable = createTable(opModel);
		evModel = new DefaultTableModel(null, new Object[] { "Name",
				"Parameter", "Event Message", "Frequency (ms)" });
		evTable = createTable(evModel);

	}

}