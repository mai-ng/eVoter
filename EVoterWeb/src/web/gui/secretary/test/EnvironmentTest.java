/**
 * 
 */
package web.gui.secretary.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import web.applet.RunningTimeData;
import web.util.EVoterHTTPRequest;
import web.util.RequestConfig;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**
 * @author maint
 * 
 */
public class EnvironmentTest {
	String userName = "paul_gibson";
	String password = "12345678";

	public EnvironmentTest() {
	}

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

}
