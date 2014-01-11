package evoter.mobile.question;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.item.ItemDataActivity;
import evoter.mobile.main.Configuration;
import evoter.mobile.main.RuntimeEVoterManager;
import evoter.mobile.model.ItemData;
import evoter.mobile.model.Question;
import evoter.mobile.utils.Utils;
import evoter.server.dao.QuestionDAO;
import evoter.server.dao.QuestionSessionDAO;
import evoter.server.dao.UserDAO;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class QuestionActivity extends ItemDataActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSessionName());

		adapter = new QuestionAdapter(listInitial, QuestionActivity.this);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				Toast.makeText(QuestionActivity.this,
						"View question" + selectQuestion.getTitle(),
						Toast.LENGTH_LONG).show();
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				Toast.makeText(
						QuestionActivity.this,
						"Process item long clicked for item: "
								+ selectQuestion.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

	}

	protected void loadListItemData() {
		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(RuntimeEVoterManager.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, RuntimeEVoterManager.getUSER_KEY());

		client.post(Configuration.get_urlGetAllQuestion(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.i("Get All Quesion Test", "response : " + response);
						try {
							ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
							JSONArray array = Utils.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								String sString = array.get(i).toString();
								JSONObject s = new JSONObject(sString);
								Question question = new Question(
										Long.parseLong(s
												.getString(QuestionDAO.ID)),
										s.getString(QuestionDAO.QUESTION_TEXT),
										s.getString(QuestionSessionDAO.SESSION_ID),
										Integer.parseInt(s
												.getString(QuestionDAO.QUESTION_TYPE_ID)));
								listQuestion.add(question);
							}
							adapter.updateList(listQuestion);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Log.i("Get All Quesion Test", "response : " +
						// response);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});

	}

}