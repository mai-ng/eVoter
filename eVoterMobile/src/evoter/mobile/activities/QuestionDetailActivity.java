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
import android.widget.SeekBar;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
import evoter.share.model.UserType;

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
	
	TextView tvQuestionText;
	LinearLayout answerArea;
	Button btSend;
	Button btView;
	
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
		
		int type = (int) EVoterShareMemory.getCurrentQuestion().getQuestionTypeId();
		
		//Parser the answer of question
		ArrayList<Answer> column1 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn1());
		
		//		type = 1;
		switch (type) {
			case QuestionType.YES_NO:
				RadioGroup groups = new RadioGroup(this);
				groups.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				RadioButton btYes = new RadioButton(this);
				btYes.setText("True");
				btYes.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				RadioButton btNo = new RadioButton(this);
				btNo.setText("False");
				btNo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				groups.addView(btYes);
				groups.addView(btNo);
				answerArea.addView(groups);
				break;
			case QuestionType.MULTI_RADIOBUTTON:
				RadioGroup groupMultiRadioBox = new RadioGroup(this);
				groupMultiRadioBox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				for (int i = 0; i < column1.size(); i++) {
					RadioButton ans = new RadioButton(this);
					ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					ans.setText(column1.get(i).getAnswerText());
					groupMultiRadioBox.addView(ans);
				}
				answerArea.addView(groupMultiRadioBox);
				break;
			case QuestionType.MULTI_CHECKBOX:
				for (int i = 0; i < column1.size(); i++) {
					CheckBox ans = new CheckBox(this);
					ans.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					ans.setText(column1.get(i).getAnswerText());
					answerArea.addView(ans);
				}
				break;
			case QuestionType.SLIDER:
				SeekBar seekbar = new SeekBar(this);
				seekbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				int max = Integer.parseInt(column1.get(0).getAnswerText());
				seekbar.setMax(max);
				seekbar.setProgress(max / 2);
				answerArea.addView(seekbar);
				break;
			case QuestionType.INPUT_ANSWER:
				EditText etAnswer = new EditText(this);
				etAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				etAnswer.setHint("Your answer here");
				answerArea.addView(etAnswer);
				break;
			case QuestionType.MATCH:
				if (!EVoterShareMemory.getCurrentQuestion().getAnswerColumn2().equals("")) {
					ArrayList<Answer> column2 = parserAnswer(EVoterShareMemory.getCurrentQuestion().getAnswerColumn2());
				}
				break;
			default:
				break;
		}
		
		btSend = (Button) findViewById(R.id.btSendQuestion);
		if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
			btSend.setText("Send");
		} else if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT) {
			btSend.setText("Submit");
		}
		
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
