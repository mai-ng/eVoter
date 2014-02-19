/**
 * 
 */
package evoter.mobile.main;

import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.activities.AcceptedStudentsActivity;
import evoter.mobile.activities.EditQuestionActivity;
import evoter.mobile.activities.EditSessionActivity;
import evoter.mobile.activities.LoginActivity;
import evoter.mobile.activities.NewQuestionActivity;
import evoter.mobile.activities.NewSessionActivity;
import evoter.mobile.activities.QuestionActivity;
import evoter.mobile.activities.QuestionDetailActivity;
import evoter.mobile.activities.RegisterActivity;
import evoter.mobile.activities.ResetPasswordActivity;
import evoter.mobile.activities.SessionActivity;
import evoter.mobile.activities.StudentFeedbackActivity;
import evoter.mobile.activities.SubjectActivity;
import evoter.mobile.activities.SubjectUserActivity;
import evoter.mobile.utils.EVoterMobileUtils;
import android.content.Intent;

/**
 * @author luongnv89
 */
public class ActivityManager {
	
	
	/**
	 * Start a {@link LoginActivity} from an {@link EVoterActivity} context
	 * @param context
	 */
	public static void startLoginActivity(EVoterActivity context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * Start a {@link SubjectActivity} after user login successfully
	 * <br>Set new {@link SubjectActivity} is the top activity
	 * @param eVoterActivity
	 */
	public static void startSubjectActivity(EVoterActivity eVoterActivity) {
		Intent subjectIntent = new Intent(eVoterActivity,SubjectActivity.class);
		subjectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		subjectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		eVoterActivity.startActivity(subjectIntent);
	}

	
	/**
	 * Start a {@link ResetPasswordActivity} from an {@link EVoterActivity} context
	 * @param loginActivity
	 */
	public static void startResetPasswordActivity(EVoterActivity eVoterActivity) {
		Intent registerIntent = new Intent(eVoterActivity,
				ResetPasswordActivity.class);
		eVoterActivity.startActivity(registerIntent);
		
	}
	
	/**
	 * Start a {@link RegisterActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startRegisterActivity(EVoterActivity eVoterActivity) {
		Intent registerIntent = new Intent(eVoterActivity,RegisterActivity.class);
		eVoterActivity.startActivity(registerIntent);
	}
	
	/**
	 * Start a {@link SubjectUserActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startSubjectUserActivity(EVoterActivity eVoterActivity) {
		Intent subjectUserActivity = new Intent(eVoterActivity, SubjectUserActivity.class);
		eVoterActivity.startActivity(subjectUserActivity);
	}

	/**
	 * Start a {@link StudentFeedbackActivity} from an {@link EVoterActivity} context
	 * <br>Context can start:
	 * <li> {@link QuestionActivity}
	 * <li> {@link QuestionDetailActivity}
	 * <li> {@link NewQuestionActivity}
	 * <li> {@link EditQuestionActivity}
	 * <br> Only available for teacher.
	 * @param context
	 */
	public static void startStudentFeedBackActivity(EVoterActivity context) {
		EVoterShareMemory.setPreviousContext(context);
		if (EVoterShareMemory.getExictedQuestion() == null || EVoterShareMemory.getDifficultQuestion() == null) {
			EVoterMobileUtils.showeVoterToast(context, EVoterShareMemory.getCurrentSession().getTitle() + " does not have feedback");
		} else {
			Intent feedback = new Intent(context, StudentFeedbackActivity.class);
			context.startActivity(feedback);
		}
		
	}

	/**Start a {@link NewQuestionActivity} from an {@link EVoterActivity} context
	 * <br> Only show for teacher
	 * <br> When new activity {@link NewQuestionActivity} finish, the previous
	 *  context will be refreshed
	 * @param eVoterActivity
	 */
	public static void startNewQuestionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent newQuestion = new Intent(eVoterActivity,NewQuestionActivity.class);
		eVoterActivity.startActivity(newQuestion);
		
	}

	/**
	 * Start a {@link AcceptedStudentsActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startAcceptedStudent(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent acceptedStudents = new Intent(eVoterActivity, AcceptedStudentsActivity.class);
		eVoterActivity.startActivity(acceptedStudents);
	}

	/**
	 * Start a {@link NewSessionActivity} from an {@link EVoterActivity} context
	 * <br> Only show for teacher
	 * <br> When new activity {@link NewSessionActivity} finish, the previous context will be refreshed
	 * @param eVoterActivity
	 */
	public static void startNewSessionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent newSession = new Intent(eVoterActivity, NewSessionActivity.class);
		eVoterActivity.startActivity(newSession);
	}
	
	/**
	 * Start a {@link EditSessionActivity} from an {@link EVoterActivity} context
	 * <br> Only show for teacher
	 * <br> When new activity {@link EditSessionActivity} finish, the previous context will be refreshed
	 * @param eVoterActivity
	 */
	public static void startEditSessionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent editSession = new Intent(eVoterActivity, EditSessionActivity.class);
		eVoterActivity.startActivity(editSession);
	}
	
	/**
	 * Start a {@link EditQuestionActivity} from an {@link EVoterActivity} context
	 * <br> Only show for teacher
	 * <br> When new activity {@link EditQuestionActivity} finish, the previous context will be refreshed
	 * @param eVoterActivity
	 */
	public static void startEditQuestionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent editQuestion = new Intent(eVoterActivity, EditQuestionActivity.class);
		eVoterActivity.startActivity(editQuestion);
	}

	/**
	 * Start a {@link QuestionActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startQuestionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent questionActivity = new Intent(eVoterActivity, QuestionActivity.class);
		eVoterActivity.startActivity(questionActivity);
		
	}

	/**
	 * Start a {@link QuestionDetailActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startQuestionDetailActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent detailQuestion = new Intent(eVoterActivity, QuestionDetailActivity.class);
		eVoterActivity.startActivity(detailQuestion);
		
	}
	
	/**
	 * Start a {@link SessionActivity} from an {@link EVoterActivity} context
	 * @param eVoterActivity
	 */
	public static void startSessionActivity(EVoterActivity eVoterActivity) {
		EVoterShareMemory.setPreviousContext(eVoterActivity);
		Intent detailQuestion = new Intent(eVoterActivity, SessionActivity.class);
		eVoterActivity.startActivity(detailQuestion);
		
	}

}