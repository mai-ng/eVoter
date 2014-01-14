/**
 * 
 */
package evoter.mobile.objects;

import evoter.mobile.main.R;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Present an information dialog in eVoter application <br>
 * Include a TextView {@link DialogInfor#tvMessage} content the message of
 * application <br>
 * a Button {@link DialogInfor#btOK} to process an action like: OK, Agree,
 * Retry,... <br>
 * a Button {@link DialogInfor#btKO} to process an action which is close
 * application or cancel,....
 * 
 * @author luongnv89
 * 
 */
public class DialogInfor extends Dialog {

	private TextView tvMessage;
	private Button btOK;
	private Button btKO;

	/**
	 * @param context
	 */
	public DialogInfor(Context context, String title) {
		super(context);
		setTitle(title);
		initialDialog();
	}

	/**
	 * 
	 */
	private void initialDialog() {
		setContentView(R.layout.dialog_info);
		tvMessage = (TextView) findViewById(R.id.tvDialogMessage);
		btOK = (Button) findViewById(R.id.btDialogOK);
		btKO = (Button) findViewById(R.id.btDialogExit);
		WindowManager.LayoutParams layoutParameters = new WindowManager.LayoutParams();
		layoutParameters.copyFrom(this.getWindow().getAttributes());
		layoutParameters.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParameters.height = WindowManager.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(layoutParameters);
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public DialogInfor(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initialDialog();
	}

	/**
	 * @param context
	 * @param theme
	 */
	public DialogInfor(Context context, int theme) {
		super(context, theme);
		initialDialog();
	}

	/**
	 * @return the tvMessage
	 */
	public TextView getTvMessage() {
		return tvMessage;
	}

	/**
	 * @param tvMessage
	 *            the tvMessage to set
	 */
	public void setMessageContent(String message) {
		this.tvMessage.setText(message);
	}

	/**
	 * @return the btOK
	 */
	public Button getBtOK() {
		return btOK;
	}

	/**
	 * @return the btKO
	 */
	public Button getBtKO() {
		return btKO;
	}

}
