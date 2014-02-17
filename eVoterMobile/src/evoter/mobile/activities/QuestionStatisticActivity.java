/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 */
public class QuestionStatisticActivity extends EVoterActivity {
	TextView tvStatistic;
	
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
		tvStatistic = (TextView) findViewById(R.id.tvStatistic);
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshActivity();
			}
		});
		ivTitleBarRefresh.setVisibility(View.VISIBLE);
		refreshActivity();
	}
	
	/**
	 * 
	 */
	public void refreshActivity() {
		RequestParams params = new RequestParams();
		params.add(QuestionDAO.ID,
				String.valueOf(EVoterShareMemory.getCurrentQuestion().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(RequestConfig.getURL(URIRequest.GET_STATISTICS), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						Log.i("Get Statistic of question", response);
						drawStatistic(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get question statistic", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
	}
	
	/**
	 * @param response
	 */
	protected void drawStatistic(String response) {
		tvStatistic.setText(response);
		
	}
	
}
