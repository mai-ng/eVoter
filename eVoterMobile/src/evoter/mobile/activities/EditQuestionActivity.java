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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.QuestionType;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 */
public class EditQuestionActivity extends NewQuestionActivity {
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.NewQuestionActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		etQuestionText.setText(EVoterShareMemory.getCurrentQuestion().getQuestionText());
		int type = (int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId();
		//Parser the answer of question
		ArrayList<Answer> column1 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1());
		spQuestionType.setSelection(type-1);
		spQuestionType.setEnabled(false);
		//		type = 1;
		switch (type) {
			
			case QuestionType.MULTI_RADIOBUTTON:
				for (int i = 0; i < column1.size(); i++) {
					listAnswser.add(column1.get(i).getAnswerText());
				}
				adaterListView.notifyDataSetChanged();
				break;
			case QuestionType.MULTI_CHECKBOX:
				for (int i = 0; i < column1.size(); i++) {
					listAnswser.add(column1.get(i).getAnswerText());
				}
				adaterListView.notifyDataSetChanged();
				break;
			case QuestionType.SLIDER:
				etMax.setText(column1.get(0).getAnswerText());
				break;
			
			case QuestionType.MATCH:
				EVoterMobileUtils.showeVoterToast(EditQuestionActivity.this, "Not implemented yet!");
				break;
			default:
				break;
		}
		
		setBtSaveAction();
	}
	
	/**
	 * 
	 */
	@Override
	protected void setBtSaveAction() {
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateQuestionRequest();
				
			}
		});
		
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

	/**
	 * 
	 */
	private void updateQuestionRequest() {
		if (!readyToCreate()) {
			EVoterMobileUtils.showeVoterToast(EditQuestionActivity.this, "Invalid parameter. Please input again!");
		} else {
			
//			parameters.put(QuestionDAO.ID, questionId);
			
			RequestParams params = new RequestParams();
			params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
			params.put(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
			params.put(QuestionDAO.QUESTION_TEXT, etQuestionText.getText().toString());
			params.put(AnswerDAO.ANSWER_TEXT, listAnswser);
			client.post(RequestConfig.getURL(URIRequest.UPDATE_QUESTION), params,
					new AsyncHttpResponseHandler() {
						// Request successfully - client receive a response
						@Override
						public void onSuccess(String response) {
							Log.i("Response", response);
							if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
								EVoterMobileUtils.showeVoterToast(
										EditQuestionActivity.this,
										"Updated successfully!");
								EVoterShareMemory.getPreviousContext().loadListItemData();
							} else {
								EVoterMobileUtils.showeVoterToast(
										EditQuestionActivity.this,
										"Cannot update question");
							}
							finish();
							
						}
						
						//Login fail
						@Override
						public void onFailure(Throwable error,
								String content) {
							EVoterMobileUtils.showeVoterToast(
									EditQuestionActivity.this,
									"Cannot request to server!");
							Log.e("edit question", "onFailure error : "
									+ error.toString() + "content : "
									+ content);
							finish();
						}
					});
		}
	}
	
}
