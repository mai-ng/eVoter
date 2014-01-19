/**
 * 
 */
package evoter.server.http.request;

import evoter.server.http.request.interfaces.IAccountRequest;
import evoter.server.http.request.interfaces.IQuestionRequest;
import evoter.server.http.request.interfaces.ISessionRequest;
import evoter.server.http.request.interfaces.ISubjectRequest;

/**
 * 
 * This class produces specific request handler </br>
 * 
 * @author btdiem
 *
 */
public class RequestFactory {
	
	/**
	 * 
	 * @return {@link IAccountRequest} singleton instance </br>
	 */
	public static IAccountRequest getAccountRequest(){
		return AccountRequest.getInstance();
	}

	/**
	 * 
	 * @return {@link IQuestionRequest} singleton instance </br>
	 */
	public static IQuestionRequest getQuestionRequest(){
		return QuestionRequest.getInstance();
	}
	/**
	 * 
	 * @return {@link ISessionRequest} singleton instance </br>
	 */
	public static ISessionRequest getSessionRequest(){
		return SessionRequest.getInstance();
	}
	/**
	 * 
	 * @return {@link ISubjectRequest} singleton instance </br>
	 */
	public static ISubjectRequest getSubjectRequest(){
		return SubjectRequest.getInstance();
	}
}
