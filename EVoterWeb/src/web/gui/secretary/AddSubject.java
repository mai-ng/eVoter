package web.gui.secretary;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;

import web.gui.secretary.spec.SubjectGUIAbstract;

/**
 * Add a new subject by inserting information of a subject such as the title, teachers, students of it.<br> 
 * extends {@link SubjectGUIAbstract} class.
 * @author maint<br>
 */
public class AddSubject extends SubjectGUIAbstract {

	private static final long serialVersionUID = 1L;
	

	/**
	 * set the title of the frame, and set properties for its components.<br>
	 * @throws IOException 
	 */
	public AddSubject() {
		super();
		setTitle("Add new subject");
	}

	/**
	 * set text for button "Invite"
	 */
	public void initComponents() {
		super.initComponents();
		btnSave.setText("Add");
	}

	@Override
	protected String getURLRequest() {
		//TODO: Set URL for create new subject
//		return RequestConfig.getURL(URIRequest.C);
		return null;
	}

	@Override
	protected List<NameValuePair> buildRequestParameters() {
		// TODO Build Paramters for create new subject
		return null;
	}

}