package evoter.server.http;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.IQuestionService;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.server.http.request.interfaces.ISubjectService;


public class ServerHandler implements HttpHandler {

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
			
			IAccountService accountRequest = (IAccountService)BeanDAOFactory.getBean(IAccountService.BEAN_NAME);
			ISubjectService subjectRequest = (ISubjectService)BeanDAOFactory.getBean(ISubjectService.BEAN_NAME);
			ISessionService sessionRequest = (ISessionService)BeanDAOFactory.getBean(ISessionService.BEAN_NAME);
			IQuestionService questionRequest = (IQuestionService)BeanDAOFactory.getBean(IQuestionService.BEAN_NAME);
			
			if (URIUtils.isLoginRequest(uri)){
				accountRequest.doLogin(httpExchange, parameters);
			}else if (URIUtils.isLogoutRequest(uri)){
				accountRequest.doLogout(httpExchange, parameters);
			}else if (URIUtils.isResetPassword(uri)){
				accountRequest.doResetPassword(httpExchange, parameters);
			}else if (URIUtils.isRegister(uri)){
				accountRequest.doRegister(httpExchange, parameters);
			}
			
			
			else{
				//verify the user key 1st
				if (accountRequest.hasUserKey(parameters)){
					System.out.println("has userKey");
					if (URIUtils.isViewSubjectRequest(uri)){
						subjectRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isGetAllSubjectRequest(uri)){
						subjectRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isDeleteSubjectRequest(uri)){
						subjectRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSearchSubjectRequest(uri)){
						subjectRequest.doSearch(httpExchange, parameters);
						
						//start with session management part
					}else if (URIUtils.isGetAllSessionRequest(uri)){
						sessionRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewSessionRequest(uri)){
						sessionRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						sessionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						sessionRequest.doActive(httpExchange, parameters);
					}else if (URIUtils.isAcceptSessionRequest(uri)){
						sessionRequest.doAccept(httpExchange, parameters);
					}else if (URIUtils.isDeleteSessionRequest(uri)){
						sessionRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						sessionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						sessionRequest.doActive(httpExchange, parameters);
					}else if (URIUtils.isInActiveSessionRequest(uri)){
						sessionRequest.doInActive(httpExchange, parameters);
					}else if (URIUtils.isUpdateSessionRequest(uri)){
						sessionRequest.doUpdate(httpExchange, parameters);
						
						//start with question management part
					}else if (URIUtils.isGetAllQuestionRequest(uri)){
						questionRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewQuestionRequest(uri)){
						questionRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateQuestionRequest(uri)){
						questionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isDeleteQuestionRequest(uri)){
						questionRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSendQuestionRequest(uri)){
						questionRequest.doSend(httpExchange, parameters);
					}else if (URIUtils.isGetLatestQuestionRequest(uri)){
						questionRequest.doGetLatest(httpExchange, parameters);
					}else if (URIUtils.isStopSendQuestionRequest(uri)){
						questionRequest.doStopSend(httpExchange, parameters);
					}
				}

				
			}

		}
		
		
	}

}
