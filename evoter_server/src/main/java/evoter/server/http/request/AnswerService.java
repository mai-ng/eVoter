/**
 * 
 */
package evoter.server.http.request;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IAnswerService;
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
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
	
	

}
