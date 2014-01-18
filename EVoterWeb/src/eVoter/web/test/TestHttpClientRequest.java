/**
 * 
 */
package eVoter.web.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eVoter.web.utils.Utils;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;


/**
 * @author luongnv89
 * 
 */
public class TestHttpClientRequest {

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		String USER_KEY = null;
		EVoterHttpClient client = new EVoterHttpClient();

		// Check login
		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair(UserDAO.USER_NAME, "nvluong"));
		loginParams.add(new BasicNameValuePair(UserDAO.PASSWORD, "12345678"));
		
		int reponseStatus = client.post(Configuration.get_urlLogin(),
				loginParams);
		System.out.println(reponseStatus);
		if (reponseStatus == 200) {
			String content = client.getResponseContent();
			System.out.println(content);
			try {
				JSONObject item = new JSONObject(content);
				USER_KEY = item.getString(UserDAO.USER_KEY);
				if(USER_KEY!=null&&USER_KEY!="null"){
					
					}
				else{
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Get all subjects
		String SUBJECT_ID = null;
		List<NameValuePair> getAllSubjectParams = new ArrayList<NameValuePair>();
		getAllSubjectParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				USER_KEY));

		if (client.post(Configuration.get_urlGetAllSubject(),
				getAllSubjectParams) != -1) {
			String content = client.getResponseContent();
			System.out.println(content);
			JSONArray array = Utils.getJSONArray(content);
			String sItem = array.get(0).toString();
			JSONObject item = new JSONObject(sItem);
			SUBJECT_ID = String.valueOf(item.getInt(SubjectDAO.ID));
			System.out.println(SUBJECT_ID);
		}

		// Get all sessions
		String SESSION_ID = null;
		List<NameValuePair> getAllSessionParams = new ArrayList<NameValuePair>();
		getAllSessionParams.add(new BasicNameValuePair(SubjectDAO.ID,
				SUBJECT_ID));
		getAllSessionParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				USER_KEY));

		if (client.post(Configuration.get_urlGetAllSession(),
				getAllSessionParams) != -1) {
			String content = client.getResponseContent();
			System.out.println(content);
			JSONArray array = Utils.getJSONArray(content);
			JSONObject item = new JSONObject(array.getJSONObject(0).toString());
			System.out.println(item.toString());
			SESSION_ID = item.getString(SubjectDAO.ID);
			System.out.println(SESSION_ID);
		}

		// Get all sessions
		List<NameValuePair> getAllQuestionParams = new ArrayList<NameValuePair>();
		getAllQuestionParams.add(new BasicNameValuePair(SessionDAO.ID,
				SESSION_ID));
		getAllQuestionParams.add(new BasicNameValuePair(UserDAO.USER_KEY,
				USER_KEY));

		if (client.post(Configuration.get_urlGetAllQuestion(),
				getAllQuestionParams) != -1) {
			String content = client.getResponseContent();
			System.out.println(content);
		}

	}

}
