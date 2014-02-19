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
	 * @param resetPasswordActivity
	 */
	public static void gotoLogin(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		
	}
	
}