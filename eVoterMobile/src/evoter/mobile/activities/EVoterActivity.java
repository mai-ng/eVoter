/**
 * 
 */
package evoter.mobile.activities;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterMainMenu;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.UserDAO;
import evoter.share.model.UserType;

/**
 * <br>
 * Update by @author luongnv89 on 12-Feb-2014: <br>
 * <li>add logout request to server <br>
 * Update by @author luongnv89 on 09-Feb-2014: <br> <li>Change the name of
 * {@link EVoterMainMenu} variable to mainMenu <br>
 * <br>
 * Update by @author luongnv89 on Thu 30-Jan-2014: <br> <li>Add constructor for
 * {@link OfflineEVoterManager} <br>
 * On Sat - 18/01/2014 - modified by luongnv89: <br>
 * <br>
 * Add {@link EVoterActivity#exit()} - to exit application from anywhere when
 * the application has error, exception,... avoid stuck phone
 * {@link EVoterActivity} is a parent class of all activity of eVoterMobile
 * application <br>
 * Created by @author luongnv89
 */
public abstract class EVoterActivity extends Activity {
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
	protected ProgressBar internetProcessBar;
	/**
	 * manage the offline data of eVoterMobile
	 */
	protected OfflineEVoterManager offlineEVoterManager;
	
	protected EVoterMainMenu mainMenu;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		offlineEVoterManager = new OfflineEVoterManager(this);
		EVoterShareMemory.setOfflineEVoterManager(offlineEVoterManager);
		mainMenu = new EVoterMainMenu(this);
		
		setupTitleBar();
		setupContentMainMenu();
		setupMainMenu();
		
