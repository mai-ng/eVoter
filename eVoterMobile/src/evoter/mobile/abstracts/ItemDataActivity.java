/**
 * 
 */
package evoter.mobile.abstracts;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableLayout;
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
	
	/**
	 * Seekbar for student vote the difficult level of session
	 */
	protected SeekBar sbDifficult;
	
	/**
	 * Seekbar for student vote the excited level of session
	 */
	protected SeekBar sbExcited;
	
	/**
	 * TableLayout content {@link ItemDataActivity#sbDifficult} and {@link ItemDataActivity#sbExcited}
	 */
	protected TableLayout tbSessionValue;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item_data_view);
		initComponent();
	}
	
	/**
	 * Init component for an {@link ItemDataActivity}
	 */
	public void initComponent() {
		tbSessionValue=(TableLayout)findViewById(R.id.tbSessionValue);
		sbExcited = (SeekBar) findViewById(R.id.sbBored);
		sbDifficult = (SeekBar) findViewById(R.id.sbDifficult);
		listView = (ListView) findViewById(R.id.lvItemData);
		etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				ItemDataActivity.this.adapter.getFilter().filter(s);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
		});
		tbSessionValue.setVisibility(View.GONE);
	}
	
}
