package evoter.mobile.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import evoter.mobile.abstracts.ItemDataActivity;
import evoter.mobile.adapters.QuestionAdapter;
import evoter.mobile.main.ActivityManager;
import evoter.mobile.main.EVoterMainMenu;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.model.ItemData;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
import evoter.share.model.Session;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Updated by @author luongnv89 on 13-Feb-2014 <br>
 * <li>change behaviour for click question event -> always show the detail of
 * question <br>
 * Updated by @author luongnv89 on 18-Jan-2014 <br> <li>Add 2 seekbar for
 * difficult and bored value of session - only add when usertype is student and
 * session is active {@link QuestionActivity} extend from
 * {@link ItemDataActivity} manages questions in a session. Created by luongnv89
 * on 06/12/13.
 */
public class QuestionActivity extends ItemDataActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.ItemDataActivity#initComponent()
	 */
	@Override
	public void initComponent() {
		// TODO Auto-generated method stub
		super.initComponent();
		adapter = new QuestionAdapter(QuestionActivity.this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && !EVoterShareMemory.userJoinedSession()) {
					EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "You have to accept joining session before go to detail question!");
				} else {
					Question selectQuestion = (Question) parent
							.getItemAtPosition(position);
					EVoterShareMemory.setCurrentQuestion(selectQuestion);
					ActivityManager.startQuestionDetailActivity(QuestionActivity.this);
				}
			}
		});
		
		if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					final Question selectQuestion = (Question) parent
							.getItemAtPosition(position);
					EVoterShareMemory.setCurrentQuestion(selectQuestion);
					longClickQuestionAction();
					return true;
				}
			});
		}
		buildStaticSlider();
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupMainMenu()
	 */
	@Override
	protected void setupMainMenu() {
		// TODO Auto-generated method stub
		super.setupMainMenu();
		mainMenu.getBtChangeSessionStatus().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mainMenu.getBtChangeSessionStatus().getText().toString().contains(EVoterMainMenu.MN_STOP_SESSION)) {
					if (okChangeStatus(false)) {
						EVoterRequestManager.changeSessionStatus(false, EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
					} else {
						EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There is some question still waiting for answer, you cannot stop session");
					}
					mainMenu.dismiss();
				} else if (mainMenu.getBtChangeSessionStatus().getText().toString().contains(EVoterMainMenu.MN_START_SESSION)) {
					if (okChangeStatus(true)) {
						EVoterRequestManager.changeSessionStatus(true, EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
					}
					mainMenu.dismiss();
				}
				
			}
		});
		
		mainMenu.getBtJoin().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EVoterRequestManager.acceptSession(EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
				mainMenu.dismiss();
			}
		});
		
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
		tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSessionName());
	}
	
	/**
	 * Check condition before change the status of session
	 * 
	 * @param start <br>
	 * <br>
	 *            start = true -> change current session to active <br>
	 *            else -> change current session to inactive
	 * @return false if there is some question which is not stop receive answer. <br>
	 *         otherwiser return true;
	 */
	private boolean okChangeStatus(boolean start) {
		if (!start) {
			for (int i = 0; i < EVoterShareMemory.getListQuestions().size(); i++) {
				if (EVoterShareMemory.getListQuestions().get(i).getStatus() == 1) {
					EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "Question : " + EVoterShareMemory.getListQuestions().get(i).getTitle() + " is waiting for answer!");
					return false;
				}
			}
			return true;
		} else {
			if (!EVoterShareMemory.getListActiveSessions().isEmpty()) {
				EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There is some session is running! You cannot start more than 1 session at the same time");
				return false;
			} else
				return true;
		}
	}
	
	/**
	 * Build static seekbar of current session <br>
	 * Only show with condition: <br>
	 * - student user <br>
	 * - user has accepted to join session <br>
	 * - session is active status <br>
	 * Each time user change the value on seekbar the value will automatically
	 * send to server <br>
	 * To see the votes of student about session, teacher can open
	 * {@link StudentFeedbackActivity}
	 */
	private void buildStaticSlider() {
		if (EVoterShareMemory.userJoinedSession() && EVoterShareMemory.getCurrentUserType() == UserType.STUDENT)
			mainMenu.getBtJoin().setVisibility(View.GONE);
		Log.i("User accepted?", String.valueOf(EVoterShareMemory.userJoinedSession()));
		if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && EVoterShareMemory.userJoinedSession() && EVoterShareMemory.getCurrentSession().isActive()) {
			tbSessionValue.setVisibility(View.VISIBLE);
			sbExcited.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				int progressValue;
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					EVoterRequestManager.doVote(EVoterMobileUtils.getstaticAnswerID(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST), QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					Toast.makeText(QuestionActivity.this, "Vote for exciting level of session", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					progressValue = progress;
				}
			});
			
			sbDifficult.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				int progressValue;
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					EVoterRequestManager.doVote(EVoterMobileUtils.getstaticAnswerID(CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST), QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					Toast.makeText(QuestionActivity.this, "Vote for difficult level of session", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					progressValue = progress;
				}
			});
		} else {
			tbSessionValue.setVisibility(View.GONE);
		}
	}
	
	/**
	 * @param selectQuestion
	 */
	private void longClickQuestionAction() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("Session: " + EVoterShareMemory.getCurrentQuestion().getQuestionText())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton(R.string.edit_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent editQuestion = new Intent(QuestionActivity.this, EditQuestionActivity.class);
						EVoterShareMemory.setPreviousContext(QuestionActivity.this);
						startActivity(editQuestion);
					}
				})
				.setNegativeButton(R.string.delete_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						EVoterRequestManager.deleteQuestion(EVoterShareMemory.getCurrentQuestion().getId(), QuestionActivity.this);
					}
				}).show();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.DELETE_QUESTION_EVOTER_REQUEST)) {
			if (response.equals(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
						"Deleted question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
				adapter.deleteItem(EVoterShareMemory.getCurrentQuestion().getId());
				adapter.notifyDataSetChanged();
			}
			else {
				EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
						"Cannot delete question: " + response);
			}
		}
		else if (callBackMessage.equals(CallBackMessage.CHANGE_SESSION_STATUS_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"Session is change status successfully");
				if (response.contains(EVoterMainMenu.MN_STOP_SESSION)) {
					mainMenu.getBtChangeSessionStatus().setText(EVoterMainMenu.MN_STOP_SESSION);
					EVoterShareMemory.getCurrentSession().setActive(true);
				} else if (response.contains(EVoterMainMenu.MN_START_SESSION)) {
					mainMenu.getBtChangeSessionStatus().setText(EVoterMainMenu.MN_START_SESSION);
					EVoterShareMemory.getCurrentSession().setActive(false);
				}
			} else {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"Request failure: " + response);
			}
		} else if (callBackMessage.equals(CallBackMessage.ACCEPT_SESSION_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"You joined to session");
				mainMenu.getBtJoin().setVisibility(View.GONE);
				EVoterShareMemory.addToListAcceptedSessions(EVoterShareMemory.getCurrentSession().getId());
				buildStaticSlider();
			} else {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this, "Request false: " +
								response);
			}
		}
		else if (callBackMessage.equals(CallBackMessage.SUBMIT_ANSWER_EVOTER_REQUEST)) {
			if (!response.contains(URIRequest.SUCCESS_MESSAGE))
				EVoterMobileUtils.showeVoterToast(this, "Cannot send static value: " + response);
		}
		else if (callBackMessage.equals(CallBackMessage.GET_ALL_QUESTION_EVOTER_REQUEST)) {
			ArrayList<ItemData> listQuestion = EVoterMobileUtils.parserQuestionArray(response);
			if (listQuestion.isEmpty()) {
				EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There isn't any question!");
			}
			else {
				EVoterShareMemory.getListQuestions().clear();
				for(int i=0;i<listQuestion.size();i++){
					EVoterShareMemory.addQuestionToList((Question)listQuestion.get(i));
				}
				buildStaticSlider();
				adapter.updateList(listQuestion);
			}
			
			if (EVoterShareMemory.getExictedQuestion() == null || EVoterShareMemory.getDifficultQuestion() == null) {
				Log.i("STATIC SLIDER", "Cannot set id for static slider bar");
			} else {
				Log.i(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST, String.valueOf(EVoterShareMemory.getExictedQuestion().getId()));
				Log.i(CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST, String.valueOf(EVoterShareMemory.getDifficultQuestion().getId()));
			}
			
		} else if (callBackMessage.equals(CallBackMessage.UPDATE_SESSION_EVOTER_REQUEST)) {
			ArrayList<ItemData> listSessions = EVoterMobileUtils.parserSessionArray(response);
			if (!listSessions.isEmpty()) {
				Session session = (Session) listSessions.get(0);
				Log.i("New session: ", session.toString());
				Log.i("Session before update: ", EVoterShareMemory.getCurrentSession().toString());
				if (session != null) {
					EVoterShareMemory.setCurrentSession(session);
					buildStaticSlider();
				}
				Log.i("Session after update: ", EVoterShareMemory.getCurrentSession().toString());
			}
			
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		EVoterRequestManager.getListQuestion(QuestionActivity.this);
		EVoterRequestManager.updateSession(QuestionActivity.this);
		
	}
	
}