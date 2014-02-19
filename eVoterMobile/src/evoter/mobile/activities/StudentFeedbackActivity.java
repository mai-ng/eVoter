/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;

/**
 * @author luongnv89
 */
public class StudentFeedbackActivity extends EVoterActivity {
	
	LinearLayout excitedLayout;
	LinearLayout difficultLayout;
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_feeback);
		excitedLayout = (LinearLayout) findViewById(R.id.la_excitedStatistic);
		difficultLayout = (LinearLayout) findViewById(R.id.la_difficultStatistic);
	}
	
	
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupContentMainMenu()
	 */
	@Override
	protected void setupContentMainMenu() {
		// TODO Auto-generated method stub
		super.setupContentMainMenu();
		setQuestionActivityMenu();
		mainMenu.getBtViewFeedback().setVisibility(View.GONE);
	}



	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText("STUDENT FEEDBACK");
	}


	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		EVoterRequestManager.updateStaticValue(StudentFeedbackActivity.this, CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST);
		EVoterRequestManager.updateStaticValue(StudentFeedbackActivity.this, CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.EXCITED_BAR_STATISTIC_EVOTER_REQUEST)) {
			excitedLayout.removeAllViews();
			ArrayList<String> textToView = EVoterMobileUtils.drawStatistic(response, EVoterShareMemory.getExictedQuestion());
			for (int i = 0; i < textToView.size(); i++) {
				TextView tvShow = new TextView(this);
				tvShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tvShow.setText(textToView.get(i));
				excitedLayout.addView(tvShow);
			}
		} else if (callBackMessage.equals(CallBackMessage.DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST)) {
			difficultLayout.removeAllViews();
			ArrayList<String> textToView = EVoterMobileUtils.drawStatistic(response, EVoterShareMemory.getDifficultQuestion());
			for (int i = 0; i < textToView.size(); i++) {
				TextView tvShow = new TextView(this);
				tvShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tvShow.setText(textToView.get(i));
				difficultLayout.addView(tvShow);
			}
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
}
