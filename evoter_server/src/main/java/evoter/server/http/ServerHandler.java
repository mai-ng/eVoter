package evoter.server.http;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

//import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.IQuestionService;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.server.http.request.interfaces.ISubjectService;

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
/**			
			IAccountService accountService = (IAccountService)BeanDAOFactory.getBean(IAccountService.BEAN_NAME);
			ISubjectService subjectService = (ISubjectService)BeanDAOFactory.getBean(ISubjectService.BEAN_NAME);
			ISessionService sessionService = (ISessionService)BeanDAOFactory.getBean(ISessionService.BEAN_NAME);
			IQuestionService questionService = (IQuestionService)BeanDAOFactory.getBean(IQuestionService.BEAN_NAME);
*/			
			if (URIUtils.isLoginRequest(uri)){
				accountService.doLogin(httpExchange, parameters);
			}else if (URIUtils.isLogoutRequest(uri)){
				accountService.doLogout(httpExchange, parameters);
			}else if (URIUtils.isResetPassword(uri)){
				accountService.doResetPassword(httpExchange, parameters);
			}else if (URIUtils.isRegister(uri)){
				accountService.doRegister(httpExchange, parameters);
			}
			
			
			else{
				//verify the user key 1st
				if (accountService.hasUserKey(parameters)){
					System.out.println("has userKey");
					if (URIUtils.isViewSubjectRequest(uri)){
						subjectService.doView(httpExchange, parameters);
					}else if (URIUtils.isGetAllSubjectRequest(uri)){
						subjectService.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isDeleteSubjectRequest(uri)){
						subjectService.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSearchSubjectRequest(uri)){
						subjectService.doSearch(httpExchange, parameters);
					}else if (URIUtils.isGetAllUserOfSubject(uri)){
						subjectService.doGetUsersOfSubject(httpExchange, parameters);
					}else if (URIUtils.isUpdateSubject(uri)){
						subjectService.doEdit(httpExchange, parameters);
						
						
						//start with session management part
					}else if (URIUtils.isGetAllSessionRequest(uri)){
						sessionService.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewSessionRequest(uri)){
						sessionService.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						sessionService.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						sessionService.doActive(httpExchange, parameters);
					}else if (URIUtils.isAcceptSessionRequest(uri)){
						sessionService.doAccept(httpExchange, parameters);
					}else if (URIUtils.isDeleteSessionRequest(uri)){
						sessionService.doDelete(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						sessionService.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						sessionService.doActive(httpExchange, parameters);
					}else if (URIUtils.isInActiveSessionRequest(uri)){
						sessionService.doInActive(httpExchange, parameters);
					}else if (URIUtils.isUpdateSessionRequest(uri)){
						sessionService.doUpdate(httpExchange, parameters);
						
						//start with question management part
					}else if (URIUtils.isGetAllQuestionRequest(uri)){
						questionService.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewQuestionRequest(uri)){
						questionService.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateQuestionRequest(uri)){
						questionService.doCreate(httpExchange, parameters);
					}else if (URIUtils.isDeleteQuestionRequest(uri)){
						questionService.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSendQuestionRequest(uri)){
						questionService.doSend(httpExchange, parameters);
					}else if (URIUtils.isGetLatestQuestionRequest(uri)){
						questionService.doGetLatest(httpExchange, parameters);
					}else if (URIUtils.isStopSendQuestionRequest(uri)){
						questionService.doStopSend(httpExchange, parameters);
					}
				}

				
			}

		}
		
		
	}

}
