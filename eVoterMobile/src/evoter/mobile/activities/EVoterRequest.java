/**
 * 
 */
package evoter.mobile.activities;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.ItemData;
import evoter.share.model.Subject;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 *
 */
public class EVoterRequest {
	protected AsyncHttpClient client;
	protected Context context;
	ArrayList<ItemData> listItemData;
	/**
	 * 
	 */
	public EVoterRequest(Context context) {
		client = new AsyncHttpClient(RequestConfig.TIME_OUT);
		this.context = context;
		listItemData = new ArrayList<ItemData>();
	}


	/**
	 * 
	 */
	public ArrayList<ItemData> loadListSubjects() {
		RequestParams params = new RequestParams();
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						extractSubjectFromResponse(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get all subjects", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		return listItemData;
	}
	
	
	/**
	 * @param response
	 */
	private void extractSubjectFromResponse(String response) {
		try {
			ArrayList<ItemData> newList = new ArrayList<ItemData>();
			JSONArray array = EVoterMobileUtils.getJSONArray(response);
			
			for (int i = 0; i < array.length(); i++) {
				String sItem = array.get(i).toString();
				JSONObject item = new JSONObject(sItem);
//				Log.i("JSON TEST: ", item.toString());
				Subject subject = null;
				try {
					subject = new Subject(
							Long.parseLong(item
									.getString(SubjectDAO.ID)),
							item.getString(SubjectDAO.TITLE),
							EVoterMobileUtils.convertToDate(item
									.getString(SubjectDAO.CREATION_DATE)));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newList.add(subject);
			}
			if (newList.isEmpty()) {
				EVoterMobileUtils.showeVoterToast(context,
						"There isn't any subject!");
			}
			listItemData.addAll(newList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
