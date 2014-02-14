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

import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

import web.applet.RunningTimeData;
import web.gui.secretary.spec.ItemViewAbstract;
import web.gui.secretary.spec.MenuTabAbstract;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;

/**
 * content panel in {@link MainPanel} of Teacher tab. Contains list of
 * {@link TeacherItem} and allow to add a new teacher.
 * @author maint
 * 
 */
public class TeacherTab extends MenuTabAbstract {

	private static final long serialVersionUID = 1L;

	/**
	 * constructor to initialize, design user interface and action performance
	 * for Teacher tab on menu bar.
	 */
	public TeacherTab() {
		super();
	}

	/**
	 * @return list of all existing teachers
	 */
	public ArrayList<ItemViewAbstract> loadListItems() {
		ArrayList<ItemViewAbstract> list_teachers = new ArrayList<ItemViewAbstract>();

		List<NameValuePair> teacherParams = new ArrayList<NameValuePair>();
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		teacherParams.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID, String
				.valueOf(UserType.TEACHER)));
		String listTeacherResponse = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.GET_ALL_USER), teacherParams);
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
				list_teachers.add(new TeacherItem(u));
			}
		}
		return list_teachers;
	}

	/**
	 * add a new subject when click {@link #btnNewTeacher}
	 */
	public void addItem() {
		btnAddNewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddTeacher addedTeacher = new AddTeacher();
				addedTeacher.setLocationRelativeTo(null);
				addedTeacher.setVisible(true);
			}
		});
	}

}
