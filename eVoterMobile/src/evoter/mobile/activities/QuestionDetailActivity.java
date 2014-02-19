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
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.QuestionDAO;
import evoter.share.model.Answer;
import evoter.share.model.QuestionType;
import evoter.share.model.Session;
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
	
	private final String STOP = "Stop receiving answer";
	private final String SEND = "Send";
	private final String SUBMIT = "Submit";
	
	TextView tvQuestionText;
	LinearLayout answerArea;
	Button btSend;
	Button btViewStatistic;
	RadioGroup groups;
	ArrayList<Answer> answers;
	ArrayList<CheckBox> listCheckBox;
	long idAnswer = -1;
	String statistic;
	EditText etAnswer;
	TextView tvMaxShow;
	TextView tvAnswerShow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view_detail);
		initComponents();
	}
	
	/**
	 * Init components of {@link QuestionDetailActivity}
	 */
	private void initComponents() {
		tvQuestionText = (TextView) findViewById(R.id.tvQuestionText);
		answerArea = (LinearLayout) findViewById(R.id.loAnswerArea);
		btSend = (Button) findViewById(R.id.btSendQuestion);
		btViewStatistic = (Button) findViewById(R.id.btViewStatistic);
		groups = new RadioGroup(this);
		groups.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listCheckBox = new ArrayList<CheckBox>();
		etAnswer = new EditText(this);
		etAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		btSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setupSendButtonAction();
			}
		});
		btViewStatistic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent statisticActivity = new Intent(QuestionDetailActivity.this, QuestionStatisticActivity.class);
				startActivity(statisticActivity);
			}
		});
		builGUI();
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupContentMainMenu()
	 */
	@Override
	protected void setupContentMainMenu() {
		// TODO Auto-generated method stub
		super.setupContentMainMenu();
		setQuestionActivityMenu();
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentSessionName());
	}
	
	/**
	 * Build question detail
	 */
	private void builGUI() {
		tvQuestionText.setText(EVoterShareMemory.getCurrentQuestion().getQuestionText());
		answerArea.removeAllViews();
		listCheckBox.clear();
		groups.removeAllViews();
		Log.i("Question to build: ", EVoterShareMemory.getCurrentQuestion().toString());
		answers = EVoterMobileUtils.parserAnswerArray(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1(), EVoterShareMemory.getCurrentQuestion().getId());
		if (answers.isEmpty()) {
			EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Cannot get answer of question!");
		} else {
			if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
				if (EVoterShareMemory.getCurrentQuestion().getStatus() == 0) {
					btSend.setText(SEND);
				} else if (EVoterShareMemory.getCurrentQuestion().getStatus() == 1) {
					btSend.setText(STOP);
				} else if (EVoterShareMemory.getCurrentQuestion().getStatus() == 2) {
					btSend.setVisibility(View.GONE);
				}
			} else if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT) {
				btSend.setText(SUBMIT);
				if (EVoterShareMemory.getCurrentQuestion().getStatus() != 1 || EVoterShareMemory.getListIDAnsweredQuestion().contains(EVoterShareMemory.getCurrentQuestion().getId())) {
					btSend.setVisibility(View.GONE);
				}
			}
			
			if (!EVoterShareMemory.getCurrentSession().isActive()) btSend.setEnabled(false);
			buidAnswerArea();
		}
	}
	
	/**
	 * Submit answer to server
	 */
	private void submitAnswerToServer() {
		if (!EVoterShareMemory.getCurrentSession().isActive()) {
			EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Session is closed. You cannot send answer!");
		} else {
			long questionTypeID = EVoterShareMemory.getCurrentQuestion().getQuestionTypeId();
			switch ((int) questionTypeID) {
				case QuestionType.YES_NO:
				case QuestionType.MULTI_RADIOBUTTON:
					if (idAnswer == -1) {
						EVoterMobileUtils.showeVoterToast(this, "You have to choose one answer before submit");
					} else {
						EVoterRequestManager.doVote(idAnswer, questionTypeID, statistic, QuestionDetailActivity.this);
					}
					break;
				case QuestionType.MULTI_CHECKBOX:
					boolean hasAnswer = false;
					for (int i = 0; i < listCheckBox.size(); i++) {
						if (listCheckBox.get(i).isChecked()) {
							EVoterRequestManager.doVote(answers.get(i).getId(), questionTypeID, null, QuestionDetailActivity.this);
							hasAnswer = true;
						}
					}
					if (!hasAnswer) {
						EVoterMobileUtils.showeVoterToast(this, "You have to choose at least one answer before submit");
					}
					break;
				case QuestionType.SLIDER:
					if (statistic == null) {
						EVoterMobileUtils.showeVoterToast(this, "You have to choose at least one answer before submit");
					} else {
						EVoterRequestManager.doVote(answers.get(0).getId(), questionTypeID, statistic, QuestionDetailActivity.this);
					}
					break;
				case QuestionType.INPUT_ANSWER:
					statistic = etAnswer.getText().toString();
					if (statistic == null || statistic.equals("")) {
						EVoterMobileUtils.showeVoterToast(this, "You have to fill an answer before submit");
					} else {
						EVoterRequestManager.doVote(answers.get(0).getId(), questionTypeID, statistic, QuestionDetailActivity.this);
					}
					break;
				case QuestionType.MATCH:
					EVoterMobileUtils.showeVoterToast(this, "Not implemented yet");
					break;
				default:
					break;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String, java.lang.String)
	 */
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.CHECK_QUESTION_STATUS_EVOTER_REQUEST)) {
			try {
				JSONArray array = new JSONArray(response);
				JSONObject ob = array.getJSONObject(0);
				int status = ob.getInt(QuestionDAO.STATUS);
				Log.i("Get question status", String.valueOf(status));
				EVoterShareMemory.getCurrentQuestion().setStatus(status);
				if (EVoterShareMemory.getCurrentQuestion().getStatus() == 1) {
					EVoterRequestManager.checkSessionStatus(EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
				} else {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Time out! Your will not be count in the statistic of question!");
					btSend.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		else if (callBackMessage.equals(CallBackMessage.SEND_QUESTION_EVOTER_REQUEST)) {
			if (response.equals("SUCCESS")) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
				btSend.setText(STOP);
				
			}
			else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Cannot send question: " + response);
			}
		} else if (callBackMessage.equals(CallBackMessage.CHECK_SESSION_STATUS_EVOTER_REQUEST)) {
			try {
				JSONArray array = new JSONArray(response);
				Session session = EVoterMobileUtils.parserToSession(array.getJSONObject(0));
				if (session != null) EVoterShareMemory.setCurrentSession(session);
				submitAnswerToServer();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (callBackMessage.equals(CallBackMessage.SUBMIT_ANSWER_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Success!");
				EVoterShareMemory.addAnsweredQuestion(EVoterShareMemory.getCurrentQuestion().getId());
				btSend.setVisibility(View.GONE);
			} else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Cannot send answer : " + response.replace(CallBackMessage.SUBMIT_ANSWER_EVOTER_REQUEST, ""));
			}
			
		} else if (callBackMessage.equals(CallBackMessage.STOP_RECEIVE_ANSWER_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						response);
				btSend.setVisibility(View.GONE);
			}
			else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Fail: " + response);
			}
		} else if (callBackMessage.equals(CallBackMessage.SUBMIT_ANSWER_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(this,
						"Successful!");
				EVoterShareMemory.addAnsweredQuestion(EVoterShareMemory.getCurrentQuestion().getId());
				btSend.setVisibility(View.GONE);
			}
			else {
				EVoterMobileUtils.showeVoterToast(this,
						"Fail: " + response);
			}
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
		
	}
	
	/**
	 * Build answer area for question
	 * 
	 * @param type
	 * @param column1
	 */
	private void buidAnswerArea() {
		
		switch ((int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId()) {
			case QuestionType.YES_NO:
				mutiRadioButtonArea();
				break;
			case QuestionType.MULTI_RADIOBUTTON:
				mutiRadioButtonArea();
				break;
			case QuestionType.MULTI_CHECKBOX:
				multiCheckboxArea();
				break;
			case QuestionType.SLIDER:
				seekBarArea();
				break;
			case QuestionType.INPUT_ANSWER:
				etAnswer.setHint("Your answer here");
				answerArea.addView(etAnswer);
				break;
			case QuestionType.MATCH:
				break;
			default:
				break;
		}
	}
	
	/**
	 * Build seekbar area for slider question
	 */
	private void seekBarArea() {
		tvMaxShow = new TextView(this);
		tvMaxShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		tvAnswerShow = new TextView(this);
		tvAnswerShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		SeekBar seekbar = new SeekBar(this);
		seekbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		int max = Integer.parseInt(answers.get(0).getAnswerText());
		seekbar.setMax(max);
		tvMaxShow.setText("Max value: " + max);
		seekbar.setProgress(max / 2);
		statistic = String.valueOf(max / 2);
		tvAnswerShow.setText("Your value: " + statistic);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Your value is: " + statistic);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				statistic = String.valueOf(progress);
				tvAnswerShow.setText("Your value: " + statistic);
			}
		});
		answerArea.addView(seekbar);
		answerArea.addView(tvMaxShow);
		answerArea.addView(tvAnswerShow);
	}
	
	/**
	 * buld answer area of multi check box question
	 */
	private void multiCheckboxArea() {
		for (int i = 0; i < answers.size(); i++) {
			CheckBox ans = new CheckBox(this);
			ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			ans.setText(answers.get(i).getAnswerText());
			ans.setId(i);
			listCheckBox.add(ans);
			answerArea.addView(ans);
		}
	}
	
	/**
	 * Build answer of multi radio question
	 */
	private void mutiRadioButtonArea() {
		for (int i = 0; i < answers.size(); i++) {
			RadioButton ans = new RadioButton(this);
			ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			ans.setText(answers.get(i).getAnswerText());
			ans.setId(i);
			if (i == 0) ans.setChecked(true);
			groups.addView(ans);
			
		}
		idAnswer = answers.get(0).getId();
		groups.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				idAnswer = answers.get(checkedId).getId();
			}
		});
		answerArea.addView(groups);
	}
	
	/**
	 * Setup action for btSend
	 */
	private void setupSendButtonAction() {
		if (btSend.getText().toString().equals(SEND)) {
			EVoterRequestManager.sendQuestion(EVoterShareMemory.getCurrentQuestion().getId(), EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
		} else if (btSend.getText().toString().equals(SUBMIT)) {
			EVoterRequestManager.updateQuestionStatus(EVoterShareMemory.getCurrentQuestion().getId(), QuestionDetailActivity.this);
		} else if (btSend.getText().toString().equals(STOP)) {
			EVoterRequestManager.stopReceiveAnswer(EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		EVoterRequestManager.updateQuestion(QuestionDetailActivity.this);
	}
	
}
