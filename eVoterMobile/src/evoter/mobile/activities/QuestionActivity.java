package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

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
import evoter.mobile.adapters.QuestionAdapter;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.MainMenu;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.model.Answer;
import evoter.share.model.ItemData;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
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
		//Set titlebar of current activity is the name of current session
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSessionName());
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EVoterRequestManager.updateSession(EVoterShareMemory.getCurrentSession());
				refreshData();
			}
		});
		EVoterRequestManager.updateSession(EVoterShareMemory.getCurrentSession());
		mainMenu.setQuestionActivityMenu();
		
		adapter = new QuestionAdapter(QuestionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && !userAcceptSession()) {
					EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "You have to accept joining session before go to detail question!");
				} else {
					Question selectQuestion = (Question) parent
							.getItemAtPosition(position);
					EVoterShareMemory.setCurrentQuestion(selectQuestion);
					EVoterShareMemory.setPreviousContext(QuestionActivity.this);
					Intent detailQuestion = new Intent(QuestionActivity.this, QuestionDetailActivity.class);
					startActivity(detailQuestion);
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
		setupMainMenuAction();
		
	}
	
	/**
	 * 
	 */
	private void setupMainMenuAction() {
		
		mainMenu.getBtStartSession().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.STOP_SESSION)) {
					mainMenu.getBtStartSession().setText(MainMenu.START_SESSION);
					if (okChangeStatus(false)) {
						EVoterRequestManager.changeSessionStatus(false, EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
					} else {
						EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There is some question still waiting for answer, you cannot stop session");
					}
					mainMenu.dismiss();
				} else if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.START_SESSION)) {
					mainMenu.getBtStartSession().setText(MainMenu.STOP_SESSION);
					if (okChangeStatus(true)) {
						EVoterRequestManager.changeSessionStatus(true, EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
					} else {
						EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There is some question still waiting for answer, you cannot stop session");
					}
					mainMenu.dismiss();
				} else if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.ACCEPT_SESSION)) {
					EVoterRequestManager.acceptSession(EVoterShareMemory.getCurrentSession().getId(), QuestionActivity.this);
					mainMenu.dismiss();
				}
				
			}
		});
		
	}
	
	/**
	 * @param start
	 * @return
	 */
	private boolean okChangeStatus(boolean start) {
		if (start)
			return true;
		else {
			for (int i = 0; i < EVoterShareMemory.getListQuestions().size(); i++) {
				if (EVoterShareMemory.getListQuestions().get(i).getStatus() == 1) {
					EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "There is some question which is waiting for answer!");
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 
	 */
	private void buildStaticSlider() {
		if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && userAcceptSession() && EVoterShareMemory.getCurrentSession().isActive()) {
			tbSessionValue.setVisibility(View.VISIBLE);
			sbBored.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				int progressValue;
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					Toast.makeText(QuestionActivity.this, "Your excited value: " + progressValue, Toast.LENGTH_SHORT).show();
					EVoterRequestManager.doVote(getstaticAnswerID(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC), QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					Toast.makeText(QuestionActivity.this, "Evaluate the exciting of session. Max value: " + 10, Toast.LENGTH_SHORT).show();
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
					Toast.makeText(QuestionActivity.this, "Your difficult level value: " + progressValue, Toast.LENGTH_SHORT).show();
					EVoterRequestManager.doVote(getstaticAnswerID(CallBackMessage.EVOTER_REQUEST_DIFFICULT_BAR_STATISTIC), QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					Toast.makeText(QuestionActivity.this, "Evaluate the difficult level of session. Max value: " + 10, Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					progressValue = progress;
				}
			});
		}
	}
	
	/**
	 * @param difficult2
	 * @return
	 */
	protected long getstaticAnswerID(String difficult2) {
		if (difficult2.equals(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC))
			return getFirstAnswerID(EVoterShareMemory.getExictedQuestion());
		else
			return getFirstAnswerID(EVoterShareMemory.getDifficultQuestion());
	}
	
	/**
	 * @param exictedQuestion
	 * @return
	 */
	private long getFirstAnswerID(Question exictedQuestion) {
		ArrayList<Answer> listAnswers = EVoterMobileUtils.parserListAnswer(exictedQuestion.getAnswerColumn1(), exictedQuestion.getId());
		return listAnswers.get(0).getId();
	}
	
	/**
	 * @param idExcitedBar2
	 * @param progressValue
	 */
	protected void submitStatisValue(long idExcitedBar2, final int progressValue) {
		if (idExcitedBar2 != -1) {
			Log.i("Send statitic value", "id: " + idExcitedBar2 + "\t statistic:" + progressValue);
			EVoterRequestManager.doVote(idExcitedBar2, QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
		} else {
			EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "Cannot get the id of static slider bar!");
		}
	}
	
	public void refreshData() {
		if (userAcceptSession()) mainMenu.getBtStartSession().setVisibility(View.GONE);
		buildStaticSlider();
		EVoterRequestManager.getListQuestion(this);
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
						deleteQuestionRequest();
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
	public void updateRequestCallBack(String response) {
		if (response.contains(CallBackMessage.EVOTER_REQUEST_DELETE_QUESTION)) {
			if (response.contains("SUCCESS")) {
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
		else if (response.contains(CallBackMessage.EVOTER_REQUEST_CHANGE_SESSION_STATUS)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"Session is change status successfully");
				if (EVoterShareMemory.getCurrentSession().isActive())
					EVoterShareMemory.getCurrentSession().setActive(false);
				else
					EVoterShareMemory.getCurrentSession().setActive(true);
			} else {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"Request failure" + response);
			}
		} else if (response.contains(CallBackMessage.EVOTER_REQUEST_ACCEPT_SESSION)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						"You joined to session");
				mainMenu.getBtStartSession().setVisibility(View.GONE);
				if (EVoterShareMemory.getCurrentSession().isActive()) buildStaticSlider();
				EVoterShareMemory.addToListAcceptedSessions(EVoterShareMemory.getCurrentSession().getId());
			} else {
				EVoterMobileUtils.showeVoterToast(
						QuestionActivity.this,
						response);
			}
		}
		else if (response.contains(CallBackMessage.EVOTER_REQUEST_SUBMIT_ANSWER)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE))
				EVoterMobileUtils.showeVoterToast(this, response);
			else {
				EVoterMobileUtils.showeVoterToast(this, "Cannot send static value: " + response);
			}
		}
		else {
			try {
				EVoterShareMemory.getListQuestions().clear();
				ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
				JSONArray array = EVoterMobileUtils
						.getJSONArray(response);
				for (int i = 0; i < array.length(); i++) {
					
					Question question = EVoterMobileUtils.parserToQuestion(array.getJSONObject(i));
					if (question != null) {
						if (question.getTitle().equals(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC) || question.getTitle().equals(CallBackMessage.EVOTER_REQUEST_DIFFICULT_BAR_STATISTIC)) {
							setStaticAnswerID(question);
						}
						if (!question.getTitle().contains(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC) && !question.getTitle().contains(CallBackMessage.EVOTER_REQUEST_DIFFICULT_BAR_STATISTIC)) {
							//With student, only load the question which already sent or finished.
							if (!(EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && question.getStatus() == 0))
								listQuestion.add(question);
							EVoterShareMemory.addQuestionToList(question);
						}
					}
				}
				if (listQuestion.isEmpty()) {
					EVoterMobileUtils.showeVoterToast(
							QuestionActivity.this,
							"There isn't any question!");
				}
				
				if (EVoterShareMemory.getExictedQuestion() == null || EVoterShareMemory.getDifficultQuestion() == null) {
					Log.i("STATIC SLIDER", "Cannot set id for static slider bar");
				} else {
					Log.i(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC, String.valueOf(EVoterShareMemory.getExictedQuestion().getId()));
					Log.i(CallBackMessage.EVOTER_REQUEST_DIFFICULT_BAR_STATISTIC, String.valueOf(EVoterShareMemory.getDifficultQuestion().getId()));
				}
				if (!EVoterShareMemory.hasStaticBar()) {
					tbSessionValue.setVisibility(View.GONE);
				}
				adapter.updateList(listQuestion);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 */
	private void deleteQuestionRequest() {
		EVoterRequestManager.deleteQuestion(EVoterShareMemory.getCurrentQuestion().getId(), this);
	}
	
	/**
	 * @return true if current student already accepted current session
	 */
	protected boolean userAcceptSession() {
		// TODO Auto-generated method stub
		return EVoterShareMemory.getListAcceptedSessions().contains(EVoterShareMemory.getCurrentSession().getId());
	}
	
	/**
	 * @param question
	 */
	private void setStaticAnswerID(Question question) {
		if (question.getTitle().contains(CallBackMessage.EVOTER_REQUEST_EXCITED_BAR_STATISTIC)) {
			if (EVoterShareMemory.getExictedQuestion() == null || question.getId() != EVoterShareMemory.getExictedQuestion().getId())
				EVoterShareMemory.setExictedQuestion(question);
		}
		if (question.getTitle().contains(CallBackMessage.EVOTER_REQUEST_DIFFICULT_BAR_STATISTIC)) {
			if (EVoterShareMemory.getDifficultQuestion() == null || question.getId() != EVoterShareMemory.getDifficultQuestion().getId())
				EVoterShareMemory.setDifficultQuestion(question);
		}
	}
	
}