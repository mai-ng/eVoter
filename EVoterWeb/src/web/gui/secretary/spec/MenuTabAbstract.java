package web.gui.secretary.spec;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import web.applet.StartApplet;
import web.gui.secretary.MainPanel;
import web.gui.secretary.SubjectItem;
import web.gui.secretary.SubjectTab;
import web.gui.secretary.UserItem;
import web.gui.secretary.UserTab;

/**
 * content panel in {@link MainPanel} of {@link SubjectTab}, {@link UserTab}, or
 * {@link StudentTab} tabs.
 * Contains list of {@link SubjectItem}, or {@link UserItem} <br>
 * and allow to add a new subject, or user.<br>
 * Extends by {@link SubjectTab} and {@link UserTab} classes.
 * @author maint
 */
public abstract class MenuTabAbstract extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * a panel contains list of {@link UserItem}.
	 */
	protected JScrollPane listView;

	/**
	 * button add a new teacher
	 */
	protected JButton btnNewItem;

	/**
	 * list of {@link UserItem}
	 */
	protected ArrayList<ItemViewAbstract> listItems;

	/**
	 * constructor to initialize<br>
	 * design user interface <br>
	 * create action performance for tabs on menu bar <br>
	 * allow to create new subject or user.
	 */
	public MenuTabAbstract() {
		initComponents();
		addItem();
		buildGUI();
	}

	/**
	 * user interface of {@link SubjectTab}, {@link UserTab}
	 * tabs on menu bar.
	 */
	public void buildGUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(1, 10, 30, 0);

		c.gridwidth = 2;
		add(listView, c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridwidth = 1;
		c.ipady = 30;
		add(btnNewItem, c);

	}

	/**
	 * Display list of {@link SubjectItem}, or {@link UserItem}
	 */
	public void createListView() {

		listView.setPreferredSize(new Dimension(StartApplet.APPLET_WITH-120, StartApplet.APPLET_HEIGHT -200));
		listView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listView.setWheelScrollingEnabled(true);
		listItems.addAll(loadListItems());
		
		JPanel panelListItems = new JPanel();
		panelListItems.setPreferredSize(new Dimension(StartApplet.APPLET_WITH-150, listItems.size()*40));
		panelListItems.setLayout(new GridLayout(0, 1));
		for (int i = 0; i < listItems.size(); i++) {
			panelListItems.add(listItems.get(i));
		}
		listView.setViewportView(panelListItems);
		
	}

	/**
	 * initialize all components
	 */
	public void initComponents() {
		btnNewItem = new JButton();
		listItems = new ArrayList<ItemViewAbstract>();
		listView = new JScrollPane();
	}


	/**
	 * @return list of all existing subject, teacher, or student.
	 */
	public abstract ArrayList<ItemViewAbstract> loadListItems();

	/**
	 * Create a new subject, teacher or student.
	 */
	public abstract void addItem();
}