		loadData();
	}
	
	/**
	 * Setup the content of menu depend on current activity <li>Global menu:
	 * Show for all activity <br>
	 * {@link EVoterMainMenu#MN_EXIT} exit application <br>
	 * {@link EVoterMainMenu#MN_LOGOUT} logout of system <br>
	 * {@link EVoterMainMenu#MN_CANCEL}cancel menu option <li>Subject menu: Show
	 * the option in a subject. {@link SessionActivity} and lower level <br>
	 * {@link EVoterMainMenu#MN_USER_OF_SUBJECT} show user of subject <br>
	 * {@link EVoterMainMenu#MN_NEW_SESSION} create new session in current
	 * subject <li>Session menu: Show the option in a session. For
	 * {@link QuestionActivity} and lower level <br>
	 * {@link EVoterMainMenu#MN_START_SESSION} active current session <br>
	 * {@link EVoterMainMenu#MN_STOP_SESSION} inactive current session <br>
	 * {@link EVoterMainMenu#MN_NEW_QUESTION} create new question in current
	 * session <br>
	 * {@link EVoterMainMenu#MN_ACCEPT_STUDENT} show student who has accepted to
	 * join session <br>
	 * {@link EVoterMainMenu#MN_JOIN} Student join session <li>Question
	 */
	protected void setupContentMainMenu() {
		if (EVoterShareMemory.getCurrentSession() != null) {
			if (EVoterShareMemory.getCurrentSession().isActive()) {
				mainMenu.getBtChangeSessionStatus().setText(EVoterMainMenu.MN_STOP_SESSION);
			} else {
				mainMenu.getBtChangeSessionStatus().setText(EVoterMainMenu.MN_START_SESSION);
			}
		}
	}
	
	/**
	 * Set title bar of activity <li>When user click on
	 * {@link EVoterActivity#ivTitleBarIcon}, show the main menu option of
	 * current activity <li>When user click on
	 * {@link EVoterActivity#ivTitleBarRefresh}, refresh content (data +
	 * interface) of current activity <li>The content of title bar depend on
	 * activity: <br>
	 * In {@link StartActivity}, {@link LoginActivity}, {@link RegisterActivity}, {@link ResetPasswordActivity} show app name <br>
	 * In {@link SubjectActivity} show username <br>
	 * In {@link SubjectUserActivity}, {@link SessionActivity},
	 * {@link NewSessionActivity}, show current subject title <br>
	 * In {@link QuestionActivity}, {@link EditSessionActivity},
	 * {@link AcceptedStudentsActivity}, {@link StudentFeedbackActivity} show current
	 * session title <br>
	 * In {@link QuestionDetailActivity}, {@link QuestionStatisticActivity},
	 * {@link EditQuestionActivity} show current question
	 */
	protected void setupTitleBar() {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.start);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.evoter_title_bar);
		tvTitleBarContent = (TextView) findViewById(R.id.tvTitleBar);
		tvTitleBarContent.setText(R.string.content_title_bar);
		
		ivTitleBarRefresh = (ImageView) findViewById(R.id.ivRefreshTitleBar);
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refeshContent();
			}
		});
		
		ivTitleBarIcon = (ImageView) findViewById(R.id.ivIconTitleBar);
		ivTitleBarIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.show();
			}
		});
	}
	
	/**
	 * Reload data and re-build component of activity <br>
	 * Request to server to reload data <br>
	 * If request data return successful, re-build component of activity base on
	 * new data <br>
	 * otherwise, keep current content
	 */
	protected void refeshContent() {
		loadData();
	}
	
	/**
	 * Setup main menu action
	 */
	protected void setupMainMenu() {
		
		//GLOBAL MENU OPTION
		mainMenu.getBtExit().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exit();
			}
		});
		
		mainMenu.getBtLogout().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logout();
			}
		});
		
		mainMenu.getBtCancel().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.dismiss();
			}
		});
		
		
		//SUBJECT MENU
		mainMenu.getBtUserOfSubject().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.dismiss();
				ActivityManager.startSubjectUserActivity(EVoterActivity.this);
			}
		});
		
		mainMenu.getBtNewSession().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.dismiss();
				ActivityManager.startNewSessionActivity(EVoterActivity.this);
				
			}
		});
		
		
		//SESSION MENU
		mainMenu.getBtViewFeedback().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityManager.startStudentFeedBackActivity(EVoterActivity.this);
				mainMenu.dismiss();
			}
		});
		
		mainMenu.getBtNewQuestion().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityManager.startNewQuestionActivity(EVoterActivity.this);
				mainMenu.dismiss();
				
			}
		});
		mainMenu.getBtAcceptedStudent().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainMenu.dismiss();
				ActivityManager.startAcceptedStudent(EVoterActivity.this);
			}
		});
		
		
	}
	
	/**
	 * exit application from anywhere in application <br>
	 * Show a dialog to confirm exiting application
	 */
	public void exit() {
		mainMenu.dismiss();
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("Exit application?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Goodbye....");
						exitApplication();
					}
				})
				.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
	};
	
	/**
	 * Exit application
	 */
	public void exitApplication() {
		Intent exitIntent = new Intent(this, StartActivity.class);
		exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		exitIntent.putExtra(StartActivity.EXIT_APPLICATION, true);
		startActivity(exitIntent);
		finish();
	}
	
	/**
	 * 
	 */
	protected void logout() {
		offlineEVoterManager.logoutUser();
		EVoterRequestManager.logout(EVoterActivity.this);
	}
	
	
	/**
	 * Callback function.
	 * <br> According to {@link CallBackMessage} to process data
	 * 
	 * @param response response which eVoterMobile receive from server
	 * @param callBackMessage value at {@link CallBackMessage}
	 */
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.LOGIN_EVOTER_REQUEST)) {
			try {
				JSONObject object = new JSONObject(response);
				String userKey = object.getString(UserDAO.USER_KEY);
				
				if (userKey != null) {
					Log.i("USER_KEY", userKey);
					HashMap<String, String> user = offlineEVoterManager.getSavedUserDetail();
					EVoterShareMemory.setUSER_KEY(userKey);
					EVoterShareMemory.setCurrentUserName(user.get(UserDAO.USER_NAME));
					EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Welcome " + EVoterShareMemory.getCurrentUserName() + " to eVoter!");
					ActivityManager.startSubjectActivity(EVoterActivity.this);
				} else {
					EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Cannot login: " + response);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				shutdownByException();
			}
			
		}
	}
	
	/**
	 * Shutdown application when there is some exception
	 */
	public void shutdownByException() {
		EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Exception! Restart eVoter and try again!");
		exitApplication();
	}
	
	/**
	 * Reload data for content
	 */
	public abstract void loadData();
	
	public void setQuestionActivityMenu(){
		mainMenu.getBtAcceptedStudent().setVisibility(View.VISIBLE);
		if (EVoterShareMemory.getCurrentUserType() == UserType.TEACHER) {
			mainMenu.getBtNewSession().setVisibility(View.VISIBLE);
			mainMenu.getBtNewQuestion().setVisibility(View.VISIBLE);
			mainMenu.getBtChangeSessionStatus().setVisibility(View.VISIBLE);
			mainMenu.getBtViewFeedback().setVisibility(View.VISIBLE);
			mainMenu.getBtJoin().setVisibility(View.GONE);
		} else if (EVoterShareMemory.getCurrentUserType() == UserType.STUDENT) {
			mainMenu.getBtViewFeedback().setVisibility(View.GONE);
			mainMenu.getBtNewQuestion().setVisibility(View.GONE);
			mainMenu.getBtNewSession().setVisibility(View.GONE);
			mainMenu.getBtChangeSessionStatus().setVisibility(View.GONE);
			mainMenu.getBtJoin().setVisibility(View.VISIBLE);
			if (EVoterShareMemory.userJoinedSession()) mainMenu.getBtJoin().setVisibility(View.GONE);
		}
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(EVoterShareMemory.getPreviousContext()!=null)
		EVoterShareMemory.getPreviousContext().refeshContent();
	}
}
