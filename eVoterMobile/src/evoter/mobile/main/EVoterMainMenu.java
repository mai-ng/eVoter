/**
 * 
 */
package evoter.mobile.main;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.R;

/**
 * Update by @author luongnv89 on 09-Feb-2014:<br>
 * <li>Change the classname from MenuDialog to MainMenu Created by @author
 * luongnv89 on 19-Jan-2014:<br>
 * The menu option show when user click on the icon of appliction
 */
public class EVoterMainMenu extends Dialog {
	
	public static final String MN_STOP_SESSION = "Stop Session";
	public static final String MN_START_SESSION = "Start Session";
	public static final String MN_EXIT = "Exit";
	public static final String MN_LOGOUT = "Logout";
	public static final String MN_USER_OF_SUBJECT="User of Subject";
	public static final String MN_NEW_SESSION="New Session";
	public static final String MN_ACCEPT_STUDENT="Accepted Student";
	public static final String MN_VIEW_FEED_BACK="Feed Back";
	public static final String MN_JOIN = "Join Session";
	public static final String MN_NEW_QUESTION="New Question";
	public static final String MN_CANCEL="Cancel";
	
	/**
	 * Cancel menu option
	 */
	private Button btCancel;
	/**
	 * Exit application
	 */
	private Button btExit;
	/**
	 * Logout application
	 */
	private Button btLogout;
	/**
	 * Show list users who has joined current subject
	 */
	private Button btUserOfSubject;
	/**
	 * Create new session, only show with teacher user
	 */
	private Button btNewSession;
	/**
	 * Show list student who has accepted to join current session
	 */
	private Button btAcceptedStudent;
	/**
	 * Only show with teacher user. <br>
	 * Change status of session, the status changes depend the label of button. <br>
	 * {@link EVoterMainMenu#MN_STOP_SESSION} inactive session <br>
	 * {@link EVoterMainMenu#MN_START_SESSION} active session
	 */
	private Button btChangeSessionStatus;
	/**
	 * Only show with teacher user <br>
	 * Show feedback about the excited level and difficult level of student
	 * about current subject <br>
	 * Maximum value: 5 <br>
	 * Miniimum value: 0
	 */
	private Button btViewFeedback;
	/**
	 * Only show with student user, who has not accepted to join current session <br>
	 * Student accept to join current session
	 */
	private Button btJoin;
	/**
	 * Only show with teacher user <br>
	 * Create new question in current session
	 */
	private Button btNewQuestion;
	
	/**
	 * Context where the {@link EVoterMainMenu} is created
	 */
	EVoterActivity context;
	
	/**
	 * @param context
	 */
	public EVoterMainMenu(EVoterActivity context) {
		super(context);
		setContentView(R.layout.icon_menu);
		setTitle("eVoter Menu");
		WindowManager.LayoutParams layoutParameters = new WindowManager.LayoutParams();
		layoutParameters.copyFrom(this.getWindow().getAttributes());
		layoutParameters.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParameters.height = WindowManager.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(layoutParameters);
		initialComponents();
	}
	
	

	/**
	 * Initial component of {@link EVoterMainMenu} dialog
	 */
	private void initialComponents() {
		//Set id and label
		//GLOBAL MENU
		btLogout = (Button) findViewById(R.id.btLogout);
		btLogout.setText(MN_LOGOUT);
		btExit = (Button) findViewById(R.id.btExit);
		btExit.setText(MN_EXIT);
		btCancel = (Button)findViewById(R.id.btMenuCancel);
		btCancel.setText(MN_CANCEL);
		
		//MENU FOR SUBJECT LEVEL
		btUserOfSubject = (Button) findViewById(R.id.btSubjectUser);
		btUserOfSubject.setText(MN_USER_OF_SUBJECT);
		
		btNewSession = (Button) findViewById(R.id.btNewSession);
		btNewSession.setText(MN_NEW_SESSION);
		
		//MENU FOR SESSION LEVEL
		btNewQuestion = (Button) findViewById(R.id.btNewQuestion);
		btNewQuestion.setText(MN_NEW_QUESTION);
		btAcceptedStudent = (Button) findViewById(R.id.btAcceptedUser);
		btAcceptedStudent.setText(MN_ACCEPT_STUDENT);
		btViewFeedback = (Button) findViewById(R.id.btStatitisticBar);
		btViewFeedback.setText(MN_VIEW_FEED_BACK);
		btJoin = (Button) findViewById(R.id.btAcceptJoin);
		btJoin.setText(MN_JOIN);
		btChangeSessionStatus = (Button) findViewById(R.id.btStartSession);
		
		//Invisible all menu which is not a global menu
		btUserOfSubject.setVisibility(View.GONE);
		btViewFeedback.setVisibility(View.GONE);
		btNewQuestion.setVisibility(View.GONE);
		btNewSession.setVisibility(View.GONE);
		btChangeSessionStatus.setVisibility(View.GONE);
		btAcceptedStudent.setVisibility(View.GONE);
		btJoin.setVisibility(View.GONE);
	}
	
	
	/**
	 * @return the btExit
	 */
	public Button getBtExit() {
		return btExit;
	}
	
	/**
	 * @return the btLogout
	 */
	public Button getBtLogout() {
		return btLogout;
	}
	
	/**
	 * @return the btUserOfSubject
	 */
	public Button getBtUserOfSubject() {
		return btUserOfSubject;
	}
	
	/**
	 * @return the btNewSession
	 */
	public Button getBtNewSession() {
		return btNewSession;
	}
	
	/**
	 * @return the btAcceptedStudent
	 */
	public Button getBtAcceptedStudent() {
		return btAcceptedStudent;
	}
	
	/**
	 * @return the btChangeSessionStatus
	 */
	public Button getBtChangeSessionStatus() {
		return btChangeSessionStatus;
	}
	
	/**
	 * @return the btViewFeedback
	 */
	public Button getBtViewFeedback() {
		return btViewFeedback;
	}
	
	/**
	 * @return the btJoin
	 */
	public Button getBtJoin() {
		return btJoin;
	}
	
	/**
	 * @return the btNewQuestion
	 */
	public Button getBtNewQuestion() {
		return btNewQuestion;
	}

	/**
	 * @return the btCancel
	 */
	public Button getBtCancel() {
		return btCancel;
	}
	
}
