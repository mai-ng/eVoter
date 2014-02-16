/**
 * 
 */
package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import web.gui.secretary.MainPanel;
import web.gui.secretary.SubjectItem;
import web.gui.secretary.SubjectTab;
import web.gui.secretary.UserItem;
import web.gui.secretary.UserTab;

/**
 * content panel in {@link MainPanel} of {@link SubjectTab}, {@link UserTab}, or
 * {@link StudentTab} tabs.
 * Contains list of {@link SubjectItem},
 * {@link UserItem}, or {@link StudentItem} and allow to add a new teacher.<br>
 * Extends in {@link SubjectTab}, {@link UserTab}, and {@link StudentTab} classes.
 * @author maint
 */
public abstract class MenuTabAbstract extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * a panel contains list of {@link UserItem}.
	 */
	protected JPanel listView;

	/**
	 * button add a new teacher
	 */
	protected JButton btnNewItem;

	/**
	 * list of {@link UserItem}
	 */
	protected ArrayList<ItemViewAbstract> listItems;

	/**
	 * constructor to initialize, design user interface and action performance
	 * for tabs on menu bar.
	 */
	public MenuTabAbstract() {
		initComponents();
//		createListView();
		addItem();
		buildGUI();
	}

	/**
	 * user interface of {@link SubjectTab}, {@link UserTab}, or
	 * {@link StudentTab} tabs on menu bar.
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
	 * create a panel for displaying list of {@link SubjectItem},
	 * {@link UserItem}, or {@link StudentItem}
	 */
	public void createListView() {
		listView.setLayout(new BoxLayout(listView, BoxLayout.Y_AXIS));
		listItems.addAll(loadListItems());
		for (int i = 0; i < listItems.size(); i++) {
			listView.add(listItems.get(i));
		}
	}

	/**
	 * initialize all components
	 */
	public void initComponents() {
		btnNewItem = new JButton("New Subject");
		listItems = new ArrayList<ItemViewAbstract>();
		listView = new JPanel();
	}

	public abstract ArrayList<ItemViewAbstract> loadListItems();

	public abstract void addItem();
}