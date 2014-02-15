/**
 * 
 */
package evoter.mobile.activities;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import android.os.Bundle;

/**
 * @author luongnv89
 */
public class QuestionStatisticActivity extends EVoterActivity {
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_statistic);
		mainMenu.setQuestionActivityMenu();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentQuestion().getTitle());
	}
	
}
