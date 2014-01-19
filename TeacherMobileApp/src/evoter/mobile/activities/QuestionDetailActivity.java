/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.RuntimeEVoterManager;
import evoter.share.model.Question;
import evoter.share.model.QuestionType;
import evoter.share.model.UserType;

/**
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
	Question currentQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view_detail);
		this.tvTitleBarContent.setText(RuntimeEVoterManager.getCurrentSessionName());
		this.ivTitleBarRefresh.setVisibility(View.GONE);
		this.menuDialog.enableQuestionDetailActivityMenu();
		
		currentQuestion = RuntimeEVoterManager.getCurrentQuestion();
		
		tvQuestionText = (TextView) findViewById(R.id.tvQuestionText);
		tvQuestionText.setText(currentQuestion.getQuestionText());
		
		answerArea = (LinearLayout) findViewById(R.id.loAnswerArea);
		
		int type = (int) currentQuestion.getQuestionTypeId();
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
				
				break;
			case QuestionType.MULTI_CHECKBOX:
				
				break;
			case QuestionType.SLIDER:
				SeekBar seekbar = new SeekBar(this);
				seekbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				seekbar.setMax(10);
				seekbar.setProgress(5);
				answerArea.addView(seekbar);
				break;
			case QuestionType.INPUT_ANSWER:
				EditText etAnswer = new EditText(this);
				etAnswer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				etAnswer.setHint("Your answer here");
				answerArea.addView(etAnswer);
				break;
			case QuestionType.MATCH:
				
				break;
			default:
				break;
		}
		
		btSend = (Button) findViewById(R.id.btSendQuestion);
		if (RuntimeEVoterManager.getCurrentUserType() == UserType.TEACHER) {
			btSend.setText("Send");
		} else if (RuntimeEVoterManager.getCurrentUserType() == UserType.STUDENT) {
			btSend.setText("Submit");
		}
		
	}
	
}
