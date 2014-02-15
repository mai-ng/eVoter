/**
 * 
 */
package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IAnswerService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.QuestionType;
import evoter.share.utils.URIRequest;

/**
 * 
 * This class is an implementation of {@link IAnswerService} </br>
 * @author btdiem </br>
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class AnswerService implements IAnswerService {

	private AnswerDAO answerDAO;
	
	public AnswerDAO getAnswerDAO(){
		return this.answerDAO;
	}
	public void setAnswerDAO(AnswerDAO answerDAO){
		this.answerDAO = answerDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#doCreate(long, java.lang.String[])
	 */
	@Override
	public Object doCreate(long questionId, String[] answerTexts) throws Exception{
			
			for (String answerText : answerTexts){
				Answer answer = new Answer(questionId, answerText);
				answerDAO.insert(answer);
			}
			return URIRequest.SUCCESS_MESSAGE;

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#doEdit(java.lang.String[], java.lang.String[])
	 */
	@Override
	public Object doEdit(long questionId, String[] answerIds, String[] answerTexts) throws Exception{
		
		int len = answerIds.length;
		for (int i=0; i<len; i++){
			Answer answer = new Answer();
			answer.setAnswerText(answerTexts[i]);
			answer.setId(Long.valueOf(answerIds[i]));
			answer.setQuestionId(questionId);
			answerDAO.update(answer);
		}
		return URIRequest.SUCCESS_MESSAGE;

	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#getAllAnswer(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object doGetAllAnswer(long questionId) throws Exception{
		
		JSONArray response = new JSONArray();
		List<Answer> answers = (List<Answer>)answerDAO.findByQuestionId(questionId);
		if (answers != null && !answers.isEmpty()){
			
			for (Answer answer : answers){
				response.add(answer.toJSON());
			}
			
		}
		return response;
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#doDelete(long)
	 */
	@Override
	public Object doDelete(long questionId) throws Exception{
		
		answerDAO.deleteByQuestionId(questionId);
		
		return URIRequest.SUCCESS_MESSAGE;
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#doVote(java.util.Map)
	 */
	@Override
	public Object doVote(Map<String, Object> parameter) {
		
		try{
			
			long answerId = Long.valueOf((String)parameter.get(AnswerDAO.ID));
			List<Answer> answers = answerDAO.findById(answerId);
			if (answers != null && !answers.isEmpty()){
				Answer answer = answers.get(0);
				String statistics = answer.getStatistics();
				long questionTypeId = Long.valueOf((String)parameter.get(QuestionDAO.QUESTION_TYPE_ID));
				
				if (questionTypeId == QuestionType.INPUT_ANSWER 
					|| questionTypeId == QuestionType.SLIDER){
					
					statistics += ":" + (String)parameter.get(AnswerDAO.STATISTICS);
					
				}else{
					if (statistics != null){
						statistics = String.valueOf(Integer.valueOf(statistics) + 1);
					}else{
						statistics = "1";
					}
					
				}
				answer.setStatistics(statistics);
				answerDAO.update(answer);
				return URIRequest.SUCCESS_MESSAGE;
			}
			return URIRequest.ANSWER_NOT_EXIST;
			
		}catch(Exception e){
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}

	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAnswerService#doGetStatistics(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object doGetStatistics(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		JSONArray response = new JSONArray();
		
		try{
			
			long questionId = Long.valueOf((String) 
					parameter.get(QuestionDAO.ID));
			List<Answer> answerList = answerDAO.findByQuestionId(questionId);
			if (answerList != null){
				for (Answer answer : answerList){
					response.add(answer.toJSON());
				}//for
				
			}//if
			return response;
		}catch(Exception e){
			
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}
		
		
	}
	
	

}
