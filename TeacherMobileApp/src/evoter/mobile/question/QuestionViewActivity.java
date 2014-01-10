package evoter.mobile.question;

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

import evoter.mobile.main.Configuration;
import evoter.mobile.main.EVoterActivity;
import evoter.mobile.main.EVoterSessionManager;
import evoter.mobile.main.R;
import evoter.mobile.main.Splash;
import evoter.mobile.utils.Utils;
import evoter.server.dao.QuestionDAO;
import evoter.server.dao.QuestionSessionDAO;
import evoter.server.dao.UserDAO;
import evoter.server.model.Question;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class QuestionViewActivity extends EVoterActivity {

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
		this.tvTitleBarContent.setText(EVoterSessionManager
				.getCurrentSessionName());
		listQuestionView = (ListView) findViewById(R.id.lvQuestionSesionRunning);
		loadListQuestion();
		questionBaseAdapter = new QuestionBaseAdapter(listQuestions,
				QuestionViewActivity.this);
		listQuestionView.setAdapter(questionBaseAdapter);

		etQuestionSearch = (EditText) findViewById(R.id.etQuestionSearchSessionRunning);
		etQuestionSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				QuestionViewActivity.this.questionBaseAdapter.getFilter()
						.filter(s);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		listQuestionView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Question selectQuestion = (Question) parent
								.getItemAtPosition(position);
						Toast.makeText(
								QuestionViewActivity.this,
								"View question"
										+ selectQuestion.getQuestionText(),
								Toast.LENGTH_LONG).show();
					}
				});

		listQuestionView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Question selectQuestion = (Question) parent
								.getItemAtPosition(position);
						Toast.makeText(
								QuestionViewActivity.this,
								"Process item long clicked for item: "
										+ selectQuestion.getQuestionText(),
								Toast.LENGTH_SHORT).show();
						return true;
					}
				});

	}

	private void loadListQuestion() {
		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(EVoterSessionManager.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterSessionManager.getUserKey());

		client.post(Configuration.get_urlGetAllQuestion(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.i("Get All Quesion Test", "response : " + response);
						try {
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
								listQuestions.add(question);
							}
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