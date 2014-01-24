/**
 * 
 */
package evoter.mobile.objects;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import evoter.mobile.main.R;
import evoter.share.model.UserType;

/**
 * Created by @author luongnv89 on 19-Jan-2014:<br>
 * Manage main menu of eVoterMobile.
 */
public class MenuDialog extends Dialog {
	
	LinearLayout mainMenu;
	Button btExit;
	Button btLogout;
	
	LinearLayout subjectActivityMenu;
	Button btListUsers;
	
	LinearLayout sessionActivityMenu;
	Button btNewSession;
	Button btAcceptUsers;
	
	LinearLayout questionActivityMenu;
	Button btNewQuestion;
	
	LinearLayout questionDetailActivityMenu;

	Button btAllQuestion;
	
	/**
	 * @param context
	 */
	public MenuDialog(Context context) {
		super(context);
		this.setTitle("MAIN MENU");
		setContentView(R.layout.icon_menu);
		WindowManager.LayoutParams layoutParameters = new WindowManager.LayoutParams();
		layoutParameters.copyFrom(this.getWindow().getAttributes());
		layoutParameters.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParameters.height = WindowManager.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(layoutParameters);
		
		mainMenu = (LinearLayout) findViewById(R.id.mainMenu);
		btExit = (Button) findViewById(R.id.btExit);
		btLogout = (Button) findViewById(R.id.btLogout);
		
		subjectActivityMenu = (LinearLayout) findViewById(R.id.subjectActivityMenu);
		btListUsers = (Button) findViewById(R.id.btSubjectUser);
		
		sessionActivityMenu = (LinearLayout) findViewById(R.id.sessionActivityMenu);
		btNewSession = (Button) findViewById(R.id.btNewSession);
		btNewSession.setVisibility(View.GONE);
		btAcceptUsers = (Button) findViewById(R.id.btAcceptedUser);
		
		questionActivityMenu = (LinearLayout) findViewById(R.id.questionActivityMenu);
		btNewQuestion = (Button) findViewById(R.id.btNewQuestion);
		btNewQuestion.setVisibility(View.GONE);
		
		questionDetailActivityMenu = (LinearLayout) findViewById(R.id.questionDetailActivityMenu);
		
		btAllQuestion = (Button) findViewById(R.id.btAllQuestion);
		subjectActivityMenu.setVisibility(View.GONE);
		sessionActivityMenu.setVisibility(View.GONE);
		questionActivityMenu.setVisibility(View.GONE);
		questionDetailActivityMenu.setVisibility(View.GONE);
		btAllQuestion.setVisibility(View.GONE);
		if(RuntimeEVoterManager.getCurrentUserType()==UserType.TEACHER){
			btAllQuestion.setVisibility(View.VISIBLE);
			btNewSession.setVisibility(View.VISIBLE);
			btNewQuestion.setVisibility(View.VISIBLE);
		}
	}
	
	public void setMenuSubjectActivity(){
		subjectActivityMenu.setVisibility(View.VISIBLE);
		sessionActivityMenu.setVisibility(View.GONE);
		questionActivityMenu.setVisibility(View.GONE);
		questionDetailActivityMenu.setVisibility(View.GONE);
	}
	
	public void setMenuSessionActivity(){
		sessionActivityMenu.setVisibility(View.VISIBLE);
		subjectActivityMenu.setVisibility(View.GONE);
		questionActivityMenu.setVisibility(View.GONE);
		questionDetailActivityMenu.setVisibility(View.GONE);
	}
	
	public void setMenuQuestionActivity(){
		questionActivityMenu.setVisibility(View.VISIBLE);
		subjectActivityMenu.setVisibility(View.GONE);
		sessionActivityMenu.setVisibility(View.GONE);
		questionDetailActivityMenu.setVisibility(View.GONE);
	}
	
	public void setMenuQuestionDetailActivity(){
		questionDetailActivityMenu.setVisibility(View.VISIBLE);
		subjectActivityMenu.setVisibility(View.GONE);
		sessionActivityMenu.setVisibility(View.GONE);
		questionActivityMenu.setVisibility(View.GONE);
	}
	
	/**
	 * @return the mainMenu
	 */
	public LinearLayout getMainMenu() {
		return mainMenu;
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
	 * @return the subjectActivityMenu
	 */
	public LinearLayout getSubjectActivityMenu() {
		return subjectActivityMenu;
	}

	/**
	 * @return the btListUsers
	 */
	public Button getBtListUsers() {
		return btListUsers;
	}

	/**
	 * @return the sessionActivityMenu
	 */
	public LinearLayout getSessionActivityMenu() {
		return sessionActivityMenu;
	}

	/**
	 * @return the btNewSession
	 */
	public Button getBtNewSession() {
		return btNewSession;
	}

	/**
	 * @return the btAcceptUsers
	 */
	public Button getBtAcceptUsers() {
		return btAcceptUsers;
	}

	/**
	 * @return the questionActivityMenu
	 */
	public LinearLayout getQuestionActivityMenu() {
		return questionActivityMenu;
	}

	/**
	 * @return the btNewQuestion
	 */
	public Button getBtNewQuestion() {
		return btNewQuestion;
	}

	/**
	 * @return the questionDetailActivityMenu
	 */
	public LinearLayout getQuestionDetailActivityMenu() {
		return questionDetailActivityMenu;
	}

	/**
	 * @return the btAllQuestion
	 */
	public Button getBtAllQuestion() {
		return btAllQuestion;
	}

}
