/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import evoter.mobile.main.R;
import evoter.mobile.objects.RuntimeEVoterManager;

/**
 * Created by @author luongnv89 on 30-Jun-2014
 * <br>
 * Manage creating a new session in a subject
 *
 */
public class NewSessionActivity extends EVoterActivity {
	
	EditText etTitle;
	ListView listView;
	Button btAddQuestion;
	Button btSave;
	Button btCancel;
	

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_session);
		
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSubjectName());
		menuDialog.setMenuSubjectActivity();
		
		etTitle = (EditText)findViewById(R.id.etNewSessionName);
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
