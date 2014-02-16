/**
 * 
 */
package evoter.mobile.activities;

import java.sql.Timestamp;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.QuestionType;
import evoter.share.utils.URIRequest;

/**
 * @author luongnv89
 */
public class NewQuestionActivity extends EVoterActivity {
	private final String YES_NO = "Yes/No";
	private final String MULTI_RADIO = "Multi Radio Button";
	private final String MULTI_CHECK = "Multi Checkbox";
	private final String SLIDER = "Slider";
	private final String INPUT_ANSWER = "Input Answer";
	private final String MATCH = "Match";
	
	protected EditText etQuestionText;
	protected EditText etAnswer;
	//	EditText etMin;
	protected EditText etMax;
	
	protected Spinner spQuestionType;
	protected LinearLayout laAnswer;
	protected LinearLayout laSlider;
	
	protected Button btSave;
	protected Button btCancel;
	protected Button btAddAnswer;
	
	protected ListView lvListAnswser;
	protected ArrayList<String> typeArray = new ArrayList<String>();
	protected ArrayList<String> listAnswser = new ArrayList<String>();
	protected ArrayAdapter<String> adaterListView;
	protected long typeID;
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initialComponent();
		
		lvListAnswser.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentView, View itemClicked, int position, long id) {
				final String itemClick = (String) parentView.getItemAtPosition(position);
				
				clickAnswerAction(itemClick);
			}
		});
		
		btAddAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (etAnswer.getText().toString().equals("")) {
					EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "The answer text is empty! Please input answer");
				} else if (listAnswser.contains(etAnswer.getText().toString())) {
					EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "The answer is already exist! Please input again");
				}
				else {
					listAnswser.add(etAnswer.getText().toString());
					adaterListView.notifyDataSetChanged();
					etAnswer.setText("");
				}
				
			}
		});
		
		
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
		spQuestionType.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parentView, View itemSelectedView, int position, long id) {
				String selected = (String) parentView.getItemAtPosition(position);
				int idItem = getIDType(selected);
				typeID = (long) idItem;
				buildAnswerArea(idItem);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setBtSaveAction();
		
	}
	
	/**
	 * 
	 */
	private void initialComponent() {
		setContentView(R.layout.new_question);
		mainMenu.setQuestionActivityMenu();
		mainMenu.getBtNewQuestion().setVisibility(View.GONE);
		
		etQuestionText = (EditText) findViewById(R.id.etNewQuestionContent);
		
		//Layout answer for multi checkbox and multi radio button
		adaterListView = new ArrayAdapter<String>(NewQuestionActivity.this, R.layout.user_item, listAnswser);
		lvListAnswser = (ListView) findViewById(R.id.lvCreaetQuestionListAnswser);
		lvListAnswser.setAdapter(adaterListView);
		etAnswer = (EditText) findViewById(R.id.etCreateQuestionAnswer);
		laAnswer = (LinearLayout) findViewById(R.id.la_answerArea);
		btAddAnswer = (Button) findViewById(R.id.btCreateQuestionAddAnswer);
		laAnswer.setVisibility(View.GONE);
		//		lvListAnswser.setVisibility(View.GONE);
		
		//Layout answer for slider question
		laSlider = (LinearLayout) findViewById(R.id.la_slider);
		//		etMin = (EditText) findViewById(R.id.etCreateQMin);
		etMax = (EditText) findViewById(R.id.etCreateQMax);
		laSlider.setVisibility(View.VISIBLE);
		
		btCancel = (Button) findViewById(R.id.btCancelNewQuestion);
		createQuestionType();
		spQuestionType = (Spinner) findViewById(R.id.spQuestionType);
		ArrayAdapter<String> adaterSpinner = new ArrayAdapter<String>(NewQuestionActivity.this, R.layout.answer_item, typeArray);
		spQuestionType.setAdapter(adaterSpinner);
		btSave = (Button) findViewById(R.id.btSaveNewQuestion);
	}

	/**
	 * 
	 */
	private void createQuestionType() {
		typeArray.add(YES_NO);
		typeArray.add(MULTI_RADIO);
		typeArray.add(MULTI_CHECK);
		typeArray.add(SLIDER);
		typeArray.add(INPUT_ANSWER);
		typeArray.add(MATCH);
	}

	/**
	 * 
	 */
	protected void setBtSaveAction() {
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createQuestionRequest();
				
			}
		});
		
	}
	
	/**
	 * @return
	 */
	protected boolean readyToCreate() {
		if (etQuestionText.getText().equals("")) {
			EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "The content of question should not empty!");
			return false;
		}
		if (typeID == QuestionType.SLIDER) {
			//			int min = Integer.parseInt(etMin.getText().toString());
			int max = Integer.parseInt(etMax.getText().toString());
			if (max < 2) {
				EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "The max value of slider should be greater than 1 !Please input again!");
				return false;
			} else {
				listAnswser.clear();
				listAnswser.add(String.valueOf(max));
			}
		}
		if (typeID == QuestionType.INPUT_ANSWER) {
			listAnswser.clear();
			listAnswser.add("Input answer");
		}
		if(typeID == QuestionType.YES_NO){
			listAnswser.clear();
			listAnswser.add("Yes");
			listAnswser.add("No");
		}
		return true;
	}
	
	/**
	 * @param selected
	 * @return
	 */
	protected int getIDType(String selected) {
		if (selected.equals(YES_NO)) return 1;
		if (selected.equals(MULTI_RADIO)) return 2;
		if (selected.equals(MULTI_CHECK)) return 3;
		if (selected.equals(SLIDER)) return 4;
		if (selected.equals(INPUT_ANSWER)) return 5;
		if (selected.equals(MATCH)) return 6;
		return -1;
	}

	/**
	 * @param itemClick
	 */
	private void clickAnswerAction(final String itemClick) {
		final DialogInfor dialog = new DialogInfor(
				NewQuestionActivity.this, "Delete answer");
		dialog.setMessageContent("Delete the answer: " + itemClick);
		dialog.getBtOK().setText("Delete");
		dialog.getBtOK().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Answer click", "edit answer" + itemClick);
				dialog.dismiss();
				listAnswser.remove(itemClick);
				adaterListView.notifyDataSetChanged();
			}
		});
		
		dialog.getBtKO().setText("Cancel");
		dialog.getBtKO().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * @param idItem
	 */
	private void buildAnswerArea(int idItem) {
		switch (idItem) {
			case 1:
				laAnswer.setVisibility(View.GONE);
				//						lvListAnswser.setVisibility(View.GONE);
				laSlider.setVisibility(View.GONE);
				break;
			case 2:
				laAnswer.setVisibility(View.VISIBLE);
				//						lvListAnswser.setVisibility(View.VISIBLE);
				laSlider.setVisibility(View.GONE);
				break;
			case 3:
				laAnswer.setVisibility(View.VISIBLE);
				//						lvListAnswser.setVisibility(View.VISIBLE);
				laSlider.setVisibility(View.GONE);
				break;
			case 4:
				laAnswer.setVisibility(View.GONE);
				//						lvListAnswser.setVisibility(View.GONE);
				laSlider.setVisibility(View.VISIBLE);
				break;
			case 5:
				laSlider.setVisibility(View.GONE);
				laAnswer.setVisibility(View.GONE);
				//						lvListAnswser.setVisibility(View.GONE);
				break;
			case 6:
				laSlider.setVisibility(View.GONE);
				laAnswer.setVisibility(View.GONE);
				//						lvListAnswser.setVisibility(View.GONE);
				EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "Not implemented yet!");
				break;
			default:
				break;
		}
	}

	/**
	 * 
	 */
	private void createQuestionRequest() {
		if (!readyToCreate()) {
			EVoterMobileUtils.showeVoterToast(NewQuestionActivity.this, "Invalid parameter. Please input again!");
		} else {
			
			// Send login request to server
			RequestParams params = new RequestParams();
			params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
			params.put(QuestionDAO.QUESTION_TEXT, new String[] { etQuestionText.getText().toString() });
			params.add(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(typeID));
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			params.add(QuestionDAO.CREATION_DATE, ts.toString());
			params.add(QuestionSessionDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSessionID()));
			params.put(AnswerDAO.ANSWER_TEXT, listAnswser);
			Log.i("List Answer: ", listAnswser.get(0));
			client.post(RequestConfig.getURL(URIRequest.CREATE_QUESTION), params,
					new AsyncHttpResponseHandler() {
						// Request successfully - client receive a response
						@Override
						public void onSuccess(String response) {
							Log.i("Response", response);
							if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
								EVoterMobileUtils.showeVoterToast(
										NewQuestionActivity.this,
										"A new question is created successfully!");
								EVoterShareMemory.getPreviousContext().loadListItemData();
							} else {
								EVoterMobileUtils.showeVoterToast(
										NewQuestionActivity.this,
										"Cannot create new question");
							}
							finish();
							
						}
						
						//Login fail
						@Override
						public void onFailure(Throwable error,
								String content) {
							EVoterMobileUtils.showeVoterToast(
									NewQuestionActivity.this,
									"Cannot request to server!");
							Log.e("create question", "onFailure error : "
									+ error.toString() + "content : "
									+ content);
							finish();
						}
					});
		}
	}
	
}
