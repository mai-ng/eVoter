/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 */
public class QuestionStatisticActivity extends EVoterActivity {
	LinearLayout layout;
	
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
		layout = (LinearLayout) findViewById(R.id.layout);
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshData();
			}
		});
		ivTitleBarRefresh.setVisibility(View.VISIBLE);
		refreshData();
	}
	
	/**
	 * 
	 */
	public void refreshData() {
		EVoterRequestManager.updateCurrentQuestion();
		EVoterMobileUtils.drawStatistic(EVoterShareMemory.getCurrentQuestion(), layout, QuestionStatisticActivity.this);
	}
	
}
