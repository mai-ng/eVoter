/**
 * 
 */
package evoter.mobile.activities;

import evoter.mobile.main.R;
import evoter.mobile.objects.RuntimeEVoterManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author luongnv89
 *
 */
public class EditSessionActivity extends NewSessionActivity {

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.NewSessionActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSessionName());
		menuDialog.setMenuSubjectActivity();
		
		etTitle = (EditText)findViewById(R.id.etNewSessionName);
		etTitle.setText(RuntimeEVoterManager.getCurrentSessionName());
		listView = (ListView)findViewById(R.id.lvNewSessionQuestion);
		btAddQuestion = (Button)findViewById(R.id.btNewSessionAddQuestion);
		btSave = (Button)findViewById(R.id.btNewSessionSave);
		btCancel = (Button)findViewById(R.id.btNewSessionCancel);
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
}
