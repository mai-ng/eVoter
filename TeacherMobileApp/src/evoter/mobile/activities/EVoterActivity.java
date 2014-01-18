/**
 * 
 */
package evoter.mobile.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.OfflineEVoterManager;

/**
 * On Sat - 18/01/2014 - modified by luongnv89:
 * <br> Add {@link EVoterActivity#exit()} - to exit application from anywhere when the application has error, exception,... avoid stuck phone
 * {@link EVoterActivity} is a parent class of all activity of eVoterMobile application
 * @author luongnv89
 */
public class EVoterActivity extends Activity {
	/**
	 * Image which is the title bar icon (on the left-top) of eVoterMobile. The same for all activities.
	 * When user touch to this icon, the main menu of eVoterMobile will be showed.
	 */
	protected ImageView ivTitleBarIcon;
	/**
	 * Which is present on the title bar of eVoterMobile. Different for each activities.
	 * <br> Show the username in {@link SubjectActivity}
	 * <br> Show the current subject name in {@link SessionActivity}
	 * <br> Show the current session name in {@link QuestionActivity} and {@link AnswerActivity}
	 */
	protected TextView tvTitleBarContent;
	/**
	 * Image which is showed on the right-top of screen. When the user touch to this icon, the activity will reload the data for current activity
	 */
	protected ImageView ivTitleBarRefresh;
	
	
	/**
	 * The dialog show the information of loading data process
	 */
	protected Dialog dialogLoading;
	/**
	 * The progressbar show the progress of data loading process 
	 */
	protected ProgressBar progressBar;
	/**
	 * manage the offline data of eVoterMobile
	 */
	protected OfflineEVoterManager offlineEVoterManager;
	
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.start);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.evoter_title_bar);
		ivTitleBarIcon = (ImageView) findViewById(R.id.ivIconTitleBar);
		ivTitleBarRefresh = (ImageView) findViewById(R.id.ivRefreshTitleBar);
		tvTitleBarContent = (TextView) findViewById(R.id.tvTitleBar);
		ivTitleBarRefresh.setVisibility(View.GONE);
	}
	
	/**
	 * This method to exit application from anywhere in application
	 */
	public void exit(){
		Intent exitIntent = new Intent(this, StartActivity.class);
		exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		exitIntent.putExtra("Exit application", true);
		startActivity(exitIntent);
		finish();
	}
	
}
