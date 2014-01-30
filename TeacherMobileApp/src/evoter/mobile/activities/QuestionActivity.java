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
import evoter.mobile.objects.Configuration;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.RuntimeEVoterManager;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.ItemData;
import evoter.share.model.Question;
import evoter.share.model.UserType;

/**
 * Updated by @author luongnv89 on 18-Jan-2014 <br>
 * <li>Add 2 seekbar for difficult and bored value of session - only add when
 * usertype is student and session is active {@link QuestionActivity} extend
 * from {@link ItemDataActivity} manages questions in a session. Created by
 * luongnv89 on 06/12/13.
 */
public class QuestionActivity extends ItemDataActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set titlebar of current activity is the name of current session
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSessionName());
		
		menuDialog.setMenuSessionActivity();
		if (RuntimeEVoterManager.getCurrentUserType() == UserType.STUDENT && RuntimeEVoterManager.currentSessionIsActive()) {
			//Setup seekbar
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
		
		adapter = new QuestionAdapter(QuestionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Question selectQuestion = (Question) parent
						.getItemAtPosition(position);
				RuntimeEVoterManager.setCurrentQuestion(selectQuestion);
				//TODO: REQUEST GET STATISTIC OF QUESTION. IF THE QUESTION HASNOT STATISTIC YET, AND CURRENT SESSION IS RUNNING, SHOW QUESTIONDETAIL
				if (RuntimeEVoterManager.currentSessionIsActive()) {
					Intent detailQuestion = new Intent(QuestionActivity.this, QuestionDetailActivity.class);
					startActivity(detailQuestion);
				} else {
					//TODO: SHOW STATISTIC OF QUESTION
				}
			}
		});
		
		if (RuntimeEVoterManager.getCurrentUserType() == UserType.TEACHER) {
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					final Question selectQuestion = (Question) parent
							.getItemAtPosition(position);
					final DialogInfor dialog = new DialogInfor(
							QuestionActivity.this, "Question");
					dialog.setMessageContent(selectQuestion.getTitle());
					dialog.getBtOK().setText("Edit");
					dialog.getBtOK().setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//TODO: START EDIT SESSION
							Log.i("QUESTION LONG ITEM CLICK", "Edit question" + selectQuestion.getTitle());
							dialog.dismiss();
						}
					});
					
					dialog.getBtKO().setText("Delete");
					dialog.getBtKO().setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							RequestParams params = new RequestParams();
							params.add(UserDAO.USER_KEY, RuntimeEVoterManager.getUSER_KEY());
							params.add(SessionUserDAO.SESSION_ID, String.valueOf(selectQuestion.getId()));
							client.post(Configuration.get_urlDeleteSession(), params, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String response) {
									if (response.contains("SUCCESS")) {
										EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
												"Deleted question: " + selectQuestion.getTitle());
										adapter.deleteItem(selectQuestion.getId());
										adapter.notifyDataSetChanged();
									}
									else {
										EVoterMobileUtils.showeVoterToast(QuestionActivity.this,
												"Cannot delete session: " + response);
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
							dialog.dismiss();
						}
					});
					dialog.show();
					return true;
				}
			});
		}
		
	}
	
	protected void loadListItemData() {
		RequestParams params = new RequestParams();
		params.add(QuestionSessionDAO.SESSION_ID,
				String.valueOf(RuntimeEVoterManager.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, RuntimeEVoterManager.getUSER_KEY());
		
		client.post(Configuration.get_urlGetAllQuestion(), params,
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
								progressBar.setProgress((i + 1) * 100
										/ array.length());
								tvLoadingStatus.setText("Loading..." + (i + 1)
										* 100 / array.length());
								String sString = array.get(i).toString();
								JSONObject s = new JSONObject(sString);
								// long id, String questionText, long
								// questionTypeId,
								// long userId, Date creationDate, long
								// sessionID, long parentId,
								// String answerColumn1, String answerColumn2
								String answerColumn2 = "null";
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
										s.getString(Question.COL1), answerColumn2);
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
	
}