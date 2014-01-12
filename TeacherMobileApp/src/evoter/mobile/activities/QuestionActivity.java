package evoter.mobile.activities;

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

import evoter.mobile.adapters.QuestionAdapter;
import evoter.mobile.models.ItemData;
import evoter.mobile.models.Question;
import evoter.mobile.objects.Configuration;
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

		adapter = new QuestionAdapter(QuestionActivity.this);
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

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * com.loopj.android.http.AsyncHttpResponseHandler#onFinish
					 * ()
					 */
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						tvLoadingStatus.setText("Finished");
						tvLoadingStatus.setVisibility(View.GONE);
						progressBar.setVisibility(View.GONE);
						etSearch.setVisibility(View.VISIBLE);
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * com.loopj.android.http.AsyncHttpResponseHandler#onStart()
					 */
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						tvLoadingStatus.setText("Loading...");
						tvLoadingStatus.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.VISIBLE);
						etSearch.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(String response) {
						Log.i("Get All Quesion Test", "response : " + response);
						try {
							ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
							JSONArray array = Utils.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								progressBar.setProgress((i + 1) * 100
										/ array.length());
								tvLoadingStatus.setText("Loading..." + (i + 1)
										* 100 / array.length());
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
							if (listQuestion.isEmpty()) {
								Utils.showeVoterToast(QuestionActivity.this,
										"There isn't any question!");
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