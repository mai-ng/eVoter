/**
 * 
 */
package evoter.mobile.activities;

import android.content.Context;
import android.content.Intent;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.share.model.Question;

/**
 * @author luongnv89
 */
public class ActivityManager {
	
	/**
	 * @param question question to show statistic of
	 * @param context The context where call the function
	 */
	public static void viewQuestionStatistic(Question question, Context context) {
		EVoterShareMemory.setCurrentQuestion(question);
		Intent statisticActivity = new Intent(context, QuestionStatisticActivity.class);
		context.startActivity(statisticActivity);
	}
	
}
