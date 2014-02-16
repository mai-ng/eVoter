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
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * content panel in {@link MainPanel} of Teacher tab or Student tab. Contains list of
 * {@link UserItem} and allow to add a new teacher.
 * @author maint
 * 
 */
public class UserTab extends MenuTabAbstract {

	private static final long serialVersionUID = 1L;
	private long userTypeId;

	/**
	 * constructor to initialize, design user interface and action performance
	 * for Teacher tab on menu bar.
	 */
	public UserTab(long user_type_id) {
		super();
		userTypeId = user_type_id;
		if(userTypeId==UserType.STUDENT){
			btnNewItem.setText("New Student");
		}else if(userTypeId==UserType.TEACHER){
			btnNewItem.setText("New Teacher");
		}
		createListView();
	}

	/**
	 * @return list of all existing users
	 */
	public ArrayList<ItemViewAbstract> loadListItems() {
		ArrayList<ItemViewAbstract> list_teachers = new ArrayList<ItemViewAbstract>();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		params.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID, String
				.valueOf(userTypeId)));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USER), params);
		if (listTeacherResponse == null) {
			System.out.println("Get list subject fail!!!!");
		} else {
			System.out.println(listTeacherResponse);
			JSONArray array = new JSONArray(listTeacherResponse);
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				User u = new User(ob.getString(UserDAO.USER_NAME),
						ob.getString(UserDAO.PASSWORD),
						ob.getString(UserDAO.EMAIL),
						ob.getLong(UserDAO.USER_TYPE_ID),
						ob.getString(UserDAO.FULL_NAME),
						ob.getBoolean(UserDAO.IS_APPROVED));
				u.setId(ob.getLong(UserDAO.ID));
				list_teachers.add(new UserItem(u));
			}
		}
		return list_teachers;
	}

	/**
	 * add a new subject when click {@link #btnNewTeacher}
	 */
	public void addItem() {
		btnNewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddUser(userTypeId);
			}
		});
	}

}
