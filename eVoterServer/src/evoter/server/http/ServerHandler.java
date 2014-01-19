package evoter.server.http;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import evoter.server.http.request.QuestionRequest;
import evoter.server.http.request.SessionRequest;
import evoter.server.http.request.SubjectRequest;
import evoter.server.http.request.AccountRequest;


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
			
			if (URIUtils.isLoginRequest(uri)){
				AccountRequest.doLogin(httpExchange, parameters);
			}else if (URIUtils.isLogoutRequest(uri)){
				AccountRequest.doLogout(httpExchange, parameters);
			}else if (URIUtils.isResetPassword(uri)){
				AccountRequest.doResetPassword(httpExchange, parameters);
			}else if (URIUtils.isRegister(uri)){
				AccountRequest.doRegister(httpExchange, parameters);
			}
			
			
			else{
				//verify the user key 1st
				if (AccountRequest.hasUserKey(parameters)){
					System.out.println("has userKey");
					if (URIUtils.isViewSubjectRequest(uri)){
						SubjectRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isGetAllSubjectRequest(uri)){
						SubjectRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isDeleteSubjectRequest(uri)){
						SubjectRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSearchSubjectRequest(uri)){
						SubjectRequest.doSearch(httpExchange, parameters);
						
						//start with session management part
					}else if (URIUtils.isGetAllSessionRequest(uri)){
						SessionRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewSessionRequest(uri)){
						SessionRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						SessionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						SessionRequest.doActive(httpExchange, parameters);
					}else if (URIUtils.isAcceptSessionRequest(uri)){
						SessionRequest.doAccept(httpExchange, parameters);
					}else if (URIUtils.isDeleteSessionRequest(uri)){
						SessionRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isCreateSessionRequest(uri)){
						SessionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isActiveSessionRequest(uri)){
						SessionRequest.doActive(httpExchange, parameters);
					}else if (URIUtils.isInActiveSessionRequest(uri)){
						SessionRequest.doInActive(httpExchange, parameters);
					}else if (URIUtils.isUpdateSessionRequest(uri)){
						SessionRequest.doUpdate(httpExchange, parameters);
						
						//start with question management part
					}else if (URIUtils.isGetAllQuestionRequest(uri)){
						QuestionRequest.doGetAll(httpExchange, parameters);
					}else if (URIUtils.isViewQuestionRequest(uri)){
						QuestionRequest.doView(httpExchange, parameters);
					}else if (URIUtils.isCreateQuestionRequest(uri)){
						QuestionRequest.doCreate(httpExchange, parameters);
					}else if (URIUtils.isDeleteQuestionRequest(uri)){
						QuestionRequest.doDelete(httpExchange, parameters);
					}else if (URIUtils.isSendQuestionRequest(uri)){
						QuestionRequest.doSend(httpExchange, parameters);
					}else if (URIUtils.isGetLatestQuestionRequest(uri)){
						QuestionRequest.doGetLatest(httpExchange, parameters);
					}else if (URIUtils.isStopSendQuestionRequest(uri)){
						QuestionRequest.doStopSend(httpExchange, parameters);
					}
				}

				
			}

		}
		
		
	}

}
