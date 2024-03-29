/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.Map;

import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;

/**
 * This is an interface to define methods that works with {@link Answer} object in the database </br>
 * There is no delete function for answer just because the relation between {@link Question}
 * and {@link Answer} is UPDATE CASCASDE AND DELETE CASCADE, so when question is deleted,
 * its answers are deleted by default </br>
 * @author btdiem </br>
 *
 */
public interface IAnswerService {
	
	final String BEAN_NAME = "answerService";
	/**
	 * 
	 * @param questionId question Id value of answer </br>
	 * @param answerTexts an array of answer content </br>
	 * @return SUCCESS message if answers are created and inserted to database successfully </br>
	 * @return FAILURE message if an exception is thrown </br>
	 */
	public Object doCreate(long questionId, String[] answerTexts) throws Exception;
	/**
	 * 
	 * @param answerIds an array of answer id that needed to be updated content
	 * @param answerTexts an array of answer text
	 * @param questionId question id value of answers to be updated </br>
	 * @return SUCCESS message if answers are updated successfully </br>
	 * @return FAILURE message if an exception is thrown </br>
	 */
	public Object doEdit(long questionId, String[] answerIds, String[] answerTexts) throws Exception;

	/**
	 * Select all {@link Answer} of given question </br>
	 * @param questionId question id value of selected {@link Answer} </br>
	 * @return an {@link Answer#toJSON()} array </br>
	 * @return an empty array if the question has no answer </br>
	 * @return FAILURE message if there is an exception thrown </br>
	 */
	public Object doGetAllAnswer(long questionId) throws Exception;
	/**
	 * Delete all answers of a question </br>
	 * @param questionId question id value of answers will be deleted </br>
	 * @return SUCCESS if answers are deleted successfully </br>
	 * @throws exception if answers are deleted unsuccessfully </br>
	 */
	public Object doDelete(long questionId) throws Exception;
	/**
	 * Update the statistic value of {@link Answer} </br>
	 * if question type is slider or input, append a new value to the current statistics value as a string </br>
	 * if question type is yes/no, agree/disagree, multi-choice, add 1 to the current statistics value </br>
	 * 
	 * @param parameter request parameter contains: </br>
	 * {@link AnswerDAO#ID} </br>
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link QuestionDAO#QUESTION_TYPE_ID} </br>
	 * {@link AnswerDAO#STATISTICS} if the question type is slider or input </br> 
	 * @return SUCCESS message if updating the number of vote for {@link Answer} successfully </br>
	 * @return FAILURE 	message if there is an exception thrown </br>
	 */
	public Object doVote(Map<String, Object> parameter);
	/**
	 * Get statistics of a given question
	 * If there is {@link Answer} for that question, returning an empty array </br>
	 * Otherwise, returning an {@link Answer#toJSON()} array </br>
	 * 
	 * @param parameter contains </br>
	 * {@link UserDAO#USER_KEY} for account verification</br>
	 * {@link QuestionDAO#ID} question id of answers that have statistics value </br>
	 * @return json array contains answer ID and its statistics  if question has statistics</br>
	 * @return an empty array if the question has no statistics </br>
	 */
	public Object doGetStatistics(Map<String, Object> parameter);
	
		
	
}
