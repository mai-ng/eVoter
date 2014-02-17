package evoter.server.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.share.utils.URIRequest;

/**
 * 
 * This class is an request utility that checks request type based on URI string, </br>
 * fix encoding character of request parameters and write response to client via {@link HttpExchange} </br>
 * 
 * @author btdiem </br>
 *
 */

public class URIUtils {
	//context type key of response header
	public final static String CONTENT_TYPE = "Content-Type";
	//
//	public final static String CONTENT_ENCODING = "Content-Encoding";
	//content type value of response header
	public final static String APPLICATION_JSON = "application/json; charset=ISO-8859-1";
//	public final static String APPLICATION_TEXT = "application/text";
	

	/**
	 * 
	 * @param URIString uri request
	 * @return true if uri request ends with /login 
	 * @see URIRequest#LOGIN </br> 
	 */
	public static boolean isLoginRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.LOGIN); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true of uri string request ends with /logout</br>
	 * @see URIRequest#LOGOUT </br>
	 */
	public static boolean isLogoutRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.LOGOUT); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri request ends with /get_all_subject</br>
	 * @see URIRequest#GET_ALL_SUBJECT </br>
	 */
	public static boolean isGetAllSubjectRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.GET_ALL_SUBJECT); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri request ends with /view_subject</br>
	 * @see URIRequest#VIEW_SUBJECT </br>
	 */
	public static boolean isViewSubjectRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.VIEW_SUBJECT); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri request ends with /delete_subject</br>
	 * @see URIRequest#DELETE_SUBJECT </br>
	 */
	public static boolean isDeleteSubjectRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.DELETE_SUBJECT); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri request ends with /seach_subject</br>
	 * @see URIRequest#SEARCH_SUBJECT </br>
	 */
	public static boolean isSearchSubjectRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.SEARCH_SUBJECT); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_all_session </br>
	 * @see URIRequest#GET_ALL_SESSION </br>
	 */
	public static boolean isGetAllSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.GET_ALL_SESSION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /view_session</br>
	 * @see URIRequest#VIEW_SESSION </br>
	 */
	public static boolean isViewSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.VIEW_SESSION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with create_session request </br>
	 * @see URIRequest#CREATE_SESSION </br>
	 */
	public static boolean isCreateSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.CREATE_SESSION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /active_session</br>
	 * @see URIRequest#ACTIVE_SESSION </br>
	 */
	public static boolean isActiveSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.ACTIVE_SESSION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /inactive_session </br>
	 * @see URIRequest#INACTIVE_SESSION </br>
	 */
	public static boolean isInActiveSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.INACTIVE_SESSION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /delete_session</br>
	 * @see URIRequest#DELETE_SESSION </br>
	 */
	public static boolean isDeleteSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.DELETE_SESSION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /update_session</br>
	 * @see URIRequest#UPDATE_SESSION </br>
	 */
	public static boolean isUpdateSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.UPDATE_SESSION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri strings ends with /accept_session</br>
	 * @see URIRequest#ACCEPT_SESSION </br>
	 */
	public static boolean isAcceptSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.ACCEPT_SESSION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /seach_session</br>
	 * @see URIRequest#SEARCH_SESSION </br>
	 */
	public static boolean isSearchSessionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.SEARCH_SESSION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_all_session</br>
	 * @see URIRequest#GET_ALL_SESSION </br>
	 */
	public static boolean isGetAllQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.GET_ALL_QUESTION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /view_question</br>
	 * @see URIRequest#VIEW_QUESTION </br>
	 */
	public static boolean isViewQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.VIEW_QUESTION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /create_question</br>
	 * @see URIRequest#CREATE_QUESTION </br>
	 */
	public static boolean isCreateQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.CREATE_QUESTION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /delete_question </br>
	 * @see URIRequest#DELETE_QUESTION </br>
	 */
	public static boolean isDeleteQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.DELETE_QUESTION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /update_question
	 * @see URIRequest#UPDATE_QUESTION </br>
	 */
	public static boolean isUpdateQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.UPDATE_QUESTION); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string ends with /send_question
	 * @see URIRequest#SEND_QUESTION </br>
	 */
	public static boolean isSendQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.SEND_QUESTION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string ends with /get_question </br>
	 * @see URIRequest#GET_QUESTION </br>
	 */
	public static boolean isGetQuestionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.GET_QUESTION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /stop_send_question </br>
	 * @see URIRequest#STOP_SEND_QUESTION </br>
	 */
	public static boolean isStopSendQuestionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.STOP_SEND_QUESTION); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string ends with /seach_question </br>
	 * @see URIRequest#SEARCH_QUESTION </br>
	 */
	public static boolean isSearchQuestionRequest(String URIString){
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.
						getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.SEARCH_QUESTION); 
	}
	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string ends with /get_latest_question
	 * @see URIRequest#GET_LATEST_QUESTION </br>
	 */
	public static boolean isGetLatestQuestionRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.GET_LATEST_QUESTION); 
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_statistics </br>
	 * @see URIRequest#GET_STATISTICS </br>
	 */
	public static boolean isGetStatisticsRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.GET_STATISTICS); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_all_user </br>
	 * @see URIRequest#GET_ALL_USER </br>
	 */
	public static boolean isGetAllUserRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.GET_ALL_USER); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /create_user </br>
	 * @see URIRequest#CREATE_USER </br>
	 */
	public static boolean isCreateUserRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.CREATE_USER); 
	}	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /edit_user </br>
	 * @see URIRequest#EDIT_USER </br>
	 */
	public static boolean isEditUserRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.EDIT_USER); 
	}	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /delete_user </br>
	 * @see URIRequest#DELETE_USER</br>
	 */

	public static boolean isDeleteUserRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.DELETE_USER); 
	}	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /change_approve_user </br>
	 * @see URIRequest#CHANGE_APPROVE_USER </br>
	 */
	public static boolean isChangeApproveUserRequest(String URIString){
		
		return (URIString != null) 
			&& URIString.contains(((HttpConnection)BeanDAOFactory.
					getBean("httpConnection")).getContext()) 
			&& URIString.endsWith(URIRequest.CHANGE_APPROVE_USER); 
	}	
	/**
	 * Read and parse the string value from the request body into pairs and put them to {@link Map}.</br>

	 * If client put a list or an array to request body like String[] colors = { "blue", "yellow" } </br>
	 * , server will receive an url parameter like : "colors[]=blue;colors[]=yellow" </br>
	 * this method will convert these to an array. Finally, the method will returns a map that </br>
	 * key is colors, value is String[]{"blue","yellow"} as example above </br>  
	 * 
	 * @param exchange {@link HttpExchange} communicates between clients and server </br>
	 * @return {@link Map} that contains parameter key and parameter values </br> 
	 */
	public static Map<String, Object> getParameters(HttpExchange exchange) {
		
		Map<String,Object> parameters = new HashMap<String, Object>();
		
		try {
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			String parameterMessage = writer.toString();
			StringTokenizer parameterValueToken = new StringTokenizer(parameterMessage,"&");
			
			while(parameterValueToken.hasMoreTokens()) {
				
				 String parameterValue = parameterValueToken.nextToken();
				
				 StringTokenizer value = new StringTokenizer(parameterValue,"=");
				 String tokenKey = value.nextToken();
				 String tokenValue = value.nextToken(); 
				 
				 boolean isArray = isArray(tokenKey);
				 tokenKey = fixedEncodeKeyParameter(tokenKey);
				 tokenValue= fixedEncodeValueParameter(tokenValue);
				 
				 if (parameters.containsKey(tokenKey)){
					 //from the second time
					 String[] temp;
					 if (parameters.get(tokenKey) instanceof String[]){
						 temp = (String[])parameters.get(tokenKey);
					 }else{
						 //from the first time
						 temp = new String[]{(String)parameters.get(tokenKey)};
					 }
					 List<String> list = new ArrayList<String>();
					 list.addAll(Arrays.asList(temp));
					 list.add(tokenValue);
					 parameters.put(tokenKey, list.toArray(new String[]{}));

				 }else{
					 if (isArray)
						 parameters.put(tokenKey, new String[]{tokenValue});
					 else
						 parameters.put(tokenKey, tokenValue);
					 
				 }
				 
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return parameters;
		
	}
	
	/**
	 * 
	 * @param key key value of parameter element </br>
	 * @return true if key contains [] </br>
	 */
	public static boolean isArray(String key){
		return key.contains("%5B%5D");
	}
	/**
	 * 
	 * @param key encoding character string</br>
	 * @return a string without encoding characters </br>
	 */
	public static String fixedEncodeKeyParameter(String key){
		return 	key
				.replace("+", "")
				.replace("%40", "")
				.replace("%5B", "")
				.replace("%5D", "")
				.replace("%3A", "")
				.replace("%3F", "");
	}
	/**
	 * 
	 * @param value encoding character string </br>
	 * @return a string that its encoding character is replace by decoding character </br>
	 */
	public static String fixedEncodeValueParameter(String value){
		return 	value
				.replace("%40", "@")
				.replace("%5B", "[")
				.replace("%5D", "]")
				.replace("%3A", ":")
				.replace("+", " ")
				.replace("%3F", "?");
	}
	/**
	 * 
	 * @param response is written to client via {@link HttpExchange} </br>
	 * @param t {@link HttpExchange} communicates between clients and server </br>
	 */
	public static void writeResponse(Object response, HttpExchange t) {
		
		byte[] responseBytes = response.toString().getBytes();
		
		
		try {

   		    OutputStream os = t.getResponseBody();   		    
   		    
   		    Headers responseHeaders = t.getResponseHeaders();
		    responseHeaders.set(CONTENT_TYPE, APPLICATION_JSON);
		    responseHeaders.set("Content-Encoding", "gzip");
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    GZIPOutputStream gos = new GZIPOutputStream(bos);
		    gos.write(responseBytes);
		    gos.close();
		    
		    
		    int len = bos.size();
		    t.sendResponseHeaders(200, len);
		    
	        os.write(bos.toByteArray());
			   		    
			try {				
			    os.flush();
                os.close();
			} catch (Exception e) {

			}
		} catch (Exception e) {

			e.printStackTrace();

		} finally {


		}
	}
	/**
	public static void writeSuccessResponse(HttpExchange httpExchange){
		
		URIUtils.writeResponse(URIRequest.SUCCESS_MESSAGE, httpExchange);
	}
	
	public static void writeFailureResponse(HttpExchange httpExchange){
		
		URIUtils.writeResponse(URIRequest.FAILURE_MESSAGE, httpExchange);
	}*/
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /reset_password </br>
	 * @see URIRequest#RESET_PASSWORD </br>
	 */
	public static boolean isResetPassword(String URIString) {
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.RESET_PASSWORD); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /register </br>
	 * @see URIRequest#REGISTER </br>
	 */
	
	public static boolean isRegister(String URIString) {
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.REGISTER); 
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_all_users_of_subject </br>
	 * @see URIRequest#GET_ALL_USERS_OF_SUBJECT </br>
	 */
	public static boolean isGetAllUserOfSubject(String URIString) {
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.GET_ALL_USERS_OF_SUBJECT); 
	}
	

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /update_subject </br>
	 * @see URIRequest#UPDATE_SUBJECT </br>
	 */
	public static boolean isUpdateSubject(String URIString){
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.UPDATE_SUBJECT); 
		
	}
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /get_all_student </br>
	 * @see URIRequest#GET_ALL_STUDENT </br>
	 */
	public static boolean isGetAllStudentOfSession(String URIString){
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.GET_ALL_STUDENT); 
		
	}	
	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /vote_answer </br>
	 * @see URIRequest#VOTE_ANSWER </br>
	 */
	public static boolean isVoteAnswer(String URIString){
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.VOTE_ANSWER); 
		
	}

	/**
	 * 
	 * @param URIString uri request </br>
	 * @return true if uri string request ends with /create_subject </br>
	 * @see URIRequest#CREATE_SUBJECT </br>
	 */
	public static boolean isCreateSubject(String URIString) {
		
		return (URIString != null) 
				&& URIString.contains(((HttpConnection)BeanDAOFactory.getBean("httpConnection")).getContext()) 
				&& URIString.endsWith(URIRequest.CREATE_SUBJECT);
	}		
	
	
}
