/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;

/**
 * @author luongnv89
 *
 */
public class StudentFeedbackActivity extends EVoterActivity{
	
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
		mainMenu.setQuestionActivityMenu();
		tvTitleBarContent.setText("STUDENT FEEDBACK");
		excitedLayout = (LinearLayout) findViewById(R.id.la_excitedStatistic);
		difficultLayout = (LinearLayout)findViewById(R.id.la_difficultStatistic);
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EVoterRequestManager.updateQuestion(EVoterShareMemory.getExictedQuestion());
				EVoterRequestManager.updateQuestion(EVoterShareMemory.getDifficultQuestion());
				drawStatistic();
			}
		});
		ivTitleBarRefresh.setVisibility(View.VISIBLE);
		drawStatistic();
	}
	/**
	 * 
	 */
	protected void drawStatistic() {
		EVoterRequestManager.updateStaticValue(this,CallBackMessage.EXCITED);
		EVoterRequestManager.updateStaticValue(this,CallBackMessage.DIFFICULT);
		
	}
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response) {
		if(response.contains(CallBackMessage.EXCITED)){
			response.replace(CallBackMessage.EXCITED, "");
			excitedLayout.removeAllViews();
			ArrayList<String> textToView = EVoterMobileUtils.drawStatistic(response,EVoterShareMemory.getExictedQuestion());
			for(int i=0;i<textToView.size();i++){
				TextView tvShow = new TextView(this);
				tvShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tvShow.setText(textToView.get(i));
				excitedLayout.addView(tvShow);
			}
		}else if(response.contains(CallBackMessage.DIFFICULT)){
			response.replace(CallBackMessage.DIFFICULT, "");
			difficultLayout.removeAllViews();
			ArrayList<String> textToView = EVoterMobileUtils.drawStatistic(response,EVoterShareMemory.getDifficultQuestion());
			for(int i=0;i<textToView.size();i++){
				TextView tvShow = new TextView(this);
				tvShow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				tvShow.setText(textToView.get(i));
				difficultLayout.addView(tvShow);
			}
		}
	}
}
