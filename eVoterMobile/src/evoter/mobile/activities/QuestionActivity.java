package evoter.mobile.activities;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.ItemData;
import evoter.share.model.Question;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * <br>Updated by @author luongnv89 on 13-Feb-2014 <br>
 * <li>change behaviour for click question event -> always show the detail of question
 * <br>Updated by @author luongnv89 on 18-Jan-2014 <br>
 * <li>Add 2 seekbar for difficult and bored value of session - only add when
 * usertype is student and session is active {@link QuestionActivity} extend
 * from {@link ItemDataActivity} manages questions in a session. Created by
 * luongnv89 on 06/12/13.
 */
public class QuestionActivity extends ItemDataActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set titlebar of current activity is the name of current session
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSessionName());
		
		mainMenu.setQuestionActivityMenu();
		if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT && EVoterShareMemory.currentSessionIsActive()) {
			//Setup seekbar
			buildStaticSlider();
		}
		
		adapter = new QuestionAdapter(QuestionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentQuestion(selectQuestion);
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
				Toast.makeText(QuestionActivity.this, String.valueOf(progressValue), Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Toast.makeText(QuestionActivity.this, "Slide the seekbar to the value of Bored!", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressValue = progress;
			}
		});
		
		sbDifficult.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int value;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Toast.makeText(QuestionActivity.this, String.valueOf(value), Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Toast.makeText(QuestionActivity.this, "Slide the seekbar to the value of Bored!", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				
				value = progress;
			}
		});
	}
	
	protected void loadListItemData() {
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_QUESTION), params,
				new AsyncHttpResponseHandler() {
					
					/*
					 * (non-Javadoc)
					 * @see
					 * com.loopj.android.http.AsyncHttpResponseHandler#onStart()
					 */
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						tvLoadingStatus.setText("Loading...");
						dialogLoading.show();
					}
					
					/*
					 * (non-Javadoc)
					 * @see
					 * com.loopj.android.http.AsyncHttpResponseHandler#onFinish
					 * ()
					 */
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						tvLoadingStatus.setText("Finished");
						dialogLoading.dismiss();
					}
					
					@Override
					public void onSuccess(String response) {
						Log.i("Get All Quesion Test", "response : " + response);
						try {
							ArrayList<ItemData> listQuestion = new ArrayList<ItemData>();
							JSONArray array = EVoterMobileUtils
									.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								internetProcessBar.setProgress((i + 1) * 100
										/ array.length());
								tvLoadingStatus.setText("Loading..." + (i + 1)
										* 100 / array.length());
								String sString = array.get(i).toString();
								JSONObject s = new JSONObject(sString);
								String answerColumn1 = "null";
								String answerColumn2 = "null";
								if (s.toString().contains(Question.COL1)) {
									answerColumn1 = s
											.getString(Question.COL1);
								}
								if (s.toString().contains(Question.COL2)) {
									answerColumn2 = s
											.getString(Question.COL2);
								}
								Question question = new Question(
										Long.parseLong(s
												.getString(QuestionDAO.ID)),
										s.getString(QuestionDAO.QUESTION_TEXT),
										Long.parseLong(s
												.getString(QuestionDAO.QUESTION_TYPE_ID)),
										Long.parseLong(s
												.getString(QuestionDAO.USER_ID)),
										EVoterMobileUtils.convertToDate(s
												.getString(QuestionDAO.CREATION_DATE)),
										Long.parseLong(s
												.getString(QuestionSessionDAO.SESSION_ID)),
										Long.parseLong(s
												.getString(QuestionDAO.PARENT_ID)),
										answerColumn1,answerColumn2);
								listQuestion.add(question);
							}
							if (listQuestion.isEmpty()) {
								EVoterMobileUtils.showeVoterToast(
										QuestionActivity.this,
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
						catch (ParseException e) {
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
		final DialogInfor dialog = new DialogInfor(
				QuestionActivity.this, "Question");
		dialog.setMessageContent(EVoterShareMemory.getCurrentQuestion().getTitle());
		dialog.getBtOK().setText("Edit");
		dialog.getBtOK().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("QUESTION LONG ITEM CLICK", "Edit question" + EVoterShareMemory.getCurrentQuestion().getTitle());
				dialog.dismiss();
				Intent editQuestion = new Intent(QuestionActivity.this, EditQuestionActivity.class);
				startActivity(editQuestion);
			}
		});
		
		dialog.getBtKO().setText("Delete");
		dialog.getBtKO().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteQuestionRequest();
				dialog.dismiss();
			}
		});
		if(questionHasStatistic()) dialog.getBtOK().setEnabled(false);
		dialog.show();
	}

	/**
	 * @return
	 */
	private boolean questionHasStatistic() {
		return true;
//		RequestParams params = new RequestParams();
//		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
//		params.add(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
//		params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
//		params.put(AnswerDAO.ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
//		client.post(RequestConfig.getURL(URIRequest.DELETE_QUESTION), params, new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(String response) {
//				if (response.contains("SUCCESS")) {
//					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
//							"Sent question: " + EVoterShareMemory.getCurrentQuestion().getTitle());
//					btSend.setEnabled(false);
//				}
//				else {
//					EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
//							"Cannot send question: " + response);
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable error, String content)
//			{
//				EVoterMobileUtils.showeVoterToast(QuestionDetailActivity.this,
//						"FAILURE: " + error.toString());
//				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
//			}
//		});
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
	
}