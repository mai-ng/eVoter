/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.loopj.android.http.RequestParams;

import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
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
		super.onCreate(savedInstanceState);
		etQuestionText.setText(EVoterShareMemory.getCurrentQuestion().getQuestionText());
		int type = (int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId();
		spQuestionType.setSelection(type - 1);
		spQuestionType.setEnabled(false);
		buildAnswerArea(type);
		setActionListeners();
	}
	
	/**
	 * @param type
	 * @param column1
	 */
	private void buildAnswerArea(int type) {
		ArrayList<Answer> column1 = EVoterMobileUtils.parserAnswerArray(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1(), EVoterShareMemory.getCurrentQuestion().getId());
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
	}
	
	/**
	 * 
	 */
	protected void setActionListeners() {
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!readyToCreate()) {
					EVoterMobileUtils.showeVoterToast(EditQuestionActivity.this, "Invalid parameter. Please input again!");
				} else {
					RequestParams params = new RequestParams();
					params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
					params.put(QuestionDAO.ID, String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
					params.put(QuestionDAO.QUESTION_TEXT, etQuestionText.getText().toString());
					params.put(AnswerDAO.ANSWER_TEXT, listAnswser);
					EVoterRequestManager.editQuestion(params, EditQuestionActivity.this);
					
				}
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.NewQuestionActivity#updateRequestCallBack(java
	 * .lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.EDIT_QUESTION_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(
						EditQuestionActivity.this,
						"Updated successfully!");
				finish();
			} else {
				EVoterMobileUtils.showeVoterToast(
						EditQuestionActivity.this,
						"Cannot update question");
			}
			finish();
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
}
