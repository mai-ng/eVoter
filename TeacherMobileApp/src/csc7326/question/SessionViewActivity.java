package csc7326.question;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import csc7326.main.Configuration;
import csc7326.main.EVoterSessionManager;
import csc7326.main.R;
import csc7326.main.Splash;
import csc7326.utils.Utils;
import evoter.server.model.Question;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionViewActivity extends Activity {

	ArrayList<Question> listQuestions = new ArrayList<Question>();
	ListView listQuestionView;
	QuestionBaseAdapter questionBaseAdapter;
	Context context;
	EditText etQuestionSearch;

	// String subjectID = "1";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_running_teacher);
		context = this;
		listQuestionView = (ListView) findViewById(R.id.lvQuestionSesionRunning);
		loadListQuestion(EVoterSessionManager.getCurrentSessionID());
		questionBaseAdapter = new QuestionBaseAdapter(listQuestions,
				SessionViewActivity.this);
		listQuestionView.setAdapter(questionBaseAdapter);

		etQuestionSearch = (EditText) findViewById(R.id.etQuestionSearchSessionRunning);
		etQuestionSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				SessionViewActivity.this.questionBaseAdapter.getFilter().filter(s);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		listQuestionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				Toast.makeText(SessionViewActivity.this,
						"View question" + selectQuestion.getQuestionText(),
						Toast.LENGTH_LONG).show();
			}
		});

		listQuestionView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				Toast.makeText(
						SessionViewActivity.this,
						"Process item long clicked for item: "
								+ selectQuestion.getQuestionText(),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});

	}

	private void loadListQuestion(long sessionID) {
		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		params.add("SESSION_ID", String.valueOf(sessionID));
		client.post(Configuration.get_urlGetAllQuestion(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONArray array = Utils.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								String sString = array.get(i).toString();
								JSONObject s = new JSONObject(sString);
								Question question = new Question(Long
										.parseLong(s.getString("ID")), s
										.getString("QUESTION_TEXT"), s
										.getString("SESSION_ID"), Integer
										.parseInt(s.getString("QUESTION_TYPE_ID")));
								listQuestions.add(question);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.i("Get All Quesion Test", "response : " + response);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mnInfalter = getMenuInflater();
		mnInfalter.inflate(R.menu.session_running_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnExit:
			Intent exitIntent = new Intent(this, Splash.class);
			exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			exitIntent.putExtra("Exit application", true);
			startActivity(exitIntent);
			finish();
			return true;
		case R.id.mnListQuestionReload:
			loadListQuestion(EVoterSessionManager.getCurrentSessionID());
			return true;
		case R.id.mnLogout:
			EVoterSessionManager eVoterSessionManager = new EVoterSessionManager(
					this);
			if (eVoterSessionManager.isLoggedIn()) {
				eVoterSessionManager.logoutUser();
			}
			eVoterSessionManager.checkLogin();
			return true;
		}
		return false;
	}
	//
	// private class LoadListSessions extends AsyncTask<Void, Void, Void> {
	// ProgressDialog dialog;
	//
	// /**
	// * Runs on the UI thread before {@link #doInBackground}.
	// *
	// * @see #onPostExecute
	// * @see #doInBackground
	// */
	// @Override
	// protected void onPreExecute() {
	// dialog = new ProgressDialog(context);
	// dialog.setTitle("Loading list session");
	// dialog.show();
	// super.onPreExecute();
	// }
	//
	// /**
	// * <p>Runs on the UI thread after {@link #doInBackground}. The
	// * specified result is the value returned by {@link #doInBackground}.</p>
	// * <p/>
	// * <p>This method won't be invoked if the task was cancelled.</p>
	// *
	// * @param aVoid The result of the operation computed by {@link
	// #doInBackground}.
	// * @see #onPreExecute
	// * @see #doInBackground
	// * @see #onCancelled(Object)
	// */
	// @Override
	// protected void onPostExecute(Void aVoid) {
	// dialog.dismiss();
	// super.onPostExecute(aVoid);
	// }
	//
	// /**
	// * Override this method to perform a computation on a background thread.
	// The
	// * specified parameters are the parameters passed to {@link #execute}
	// * by the caller of this task.
	// * <p/>
	// * This method can call {@link #publishProgress} to publish updates
	// * on the UI thread.
	// *
	// * @param params The parameters of the task.
	// * @return A result, defined by the subclass of this task.
	// * @see #onPreExecute()
	// * @see #onPostExecute
	// * @see #publishProgress
	// */
	// @Override
	// protected Void doInBackground(Void... params) {
	// String content = InternetChecking.getData(sessionURL);
	// listSessions.clear();
	// try {
	// JSONObject jsonObject = new JSONObject(content);
	// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
	// JSONArray array = jsonObject1.getJSONArray("items");
	// for (int i = 0; i < array.length(); i++) {
	//
	// JSONObject detailInfor = array.getJSONObject(i).getJSONObject("video");
	// Session sessionData = new Session(detailInfor.getString("id"),
	// detailInfor.getString("title"), detailInfor.getString("uploader"),
	// detailInfor.getString("viewCount"), detailInfor.getString("uploaded"));
	// int count = Integer.parseInt(sessionData.getStatus());
	// if (count % 2 == 0)
	// sessionData.setStatus(count + " - Status: Wait for accepting");
	// else {
	// sessionData.setStatus(count + " - Status: accepted");
	// }
	// if (count % 7 == 0) sessionData.setActive(true);
	// else {
	// sessionData.setActive(false);
	// }
	// listSessions.add(sessionData);
	// }
	//
	// sessionBaseAdapter.notifyDataSetChanged();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	// }
}