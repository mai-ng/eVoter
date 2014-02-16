package evoter.server.http;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import evoter.server.dao.impl.BeanDAOFactory;
//import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.IAnswerService;
import evoter.server.http.request.interfaces.IQuestionService;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.server.http.request.interfaces.ISubjectService;
import evoter.server.http.request.interfaces.IUserService;
import evoter.share.dao.UserDAO;

@Service
public class ServerHandler implements HttpHandler {
	
	@Autowired
	IAccountService accountService; 
	@Autowired
	IQuestionService questionService;
	@Autowired
	ISubjectService subjectService;
	@Autowired
	ISessionService sessionService;
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		System.out.println("Request is comming..." + httpExchange.getRequestURI());
		new Thread(new ThreadHandler(httpExchange)).start();
		
	}
	
	class ThreadHandler implements Runnable{

		private HttpExchange httpExchange;
		
		public ThreadHandler(HttpExchange httpExchange){
			this.httpExchange = httpExchange;
		}
//		@SuppressWarnings("unchecked")
		@Override
		public void run() {
		
			String uri = httpExchange.getRequestURI().toString();
			Map<String,Object> parameters = URIUtils.getParameters(httpExchange);			
			System.out.println("parameters: " + parameters);
			Object response = null;
			
			IAccountService accountService = (IAccountService)BeanDAOFactory.getBean(IAccountService.BEAN_NAME);
			ISubjectService subjectService = (ISubjectService)BeanDAOFactory.getBean(ISubjectService.BEAN_NAME);
			ISessionService sessionService = (ISessionService)BeanDAOFactory.getBean(ISessionService.BEAN_NAME);
			IQuestionService questionService = (IQuestionService)BeanDAOFactory.getBean(IQuestionService.BEAN_NAME);
			IUserService userService = (IUserService)BeanDAOFactory.getBean(IUserService.BEAN_NAME);
			IAnswerService answerService = (IAnswerService)BeanDAOFactory.getBean(IAnswerService.BEAN_NAME);
			
			System.out.println("accountService: " + accountService);
			System.out.println("subjectService: " + subjectService);
			System.out.println("sessionService: " + sessionService);
			System.out.println("questionService: " + questionService);

			
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
				//verify the user key 1st
				if (accountService.hasUserKey(
						(String)parameters.get(UserDAO.USER_KEY))){
					
					System.out.println("has userKey" + parameters.get(UserDAO.USER_KEY) );
					//subject management
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
						
						//start with session management part
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
							
						//start with question management part
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
					
					
					//user management
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
					
					//statistic management
					else if (URIUtils.isVoteAnswer(uri)){
						response = answerService.doVote(parameters);
					}else if (URIUtils.isGetStatisticsRequest(uri)){
						response = answerService.doGetStatistics(parameters);
					}
				}

				
			}//else if
			if (response != null){
				URIUtils.writeResponse(response, httpExchange);
			}
			

		}
		
		
	}

}
