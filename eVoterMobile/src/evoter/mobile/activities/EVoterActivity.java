/**
 * 
 */
package evoter.mobile.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

import evoter.mobile.main.R;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.MainMenu;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.utils.EVoterMobileUtils;


/**Update by @author luongnv89 on 09-Feb-2014: <br>
 * <li> Change the name of {@link MainMenu} variable to mainMenu
 * <br>
 * Update by @author luongnv89 on Thu 30-Jan-2014: <br>
 * <li>Add constructor for {@link OfflineEVoterManager} <br>On Sat - 18/01/2014 -
 * modified by luongnv89: <br>
 * Add {@link EVoterActivity#exit()} - to exit application from anywhere when
 * the application has error, exception,... avoid stuck phone
 * {@link EVoterActivity} is a parent class of all activity of eVoterMobile
 * application
 * 
 * <br> @author luongnv89
 */
public class EVoterActivity extends Activity {
	/**
	 * Image which is the title bar icon (on the left-top) of eVoterMobile. The
	 * same for all activities.
	 * When user touch to this icon, the main menu of eVoterMobile will be
	 * showed.
	 */
	protected ImageView ivTitleBarIcon;
	/**
	 * Which is present on the title bar of eVoterMobile. Different for each
	 * activities. <br>
	 * Show the username in {@link SubjectActivity} <br>
	 * Show the current subject name in {@link SessionActivity} <br>
	 * Show the current session name in {@link QuestionActivity} and
	 * {@link AnswerActivity}
	 */
	protected TextView tvTitleBarContent;
	/**
	 * Image which is showed on the right-top of screen. When the user touch to
	 * this icon, the activity will reload the data for current activity
	 */
	protected ImageView ivTitleBarRefresh;
	
	/**
	 * The dialog show the information of loading data process
	 */
	protected Dialog dialogLoading;
	/**
	 * The status of loading data process: loading,...finished,...
	 */
	protected TextView tvLoadingStatus;
	/**
	 * The progressbar show the progress of data loading process
	 */
	protected ProgressBar progressBar;
	/**
	 * manage the offline data of eVoterMobile
	 */
	protected OfflineEVoterManager offlineEVoterManager;
	
	protected AsyncHttpClient client;
	
	protected MainMenu mainMenu;
	
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
		client = new AsyncHttpClient(RequestConfig.TIME_OUT);
		offlineEVoterManager = new OfflineEVoterManager(this);
		setupMainMenu();
		ivTitleBarIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.show();
			}
		});
		
	}
	
	/**
	 * 
	 */
	private void setupMainMenu() {
		mainMenu = new MainMenu(this);
		
		mainMenu.getBtExit().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		
		mainMenu.getBtLogout().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				offlineEVoterManager.logoutUser();
			}
		});
		
		mainMenu.getBtAcceptUsers().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Request accepted user of a session");
				mainMenu.dismiss();
				Intent acceptedStudents = new Intent(EVoterActivity.this, AcceptedStudents.class);
				startActivity(acceptedStudents);
			}
		});
		
		mainMenu.getBtAllQuestion().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Show list all questions");
				mainMenu.dismiss();
				Intent allQuestion = new Intent(EVoterActivity.this, AllQuestionActivity.class);
				startActivity(allQuestion);
			}
		});
		
		mainMenu.getBtListUsers().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Request all user of a subject");
				mainMenu.dismiss();
				Intent subjectUserActivity = new Intent(EVoterActivity.this, SubjectUserActivity.class);
				startActivity(subjectUserActivity);
			}
		});
		
		mainMenu.getBtNewQuestion().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Create new question");
				mainMenu.dismiss();
				Intent newQuestion = new Intent(EVoterActivity.this, NewQuestionActivity.class);
				startActivity(newQuestion);
				
			}
		});
		
		mainMenu.getBtNewSession().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newSessionIntent = new Intent(EVoterActivity.this, NewSessionActivity.class);
				startActivity(newSessionIntent);
				mainMenu.dismiss();
			}
		});
		
	}
	
	/**
	 * exit application from anywhere in application <br>
	 * Show a dialog to confirm exiting application
	 */
	public void exit() {
		final DialogInfor dialogExit = new DialogInfor(EVoterActivity.this, "Do you really want to exit application?");
		dialogExit.setMessageContent("");
		dialogExit.getBtOK().setText("Exit");
		dialogExit.getBtOK().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Goodbye....");
				exitApplication();
			}
		});
		dialogExit.getBtKO().setText("Cancel");
		dialogExit.getBtKO().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogExit.dismiss();
				
			}
		});
		dialogExit.show();
	};
	
	/**
	 * Exit application
	 */
	public void exitApplication() {
		Intent exitIntent = new Intent(this, StartActivity.class);
		exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		exitIntent.putExtra("Exit application", true);
		startActivity(exitIntent);
		finish();
	}
	
}
