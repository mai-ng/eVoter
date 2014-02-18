/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
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
	
	private final String STOP = "Stop receive answer";
	private final String SEND = "Send";
	private final String SUBMIT = "Submit";
	private final String VIEW_STATISTIC = "View statistic";
	
	TextView tvQuestionText;
	LinearLayout answerArea;
	Button btSend;
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
		this.tvTitleBarContent.setText(EVoterShareMemory.getCurrentSessionName());
		mainMenu.setQuestionActivityMenu();
		EVoterRequestManager.updateQuestion(EVoterShareMemory.getCurrentQuestion());
		mainMenu.getBtStartSession().setVisibility(View.GONE);
		Log.i("Current Question: ", EVoterShareMemory.getCurrentQuestion().getTitle());
		tvQuestionText = (TextView) findViewById(R.id.tvQuestionText);
		answerArea = (LinearLayout) findViewById(R.id.loAnswerArea);
		//Parser the answer of question
		answers = EVoterMobileUtils.parserListAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1(), EVoterShareMemory.getCurrentQuestion().getId());
		
		btSend = (Button) findViewById(R.id.btSendQuestion);
		groups = new RadioGroup(this);
		groups.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listCheckBox = new ArrayList<CheckBox>();
		etAnswer = new EditText(this);
		etAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tvQuestionText.setText(EVoterShareMemory.getCurrentQuestion().getQuestionText());
		buidAnswerArea();
		setButtonLabel();
		setupButtonAction();
	}
	
	/**
	 * 
	 */
	private void setButtonLabel() {
		if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
			if (EVoterShareMemory.getCurrentQuestion().getStatus() == 0) {
				btSend.setText(SEND);
			} else if (EVoterShareMemory.getCurrentQuestion().getStatus() == 1) {
				btSend.setText(STOP);
			} else if (EVoterShareMemory.getCurrentQuestion().getStatus() == 2) {
				btSend.setText(VIEW_STATISTIC);
			}
		} else if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT) {
			if (EVoterShareMemory.getCurrentQuestion().getStatus() == 2 || EVoterShareMemory.getListIDAnsweredQuestion().contains(EVoterShareMemory.getCurrentQuestion().getId())) {
				btSend.setText(VIEW_STATISTIC);
			}
			else if (EVoterShareMemory.getCurrentQuestion().getStatus() == 1) {
				btSend.setText(SUBMIT);
			}
		}
		
		if (!EVoterShareMemory.getCurrentSession().isActive()) {
			if (!btSend.getText().toString().equals(VIEW_STATISTIC)) btSend.setEnabled(false);
		}
		
	}
	
	/**
	 * 
	 */
	private void setupButtonAction() {
		
		btSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (btSend.getText().toString().equals(SEND)) {
					EVoterRequestManager.sendQuestion(EVoterShareMemory.getCurrentQuestion().getId(), EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
				} else if (btSend.getText().toString().equals(SUBMIT)) {
					EVoterRequestManager.updateQuestionStatus(EVoterShareMemory.getCurrentQuestion().getId(), QuestionDetailActivity.this);
				} else if (btSend.getText().toString().equals(STOP)) {
					EVoterRequestManager.stopReceiveAnswer(EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
				} else if (btSend.getText().toString().equals(VIEW_STATISTIC)) {
					ActivityManager.viewQuestionStatistic(EVoterShareMemory.getCurrentQuestion(), QuestionDetailActivity.this);
				}
				
			}
		});
	}
	
	/**
	 * 
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
	
	public void updateRequestCallBack(String response) {
		if (response.contains(CallBackMessage.EVOTER_REQUEST_CHECK_QUESTION_STATUS)) {
			response = response.replace(CallBackMessage.EVOTER_REQUEST_CHECK_QUESTION_STATUS, "");
			try {
				JSONArray array = new JSONArray(response);
				JSONObject ob = array.getJSONObject(0);
				int status = ob.getInt(QuestionDAO.STATUS);
				Log.i("Get question status", String.valueOf(status));
				EVoterShareMemory.getCurrentQuestion().setStatus(status);
				setButtonLabel();
				if (EVoterShareMemory.getCurrentQuestion().getStatus() == 1) {
					EVoterRequestManager.checkSessionStatus(EVoterShareMemory.getCurrentSession().getId(), QuestionDetailActivity.this);
				} else {
					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Time out! Your will not be count in the statistic of question!");
					btSend.setText(VIEW_STATISTIC);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		else if (response.contains(CallBackMessage.EVOTER_REQUEST_SEND_QUESTION)) {
			if (response.contains("SUCCESS")) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
				btSend.setText(STOP);
				
			}
			else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Cannot send question: " + response);
			}
		} else if (response.contains(CallBackMessage.EVOTER_REQUEST_CHECK_SESSION_STATUS)) {
			Log.i("View session", response);
			JSONArray array;
			try {
				array = new JSONArray(response);
				Session session = EVoterMobileUtils.parserSession(array.getJSONObject(0));
				if (session != null) EVoterShareMemory.setCurrentSession(session);
				submitAnswerToServer();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (response.contains(CallBackMessage.EVOTER_REQUEST_SUBMIT_ANSWER)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Success!");
				EVoterShareMemory.addAnsweredQuestion(EVoterShareMemory.getCurrentQuestion().getId());
				btSend.setText(VIEW_STATISTIC);
			} else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this, "Cannot send answer : " + response.replace(CallBackMessage.EVOTER_REQUEST_SUBMIT_ANSWER, ""));
			}
			
		} else if (response.contains(CallBackMessage.EVOTER_REQUEST_STOP_RECEIVE_ANSWER)) {
			if (response.contains("SUCCESS")) {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						response);
				btSend.setText(VIEW_STATISTIC);
			}
			else {
				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
						"Fail: " + response);
			}
		} else if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
			EVoterMobileUtils.showeVoterToast(this,
					"Successful!");
			EVoterShareMemory.addAnsweredQuestion(EVoterShareMemory.getCurrentQuestion().getId());
			btSend.setText(VIEW_STATISTIC);
		}
		else {
			EVoterMobileUtils.showeVoterToast(this,
					"Fail: " + response);
		}
		
	}
	
	/**
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
				//				if (!EVoterShareMemory.getCurrentQuestion().getAnswerColumn2().equals("")) {
				//					ArrayList<Answer> column2 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn2());
				//				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * 
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
	 * 
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
	 * 
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
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		EVoterShareMemory.getPreviousContext().refreshData();
		//		mainMenu.getBtStartSession().setVisibility(View.GONE);
	}
	
}
