/**
 * 
 */
package evoter.mobile.activities;

import evoter.mobile.main.R;
import evoter.mobile.objects.RuntimeEVoterManager;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author luongnv89
 */
public class SubjectUserActivity extends EVoterActivity {
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_users);
		this.ivTitleBarRefresh.setVisibility(View.VISIBLE);
		
		// When the refresh icon is click, the data of listview will be reloaded
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadListItemData();
			}

		});
		
		//Setup dialog which is show the loading process
		dialogLoading = new Dialog(this);
		dialogLoading.setContentView(R.layout.dialog_loading);
		dialogLoading.setTitle("Refresh");
		WindowManager.LayoutParams layoutParameters = new WindowManager.LayoutParams();
		layoutParameters.copyFrom(dialogLoading.getWindow().getAttributes());
		layoutParameters.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutParameters.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialogLoading.getWindow().setAttributes(layoutParameters);
		
		tvLoadingStatus = (TextView) dialogLoading
				.findViewById(R.id.tvLoadingStatus);
		progressBar = (ProgressBar) dialogLoading
				.findViewById(R.id.progressRefresh);
		progressBar.setProgress(0);
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentUserName());
	}
	

	private void loadListItemData() {
		// TODO Auto-generated method stub
		
	}
	
}
