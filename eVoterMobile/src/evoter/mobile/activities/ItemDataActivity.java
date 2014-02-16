/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import evoter.mobile.adapters.ItemDataAdapter;
import evoter.mobile.main.R;

/**
 * {@link ItemDataActivity} is the abstract class of all the item activity of
 * eVoterMobile such as: subject, session, quesiton
 * This is the activity manages view - interaction,.. with list of items.
 * 
 * @author luongnv89
 */
public abstract class ItemDataActivity extends EVoterActivity {
	/**
	 * ListView of all items
	 */
	protected ListView listView;
	/**
	 * Adapter for listview
	 */
	protected ItemDataAdapter adapter;

	/**
	 * Each activity will have an edit text to search item by name
	 */
	protected EditText etSearch;
	
	protected SeekBar sbDifficult;
	
	protected SeekBar sbBored;
	
	protected TableLayout tbSessionValue;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item_data_view);
		
		this.ivTitleBarRefresh.setVisibility(View.VISIBLE);
		
		// When the refresh icon is click, the data of listview will be reloaded
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadListItemData();
			}
		});
		
		tbSessionValue=(TableLayout)findViewById(R.id.tbSessionValue);
		sbBored = (SeekBar) findViewById(R.id.sbBored);
		sbDifficult = (SeekBar) findViewById(R.id.sbDifficult);
		tbSessionValue.setVisibility(View.GONE);
//		offlineEVoterManager = new OfflineEVoterManager(this);
		
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
	 * Load data for list view.
	 * Request data from server according to context of activity.
	 */
	protected abstract void loadListItemData();
	
}
