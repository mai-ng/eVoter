/**
 * 
 */
package web.gui.secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.ItemViewAbstract;
import web.gui.secretary.spec.MenuTabAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.utils.URIRequest;

/**
 * @author maint
 *
 */
public class SubjectTab extends MenuTabAbstract{

	private static final long serialVersionUID = 1L;

	public SubjectTab(){
		super();
		createListView();
	}

	/**
	 * add a new subject when click {@link #btnNewSubject}
	 */
	public void addItem() {
		btnNewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					new AddSubject();
			}
		});
	}

	/**
	 * @return list of all existing subjects
	 */
	public ArrayList<ItemViewAbstract> loadListItems() {
		ArrayList<ItemViewAbstract> listsubject = new ArrayList<ItemViewAbstract>();

		List<NameValuePair> subjectParams = new ArrayList<NameValuePair>();
		subjectParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		String listSubjectResponse = EVoterHTTPRequest
				.excutePost(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT),
						subjectParams);
		if (listSubjectResponse == null) {
			System.out.println("Get list subject fail!!!!");
		} else {
			System.out.println(listSubjectResponse);
			JSONArray array = new JSONArray(listSubjectResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				Subject sb = new Subject(ob.getLong(SubjectDAO.ID),
						ob.getString(SubjectDAO.TITLE), Utils.convertToDate(ob
								.getString(SubjectDAO.CREATION_DATE)));
				listsubject.add(new SubjectItem(sb));
			}
		}
		return listsubject;
	}

}
