package web.gui.secretary.spec;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.http.NameValuePair;

import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.EditUser;
import web.util.EVoterHTTPRequest;
import web.util.Utils;

/**
 * extended by {@link SubjectGUIAbstract}, {@link UserGUIAbstract}.<br>
 * Set layout for a frame, and initialize the button add/save.<br>
 * @author maint
 */
public abstract class GUIAbstract extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * button "Add" on the create/add (subject or user) frame.<br>
	 * button "Save" on the edit (subject or user) frame.<br>
	 * used in {@link AddSubject}, {@link AddUser}, {@link EditSubject}, and {@link EditUser}.
	 */
	protected JButton btnSave;
	
	/**
	 * layout of a frame- {@link GridBagLayout}
	 */
	protected GridBagLayout gridbag;
	protected GridBagConstraints c;
	
	
	public GUIAbstract(){
		initComponents();
		saveButtonEvent();
	}


	/**
	 * create button and layout
	 */
	public void initComponents() {
		btnSave = new JButton();
		// initialize the layout
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(gridbag);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 5, 5);
	}
	
	/**
	 * create an event for {@link #btnSave}:
	 * <li> Check pre-condition before send request to server.
	 * <li> Build params for request.
	 * <li> Get url for request.
	 */
	protected void saveButtonEvent() {
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (readyToSendRequest()) {
					List<NameValuePair> params = buildRequestParameters();
					String urlRequest = getURLRequest();
					System.out.println("URL: " + urlRequest);
					String response = EVoterHTTPRequest.excutePost(urlRequest,
							params);
					if (response == null) {
						Utils.informDialog("Cannot request to server!");
					} else {
						Utils.informDialog("Successful!");
						dispose();
					}
				}
			}
		});

	}

	/**
	 * check validation of fields before send request to server.
	 */
	protected abstract boolean readyToSendRequest();

	/**
	 * @return url for a request:
	 */
	protected abstract String getURLRequest();

	/**
	 * @return params which are sent to server.
	 */
	protected abstract List<NameValuePair> buildRequestParameters();


	/**
	 * @return the btnSave
	 */
	public JButton getBtnSave() {
		return btnSave;
	}
	
}
