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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.adapters.QuestionAdapter;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.MainMenu;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
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
	private static final String EXCITED = "EXCITED";
	private static final String DIFFICULT = "DIFFICULT";
	long excitedAnswerID = -1;
	long difficultAnswerID = -1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set titlebar of current activity is the name of current session
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSessionName());
		EVoterMobileUtils.updateCurrentSession();
		mainMenu.setQuestionActivityMenu();
		
		adapter = new QuestionAdapter(QuestionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentQuestion(selectQuestion);
				EVoterShareMemory.setPreviousContext(QuestionActivity.this);
				Log.i("Detail of question: ", selectQuestion.getTitle());
				Intent detailQuestion = new Intent(QuestionActivity.this, QuestionDetailActivity.class);
				startActivity(detailQuestion);
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
		mainMenu.getBtNewQuestion().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Create new question");
				mainMenu.dismiss();
				EVoterShareMemory.setPreviousContext(QuestionActivity.this);
				Intent newQuestion = new Intent(QuestionActivity.this, NewQuestionActivity.class);
				startActivity(newQuestion);
				
			}
		});
		
		mainMenu.getBtAcceptUsers().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Request accepted user of a session");
				mainMenu.dismiss();
				Intent acceptedStudents = new Intent(QuestionActivity.this, AcceptedStudents.class);
				startActivity(acceptedStudents);
			}
		});
		
		mainMenu.getBtStartSession().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.STOP_SESSION)) {
					mainMenu.getBtStartSession().setText(MainMenu.START_SESSION);
					startSession(false);
					mainMenu.dismiss();
				} else if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.START_SESSION)) {
					mainMenu.getBtStartSession().setText(MainMenu.STOP_SESSION);
					startSession(true);
					mainMenu.dismiss();
				} else if (mainMenu.getBtStartSession().getText().toString().contains(MainMenu.ACCEPT_SESSION)) {
					acceptSession();
					mainMenu.dismiss();
				}
				
			}
		});
		
		mainMenu.getBtStatistic().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showStudentFeedback();
			}
		});
		
	}
	
	/**
	 * 
	 */
	protected void showStudentFeedback() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	protected void acceptSession() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		client.post(RequestConfig.getURL(URIRequest.ACCEPT_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
							EVoterMobileUtils.showeVoterToast(
									QuestionActivity.this,
									"You joined to session");
							mainMenu.getBtStartSession().setVisibility(View.GONE);
							EVoterShareMemory.addToListAcceptedSessions(EVoterShareMemory.getCurrentSession().getId());
						} else {
							EVoterMobileUtils.showeVoterToast(
									QuestionActivity.this,
									response);
						}
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								QuestionActivity.this,
								"Cannot request to server!");
						Log.e("Accept session", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
	/**
	 * 
	 */
	protected void startSession(final boolean start) {
		if (okChangeStatus(start)) {
			RequestParams params = new RequestParams();
			params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
			params.add(SessionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
			String url = start ? URIRequest.ACTIVE_SESSION : URIRequest.INACTIVE_SESSION;
			client.post(RequestConfig.getURL(url), params,
					new AsyncHttpResponseHandler() {
						// Request successfully - client receive a response
						@Override
						public void onSuccess(String response) {
							if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
								EVoterMobileUtils.showeVoterToast(
										QuestionActivity.this,
										"Session is " + (start ? "running!" : "stop"));
							} else {
								EVoterMobileUtils.showeVoterToast(
										QuestionActivity.this,
										"Request failure");
							}
						}
						
						//Login fail
						@Override
						public void onFailure(Throwable error,
								String content) {
							EVoterMobileUtils.showeVoterToast(
									QuestionActivity.this,
									"Cannot request to server!");
							Log.e("start session", "onFailure error : "
									+ error.toString() + "content : "
									+ content);
						}
					});
		} else {
			EVoterMobileUtils.showeVoterToast(this, "There is some question still waiting for answer, you cannot stop session");
		}
	}
	
	/**
	 * @return
	 */
	private boolean okChangeStatus(boolean start) {
		//		if(start) return true;
		//		else{
		//			refreshActivity();
		//			for(int i=0;i<li)
		return true;
	}
	
	/**
	 * 
	 */
	private void buildStaticSlider() {
		tbSessionValue.setVisibility(View.VISIBLE);
		sbBored.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressValue;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(QuestionActivity.this, "Your excited value: " + progressValue, Toast.LENGTH_SHORT).show();
				EVoterRequestManager.doVote(excitedAnswerID, QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
				
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
				EVoterRequestManager.doVote(difficultAnswerID, QuestionType.SLIDER, String.valueOf(progressValue), QuestionActivity.this);
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
	
	/**
	 * @param idExcitedBar2
	 * @param progressValue
	 */
	protected void submitStatisValue(long idExcitedBar2, final int progressValue) {
		if (idExcitedBar2 != -1) {
			Log.i("Send statitic value", "id: " + idExcitedBar2 + "\t statistic:" + progressValue);
			RequestParams params = new RequestParams();
			params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
			params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(QuestionType.SLIDER));
			params.put(AnswerDAO.ID, String.valueOf(idExcitedBar2));
			params.put(AnswerDAO.STATISTICS, String.valueOf(progressValue));
			client.post(RequestConfig.getURL(URIRequest.VOTE_ANSWER), params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					Log.i("Static response: ", response);
					if (response.contains("SUCCESS")) {
						EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
								"Sent static value: " + progressValue);
					}
					else {
						EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
								"Cannot send evaluate value: " + response);
					}
				}
				
				@Override
				public void onFailure(Throwable error, String content)
				{
					EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
							"FAILURE: " + error.toString());
					Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
				}
			});
		} else {
			EVoterMobileUtils.showeVoterToast(QuestionActivity.this, "Cannot get the id of static slider bar!");
		}
	}
	
	public void refreshActivity() {
		EVoterMobileUtils.updateCurrentSession();
		if (userAcceptSession()) mainMenu.getBtStartSession().setVisibility(View.GONE);
		if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && EVoterShareMemory.currentSessionIsActive()) {
			//Setup seekbar
			buildStaticSlider();
		}
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Get All Quesion Test", "response : " + response);
						try {
							ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
							JSONArray array = EVoterMobileUtils
									.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								
								Question question = EVoterMobileUtils.parserToQuestion(array.getJSONObject(i));
								if (question != null) {
									if (excitedAnswerID == -1 || difficultAnswerID == -1) {
										setStaticAnswerID(question);
									}
									if (!question.getTitle().contains(EXCITED) && !question.getTitle().contains(DIFFICULT)) {
										//With student, only load the question which already sent or finished.
										if (!(EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && question.getStatus() == 0))
											listQuestion.add(question);
									}
								}
							}
							if (listQuestion.isEmpty()) {
								EVoterMobileUtils.showeVoterToast(
										QuestionActivity.this,
										"There isn't any question!");
							}
							
							if (excitedAnswerID == -1 || difficultAnswerID == -1) {
								Log.i("STATIC SLIDER", "Cannot set id for static slider bar");
							} else {
								Log.i(EXCITED, String.valueOf(excitedAnswerID));
								Log.i(DIFFICULT, String.valueOf(difficultAnswerID));
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
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
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
	
	/**
	 * 
	 */
	private void deleteQuestionRequest() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		client.post(RequestConfig.getURL(URIRequest.DELETE_QUESTION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
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
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
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
		ArrayList<Answer> listAnswers = EVoterMobileUtils.parserAnswer(question.getAnswerColumn1());
		if (question.getTitle().contains(EXCITED)) {
			excitedAnswerID = listAnswers.get(0).getId();
		}
		if (question.getTitle().contains(DIFFICULT)) {
			difficultAnswerID = listAnswers.get(0).getId();
		}
	}
	
}