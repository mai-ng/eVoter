/**
 * 
 */
package evoter.mobile.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import evoter.mobile.adapters.ItemDataAdapter;
import evoter.mobile.main.R;
import evoter.mobile.objects.OfflineEVoterManager;

/**
 * @author luongnv89
 * 
 */
public abstract class ItemDataActivity extends EVoterActivity {
	protected ListView listView;
	protected ItemDataAdapter adapter;
	protected Context context;
	protected EditText etSearch;
	protected TextView tvLoadingStatus;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item_data_view);

		this.ivTitleBarRefresh.setVisibility(View.VISIBLE);

		// Set listener for refresh button
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadListItemData();
			}
		});

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

		offlineEVoterManager = new OfflineEVoterManager(this);

		context = this;
		listView = (ListView) findViewById(R.id.lvItemData);
		// listView.setEmptyView(progressBar);
		etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				ItemDataActivity.this.adapter.getFilter().filter(s);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		loadListItemData();
	}

	/**
	 * Load data for list view
	 */
	protected abstract void loadListItemData();

}
