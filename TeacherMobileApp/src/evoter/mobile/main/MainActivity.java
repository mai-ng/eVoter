package evoter.mobile.main;
//package csc7326.main;
//
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import android.net.http.AndroidHttpClient;
//import android.os.Bundle;
//import android.app.Activity;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import evoter.client.teacher.R;
//import evoter.client.teacher.R.id;
//import evoter.client.teacher.R.layout;
//import evoter.client.teacher.R.menu;
//
//public class MainActivity extends Activity {
//
//	//for testing only
//	String _urlLogin = "http://157.159.100.211:1000/evoter/login" ;
//	String _urlViewSubject = "http://192.168.0.14:1000/evoter/view_subject" ;
//	String _urlGetAllSubject = "http://157.159.100.211:1000/evoter/get_all_subject" ;
//	String _urlDeleteSubject = "http://192.168.0.14:1000/evoter/delete_subject";
//	String _urlSearchSubject = "http://192.168.0.14:1000/evoter/search_subject";
//	String _urlGetAllSession = "http://192.168.0.14:1000/evoter/get_all_session";
//	String _urlViewSession = "http://192.168.0.14:1000/evoter/view_session";
//	String _urlActiveSession = "http://192.168.0.14:1000/evoter/active_session";
//	String _urlAcceptSession = "http://192.168.0.14:1000/evoter/accept_session";
//	String _urlDeleteSession = "http://192.168.0.14:1000/evoter/delete_session";
//	String _urlGetAllQuestion = "http://192.168.0.14:1000/evoter/get_all_question";
//	String _urlViewQuestion = "http://192.168.0.14:1000/evoter/view_question";
//	String _urlDeleteQuestion = "http://192.168.0.14:1000/evoter/delete_question";
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		final TextView tv = (TextView)findViewById(R.id.tvMsg);
//		final Button button = (Button) findViewById(R.id.button_id);
//		
//        button.setOnClickListener(new View.OnClickListener() {
//            
//
//			public void onClick(View v) {
//        		//send a request to server
//            	//should read from property file: port, conte
//        		AsyncHttpClient client = new AsyncHttpClient(1000);
//        		
//        		System.out.println(client.getHttpContext());
//        		System.out.println(client.toString());
//        		tv.setText("Context: " + client.getHttpContext()+"\nClient: " + client.toString());
//        		
//        		
//        		// test login request
//        		/*RequestParams params = new RequestParams();
//        		params.add("username", "nvluong");
//        		params.add("password", "12345678");
//        		client.post(_urlLogin, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	tv.setText(response);
//                    	Log.i("LoginTest" , "response : " + response);
//                    	if (response.equalsIgnoreCase("TRUE")){
//                    		tv.setText("Sucess!" + response);
//                    		Log.i("LoginTest" , "SUCCESS");
//                    	}else{
//                    		tv.setText("FAILURE!" + response);
//                    		Log.i("LoginTest" , "FAILURE");
//                    	}
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						tv.setText(error.toString()+"\nContext: "+content);
//						Log.e("LoginTest" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });*/
//    		
//        		
//        		//test for /view_subject request
///**        		RequestParams params = new RequestParams();
//        		params.add("ID", "1");
//        		client.post(_urlViewSubject, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("View Subject Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("View Subject Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });
//*/        		
//        		//test for /get_all_subject request
//    		RequestParams params = new RequestParams();
//        		params.add("USER_ID", "1");
//        		client.post(_urlGetAllSubject, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Get All Subject Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Get All Subject Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                }); 
//        		
//        		//test for /delete_subject request
///**        		RequestParams params = new RequestParams();
//        		params.add("ID", "4");
//        		client.post(_urlDeleteSubject, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Delete Subject Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Delete Subject Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                }); */
//        		
//        		//test for /search_subject
///**        		RequestParams params = new RequestParams();
//        		params.add("ID", "1");
//        		params.add("TITLE", "Object Oriented Programming");
//        		client.post(_urlSearchSubject, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Search Subject Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Search Subject Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                }); */
//        		//test /get_all_session request
///**        		
//        		//test /view_session request
///**        		RequestParams params = new RequestParams();
//        		params.add("ID", "1");
//        		client.post(_urlViewSession, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("View Session Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("View Session Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });   */
//        		//test /active_session request
///**        		RequestParams params = new RequestParams();
//        		params.add("ID", "1");
//        		client.post(_urlActiveSession, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Active Session Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Active Session Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });  */
//        		//test /accept_session request
///**        		RequestParams params = new RequestParams();
//        		params.add("SESSION_ID", "1");
//        		params.add("USER_ID", "1");
//        		client.post(_urlAcceptSession, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Accept Session Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Accept Session Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                }); */
//        		//test /delete_session request
///**        		RequestParams params = new RequestParams();
//        		params.add("SESSION_ID", "1");
//        		params.add("USER_ID", "1");
//        		client.post(_urlDeleteSession, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Delete Session Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Delete Session Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });  */     		
//        		//test /get_all_question request
///**        		RequestParams params = new RequestParams();
//        		params.add("SESSION_ID", "1");
//        		client.post(_urlGetAllQuestion, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("Get All Question Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						Log.e("Get All Question Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                }); */
//        		//test /view_question request
//       		/**RequestParams params = new RequestParams();
//        		params.add("ID", "1");
//        		client.post(_urlViewQuestion, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	
//                    	Log.i("View Question Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						tv.setText(error.toString()+". "+content);
//						Log.e("View Question Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });  */
//        		//test /delete_question request
//        		/*RequestParams params = new RequestParams();
//        		params.add("ID", "4");
//        		client.post(_urlDeleteQuestion, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String response) {
//                    	tv.setText("Delete Question Test. Response : " + response);
//                    	Log.i("Delete Question Test" , "response : " + response);
//                    }
//                    
//					@Override
//					public void onFailure(Throwable error, String content)
//					{
//						tv.setText("Delete Question Test. onFailure error : " + error.toString() + "content : " + content);
//						Log.e("Delete Question Test" , "onFailure error : " + error.toString() + "content : " + content);
//					}
//                });    */      		
//        		
//
//        		
//            }// onClick event
//            
//            
//        });
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//}
