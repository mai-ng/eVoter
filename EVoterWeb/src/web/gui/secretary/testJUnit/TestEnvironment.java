/**
 * 
 */
package web.gui.secretary.testJUnit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import web.applet.RunningTimeData;
import web.gui.secretary.AddSubject;
import web.gui.secretary.AddUser;
import web.gui.secretary.EditSubject;
import web.gui.secretary.EditUser;
import web.gui.secretary.ViewSubject;
import web.gui.secretary.ViewUser;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import web.util.Utils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * define the secretary log in to system.<br>
 * get user (teacher/student) from database to test {@link AddUser}, {@link EditUser}, {@link ViewUser}.<br>
 * get subject from database to test {@link AddSubject}, {@link EditSubject}, and {@link ViewSubject}.
 * @author maint
 * 
 */
public class TestEnvironment {
	
	//define the user (secretary) log in to the web application.
	String userName = "nvluong";
	String password = "12345678";

	/**
	 * Login to system to get User_key for next tests
	 */
	public void dologin() {
		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair(UserDAO.USER_NAME, userName));
		loginParams.add(new BasicNameValuePair(UserDAO.PASSWORD, password));
		String response = EVoterHTTPRequest.excutePost(
				RequestConfig.getURL(URIRequest.LOGIN), loginParams);
		if (response != null) {
			System.out.println("Login successful with response: " + response
					+ ". Go to next page");
			org.json.JSONObject userkeyJson = new org.json.JSONObject(response);
			String userkey = userkeyJson.getString(UserDAO.USER_KEY);
			if (userkey != null) {
				System.out.println(userkey);
				RunningTimeData.setCurrentUserKey(userkey);
			}
		}
	}
	
	/**
	 * Get the first subject which has title content input keyword
	 * @param keyword is  keyword of a subject
	 */
	public Subject getSubject(String keyword){
		ArrayList<Subject> listSubjects = getListSubject();
		for(int i=0;i<listSubjects.size();i++){
			if(listSubjects.get(i).getTitle().contains(keyword)) return listSubjects.get(i);
		}
		Utils.informDialog("Cannot find any subject which has title content keyword: "+ keyword);
		return null;
	}
	
	/**
	 * @return all existing subjects for this user.
	 */
	private ArrayList<Subject> getListSubject() {
		ArrayList<Subject> listSubjects = new ArrayList<Subject>();
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
				listSubjects.add(sb);
			}
		}
		return listSubjects;
	}

	/**
	 * Get a user by input user's type (teacher or student) and email
	 * @param user_type
	 * @param email
	 * @return the user whose email meets input.
	 */
	public User getUser(long user_type,String email){
		ArrayList<User> listUsers = getListUserByType(user_type);
		for(int i=0;i<listUsers.size();i++){
			if(listUsers.get(i).getEmail().equals(email)) return listUsers.get(i);
		}
		Utils.informDialog("Cannot find user who has email: "+ email +" and type: " + user_type);
		return null;
	}
	
	/**
	 *Get all existing user with the type is <code>user_type</code>.
	 * @param user_type
	 * @return list of users whose type is an input
	 */
	private ArrayList<User> getListUserByType(long user_type){
		ArrayList<User> listUsers = new ArrayList<User>();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_KEY,
				RunningTimeData.getCurrentUserKey()));
		params.add(new BasicNameValuePair(UserDAO.USER_TYPE_ID, String
				.valueOf(user_type)));
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
				listUsers.add(u);
			}
		}
		return listUsers;
	}

}
