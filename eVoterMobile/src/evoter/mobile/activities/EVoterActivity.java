/**
 * 
 */
package evoter.mobile.activities;

import java.util.HashMap;

import org.json.JSONArray;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.MainMenu;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.UserDAO;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Update by @author luongnv89 on 12-Feb-2014: <br>
 * <li>add logout request to server <br>
 * Update by @author luongnv89 on 09-Feb-2014: <br> <li>Change the name of
 * {@link MainMenu} variable to mainMenu <br>
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
	protected ProgressBar internetProcessBar;
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
		EVoterShareMemory.setOfflineEVoterManager(offlineEVoterManager);
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
				logout();
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
		
		mainMenu.getBtStatistic().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (EVoterShareMemory.getExictedQuestion() == null || EVoterShareMemory.getDifficultQuestion() == null) {
					EVoterMobileUtils.showeVoterToast(EVoterActivity.this, EVoterShareMemory.getCurrentSession().getTitle() + " does not have feedback");
				} else {
					Intent feedback = new Intent(EVoterActivity.this, StudentFeedbackActivity.class);
					startActivity(feedback);
					mainMenu.dismiss();
				}
			}
		});
		
		mainMenu.getBtNewQuestion().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Main menu", "Create new question");
				mainMenu.dismiss();
				EVoterShareMemory.setPreviousContext(EVoterActivity.this);
				Intent newQuestion = new Intent(EVoterActivity.this, NewQuestionActivity.class);
				startActivity(newQuestion);
				
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
		
	}
	
	/**
	 * exit application from anywhere in application <br>
	 * Show a dialog to confirm exiting application
	 */
	public void exit() {
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
	private void logout() {
		offlineEVoterManager.logoutUser();
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		
		client.post(RequestConfig.getURL(URIRequest.LOGOUT), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						Log.i("Response", response);
						if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
							EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Goodbye...!");
						} else {
							EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "You are not logged out from system!");
						}
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								EVoterActivity.this,
								"Cannot request logout from server!");
						Log.e("Logout", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
	}
	
	public void refreshData() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!EVoterMobileUtils.hasInternetConnection(this)) {
			errorConnection();
		} else {
			refreshData();
		}
	};
	
	/**
	 * 
	 */
	protected void errorConnection() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("Internet connection")
				.setMessage("Cannot connect to internet. Check your mobile internet connection an try again!")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.exit_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						exitApplication();
					}
				})
				.setNegativeButton(R.string.retry_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent exitIntent = new Intent(EVoterActivity.this,
								StartActivity.class);
						exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						exitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(exitIntent);
					}
				}).show();
	}
	
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.LOGIN_EVOTER_REQUEST)) {
			String userKey = null;
			try {
				
				JSONObject object = new JSONObject(
						response);
				userKey = object
						.getString(UserDAO.USER_KEY);
				
			} catch (JSONException e) {
				e.printStackTrace();
				EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Error! Cannot get user information");
			}
			
			//Got the userkey
			if (userKey != null && userKey != "null") {
				Log.i("USER_KEY", userKey);
				HashMap<String, String> user = offlineEVoterManager.getSavedUserDetail();
				EVoterShareMemory
						.setUSER_KEY(userKey);
				EVoterShareMemory
						.setCurrentUserName(user.get(UserDAO.USER_NAME));
				EVoterMobileUtils.showeVoterToast(
						EVoterActivity.this,
						"Welcome "
								+ EVoterShareMemory
										.getCurrentUserName()
								+ " to eVoter!");
				
				Intent subjectIntent = new Intent(
						EVoterActivity.this,
						SubjectActivity.class);
				subjectIntent
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				subjectIntent
						.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(subjectIntent);
				
			}
			else {
				EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Error! Username and password is not correct. Please try again!");
			}
		}else if (callBackMessage.equals(CallBackMessage.UPDATE_QUESTION_EVOTER_REQUEST)) {
			try {
				JSONArray array = new JSONArray(response);
				Question question = null;
				if (array != null)
					question = EVoterMobileUtils.parserToQuestion(array.getJSONObject(0));
				if (question != null) {
					EVoterShareMemory.setCurrentQuestion(question);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		} else if (callBackMessage.equals(CallBackMessage.UPDATE_SESSION_EVOTER_REQUEST)) {
			try {
				JSONArray array = EVoterMobileUtils.getJSONArray(response);
				Session session = EVoterMobileUtils.parserSession(array.getJSONObject(0));
				if (session != null) {
					EVoterShareMemory.setCurrentSession(session);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.i("Update session", "Exception");
			}
			
		}
		
	}
	
	public void shutdownByException() {
		EVoterMobileUtils.showeVoterToast(EVoterActivity.this, "Exception! Restart eVoter and try again!");
		exitApplication();
	}
	
}
