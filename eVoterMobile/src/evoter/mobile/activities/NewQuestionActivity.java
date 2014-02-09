/**
 * 
 */
package evoter.mobile.activities;

import evoter.mobile.main.R;
import android.os.Bundle;
import android.view.View;

/**
 * @author luongnv89
 *
 */
public class NewQuestionActivity extends EVoterActivity {

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_question);
		mainMenu.setQuestionActivityMenu();
		mainMenu.getBtNewQuestion().setVisibility(View.GONE);
	}
	
}
