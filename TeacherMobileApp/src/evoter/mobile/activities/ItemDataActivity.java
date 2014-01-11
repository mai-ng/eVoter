/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import evoter.mobile.adapters.ItemDataAdapter;
import evoter.mobile.main.R;
import evoter.mobile.models.ItemData;
import evoter.mobile.objects.OfflineEVoterManager;

/**
 * @author luongnv89
 * 
 */
public abstract class ItemDataActivity extends EVoterActivity {
	protected ListView listView;
	protected ArrayList<ItemData> listInitial;
	protected ItemDataAdapter adapter;
	protected Context context;
	protected EditText etSearch;
	protected OfflineEVoterManager offlineEVoterManager;

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

		offlineEVoterManager = new OfflineEVoterManager(this);

		context = this;
		listInitial = new ArrayList<ItemData>();
		listView = (ListView) findViewById(R.id.lvItemData);
		loadListItemData();
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
	}

	/**
	 * Load data for list view
	 */
	protected abstract void loadListItemData();
}
