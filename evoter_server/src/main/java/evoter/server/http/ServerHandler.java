package evoter.server.http;

import java.io.IOException;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.IAnswerService;
import evoter.server.http.request.interfaces.IQuestionService;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.server.http.request.interfaces.ISubjectService;
import evoter.server.http.request.interfaces.IUserService;
import evoter.share.dao.UserDAO;
/**
 * 
 * This class is an extension of {@link HttpHandler} to manage {@link HttpRequest} </br>
 * It receives all requests sent to {@link Server} and forward requests to correct services </br>
 * After service classes finish processing request, a response is returned and it is written back
 * client request </br>
 * @author btdiem </br>
 *
 */
@Service
public class ServerHandler implements HttpHandler {
	
	static Logger log = Logger.getLogger(ServerHandler.class);
	/**
	 * All coming requests will handled by this method </br>
	 * Each request is processed by a thread {@link ThreadHandler} </br>
	 */
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		log.info("Request is comming..." + httpExchange.getRequestURI());
		new Thread(new ThreadHandler(httpExchange)).start();
		
	}
	/**
	 * This class will get parameter map from {@link HttpExchange} </br>
	 * and request uri and then forward this request to services </br>
	 * All requests has been checked {@link UserDAO#USER_KEY} to guarantee the security </br>
	 * except login, logout, reset password and register account requests </br>
	 * @author btdiem</br>
	 *
	 */
	class ThreadHandler implements Runnable{

		private HttpExchange httpExchange;
		
		public ThreadHandler(HttpExchange httpExchange){
			this.httpExchange = httpExchange;
		}
		@Override
		public void run() {
		
			String uri = httpExchange.getRequestURI().toString();
			Map<String,Object> parameters = URIUtils.getParameters(httpExchange);			
			log.debug("parameters: " + parameters);
			Object response = null;
			
			IAccountService accountService = (IAccountService)BeanDAOFactory.getBean(IAccountService.BEAN_NAME);
			ISubjectService subjectService = (ISubjectService)BeanDAOFactory.getBean(ISubjectService.BEAN_NAME);
			ISessionService sessionService = (ISessionService)BeanDAOFactory.getBean(ISessionService.BEAN_NAME);
			IQuestionService questionService = (IQuestionService)BeanDAOFactory.getBean(IQuestionService.BEAN_NAME);
			IUserService userService = (IUserService)BeanDAOFactory.getBean(IUserService.BEAN_NAME);
			IAnswerService answerService = (IAnswerService)BeanDAOFactory.getBean(IAnswerService.BEAN_NAME);
			
			/**
			 * IAccountService will work with all requests 
			 * that involves account management part
			 * 
			 */	
			if (URIUtils.isLoginRequest(uri)){
				response = accountService.doLogin(parameters);
			}else if (URIUtils.isLogoutRequest(uri)){
				response = accountService.doLogout(parameters);
			}else if (URIUtils.isResetPassword(uri)){
				response = accountService.doResetPassword(parameters);
			}else if (URIUtils.isRegister(uri)){
				response = accountService.doRegister(parameters);
			}
			
			
			else{
				/**
				 * verify the user key 1st whether user is logging successfully
				 */
				if (accountService.hasUserKey(
						(String)parameters.get(UserDAO.USER_KEY))){
					
					log.debug("has userKey" + parameters.get(UserDAO.USER_KEY) );
					/**
					 * ISubjectService will work with all requests 
					 * that involves subject management part
					 * 
					 */
					if (URIUtils.isViewSubjectRequest(uri)){
						response = subjectService.doView(parameters);
					}else if (URIUtils.isGetAllSubjectRequest(uri)){
						response = subjectService.doGetAll(parameters);
					}else if (URIUtils.isDeleteSubjectRequest(uri)){
						response = subjectService.doDelete(parameters);
					}else if (URIUtils.isSearchSubjectRequest(uri)){
						response = subjectService.doSearch(parameters);
					}else if (URIUtils.isGetAllUserOfSubject(uri)){
						response = subjectService.doGetUsersOfSubject(parameters);
					}else if (URIUtils.isUpdateSubject(uri)){
						response = subjectService.doEdit(parameters);
					}else if (URIUtils.isCreateSubject(uri)){
						response = subjectService.doCreate(parameters);
						
					/**
					 * ISessionService will work with all requests 
					 * that involves session management part
					 * 
					 */	
					}else if (URIUtils.isGetAllSessionRequest(uri)){
						response = sessionService.doGetAll(parameters);
					}else if (URIUtils.isViewSessionRequest(uri)){
						response = sessionService.doView(parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						response = sessionService.doCreate(parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						response = sessionService.doActive(parameters);
					}else if (URIUtils.isAcceptSessionRequest(uri)){
						response = sessionService.doAccept(parameters);
					}else if (URIUtils.isDeleteSessionRequest(uri)){
						response = sessionService.doDelete(parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						response = sessionService.doCreate(parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						response = sessionService.doActive(parameters);
					}else if (URIUtils.isInActiveSessionRequest(uri)){
						response = sessionService.doInActive(parameters);
					}else if (URIUtils.isUpdateSessionRequest(uri)){
						response = sessionService.doUpdate(parameters);
					}else if (URIUtils.isGetAllStudentOfSession(uri)){
						response = sessionService.doGetStudentsOfSession(parameters);
							
					/**
					 * IQuestionService will work with all requests 
					 * that involves question management part
					 * 
					 */	
					}else if (URIUtils.isGetAllQuestionRequest(uri)){
						response = questionService.doGetAll(parameters);
					}else if (URIUtils.isViewQuestionRequest(uri)){
						response = questionService.doView(parameters);
					}else if (URIUtils.isCreateQuestionRequest(uri)){
						response = questionService.doCreate(parameters);
					}else if (URIUtils.isDeleteQuestionRequest(uri)){
						response = questionService.doDelete(parameters);
					}else if (URIUtils.isSendQuestionRequest(uri)){
						response = questionService.doSend(parameters);
					}else if (URIUtils.isGetLatestQuestionRequest(uri)){
						response = questionService.doGetLatest(parameters);
					}else if (URIUtils.isStopSendQuestionRequest(uri)){
						response = questionService.doStopSend(parameters);
					}else if(URIUtils.isUpdateQuestionRequest(uri)){
						response = questionService.doEdit(parameters);
					
					
					/**
					 * IUserService will work with all requests 
					 * that involves user management part
					 * 
					 */	
					}else if (URIUtils.isGetAllUserRequest(uri)){
						response = userService.doGetAll(parameters);
					}else if (URIUtils.isCreateUserRequest(uri)){
						response = userService.doCreate(parameters);
					}else if (URIUtils.isEditUserRequest(uri)){
						response = userService.doEdit(parameters);
					}else if (URIUtils.isDeleteUserRequest(uri)){
						response = userService.doDelete(parameters);
					}else if (URIUtils.isChangeApproveUserRequest(uri)){
						response = userService.doChangeApprove(parameters);
					}
					
					/**
					 * IAnswerService will work with all requests 
					 * that involves question statistics management part
					 * 
					 */	
					else if (URIUtils.isVoteAnswer(uri)){
						response = answerService.doVote(parameters);
					}else if (URIUtils.isGetStatisticsRequest(uri)){
						response = answerService.doGetStatistics(parameters);
					}
				}

				
			}//else if
			log.debug("response:" + response);
			if (response != null){
				URIUtils.writeResponse(response, httpExchange);
			}
			

		}
		
		
	}

}
