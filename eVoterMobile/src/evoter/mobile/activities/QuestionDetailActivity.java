/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.QuestionType;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Update by @author luongnv89 on 09-Feb-2014:<br>
 * <li>Parser answer for Multi-choice question <li>Parser answer for input
 * answer question <br>
 * Created by @author luongnv89 on 18-Jan-2014 <br>
 * {@link QuestionDetailActivity} manage a question <li>With teacher: <br>
 * - Delete send question to student <br>
 * - Edit question <br>
 * - ... <li>With student:<br>
 * - Submit answer
 */
public class QuestionDetailActivity extends EVoterActivity {
	
	private final String STOP = "Stop receive answer";
	private final String SEND = "Send";
	private final String SUBMIT = "Submit";
	TextView tvQuestionText;
	LinearLayout answerArea;
	Button btSend;
	Button btView;
	RadioGroup groups;
	ArrayList<Answer> column1;
	ArrayList<CheckBox> listCheckBox;
	long idAnswer = -1;
	String statistic;
	EditText etAnswer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view_detail);
		this.tvTitleBarContent.setText(EVoterShareMemory.getCurrentSessionName());
		this.ivTitleBarRefresh.setVisibility(View.GONE);
		mainMenu.setQuestionActivityMenu();
		Log.i("Current Question: ", EVoterShareMemory.getCurrentQuestion().getTitle());
		tvQuestionText = (TextView) findViewById(R.id.tvQuestionText);
		tvQuestionText.setText(EVoterShareMemory.getCurrentQuestion().getQuestionText());
		
		answerArea = (LinearLayout) findViewById(R.id.loAnswerArea);
		
		//Parser the answer of question
		column1 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1());
		
		//		type = 1;
		buidAnswerArea();
		
		btSend = (Button) findViewById(R.id.btSendQuestion);
		setupAction();
		btView = (Button) findViewById(R.id.btViewStatistic);
		btView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent statisticActivity = new Intent(QuestionDetailActivity.this, QuestionStatisticActivity.class);
				startActivity(statisticActivity);
			}
		});
	}
	
	/**
	 * 
	 */
	private void setupAction() {
		if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
			btSend.setText(SEND);
		} else if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT) {
			btSend.setText(SUBMIT);
		}
		
		if (!EVoterShareMemory.getCurrentSession().isActive()) {
			btSend.setEnabled(false);
		}
		
		btSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (btSend.getText().toString().equals(SEND)) {
					sendQuestion();
				} else if (btSend.getText().toString().equals(SUBMIT)) {
					submitAnswer();
				} else if (btSend.getText().toString().equals(STOP)) {
					stopReceiveAnswer();
				}
				
			}
		});
	}
	
	/**
	 * 
	 */
	protected void stopReceiveAnswer() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		client.post(RequestConfig.getURL(URIRequest.STOP_SEND_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if (response.contains("SUCCESS")) {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
					btSend.setText(SEND);
				}
				else {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Cannot send question: " + response);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * 
	 */
	protected void submitAnswer() {
		switch ((int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId()) {
			case QuestionType.YES_NO:
			case QuestionType.MULTI_RADIOBUTTON:
				if (idAnswer == -1) {
					EVoterMobileUtils.showeVoterToast(this, "You have to choose one answer before submit");
				} else {
					doVote(idAnswer, statistic);
				}
				break;
			case QuestionType.MULTI_CHECKBOX:
				boolean hasAnswer = false;
				for (int i = 0; i < listCheckBox.size(); i++) {
					if (listCheckBox.get(i).isChecked()) {
						doVote(column1.get(i).getId(), null);
						hasAnswer = true;
					}
				}
				if (!hasAnswer) {
					EVoterMobileUtils.showeVoterToast(this, "You have to choose at least one answer before submit");
				}
				break;
			case QuestionType.SLIDER:
				if (idAnswer == -1) {
					EVoterMobileUtils.showeVoterToast(this, "You have to choose at least one answer before submit");
				} else {
					doVote(column1.get(0).getId(), statistic);
				}
				break;
			case QuestionType.INPUT_ANSWER:
				statistic = etAnswer.getText().toString();
				if (statistic == null || statistic.equals("")) {
					EVoterMobileUtils.showeVoterToast(this, "You have to fill an answer before submit");
				} else {
					doVote(column1.get(0).getId(), statistic);
				}
				break;
			case QuestionType.MATCH:
				EVoterMobileUtils.showeVoterToast(this, "Not implemented yet");
				break;
			default:
				break;
		}
	}
	
	/**
	 * 
	 */
	protected void doVote(long answerID, String statistic) {
		//TODO: Submit answer
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getQuestionTypeId()));
		params.put(AnswerDAO.ID, String.valueOf(answerID));
		if (statistic != null)
			params.put(AnswerDAO.STATISTICS, statistic);
		client.post(RequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if (response.contains("SUCCESS")) {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
					btSend.setEnabled(false);
				}
				else {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Cannot send question: " + response);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
		
	}
	
	/**
	 * 
	 */
	protected void sendQuestion() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		client.post(RequestConfig.getURL(URIRequest.SEND_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if (response.contains("SUCCESS")) {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
					btSend.setText(STOP);
					
				}
				else {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
							"Cannot send question: " + response);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
	}
	
	/**
	 * @param type
	 * @param column1
	 */
	private void buidAnswerArea() {
		groups = new RadioGroup(this);
		groups.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listCheckBox = new ArrayList<CheckBox>();
		etAnswer = new EditText(this);
		etAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		switch ((int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId()) {
			case QuestionType.YES_NO:
			case QuestionType.MULTI_RADIOBUTTON:
				for (int i = 0; i < column1.size(); i++) {
					RadioButton ans = new RadioButton(this);
					ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					ans.setText(column1.get(i).getAnswerText());
					ans.setId(i);
					if (i == 0) ans.setChecked(true);
					groups.addView(ans);
					
				}
				
				groups.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						idAnswer = column1.get(checkedId).getId();
					}
				});
				answerArea.addView(groups);
				break;
			case QuestionType.MULTI_CHECKBOX:
				for (int i = 0; i < column1.size(); i++) {
					CheckBox ans = new CheckBox(this);
					ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					ans.setText(column1.get(i).getAnswerText());
					ans.setId(i);
					listCheckBox.add(ans);
					answerArea.addView(ans);
				}
				break;
			case QuestionType.SLIDER:
				SeekBar seekbar = new SeekBar(this);
				seekbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				int max = Integer.parseInt(column1.get(0).getAnswerText());
				seekbar.setMax(max);
				seekbar.setProgress(max / 2);
				seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						statistic = String.valueOf(progress);
					}
				});
				answerArea.addView(seekbar);
				break;
			case QuestionType.INPUT_ANSWER:
				etAnswer.setHint("Your answer here");
				answerArea.addView(etAnswer);
				break;
			case QuestionType.MATCH:
				//				if (!EVoterShareMemory.getCurrentQuestion().getAnswerColumn2().equals("")) {
				//					ArrayList<Answer> column2 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn2());
				//				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * @param answerColumn1
	 * @return
	 */
	private ArrayList<Answer> parserAnswer(String answerColumn1) {
		ArrayList<Answer> listAnswers = new ArrayList<Answer>();
		try {
			JSONArray listAnswer1 = new JSONArray(answerColumn1);
			for (int i = 0; i < listAnswer1.length(); i++) {
				Answer answer = parserJSONObjectToAnswer(listAnswer1.getJSONObject(i));
				if (answer != null) listAnswers.add(answer);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listAnswers;
	}
	
	/**
	 * @param jsonObject
	 * @return
	 */
	private Answer parserJSONObjectToAnswer(JSONObject jsonObject) {
		try {
			return new Answer(jsonObject.getLong(AnswerDAO.QUESTION_ID), jsonObject.getString(AnswerDAO.ANSWER_TEXT));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
