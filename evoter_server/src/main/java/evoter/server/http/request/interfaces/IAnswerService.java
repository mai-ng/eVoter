/**
 * 
 */
package evoter.server.http.request.interfaces;

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
	
	/**
	 * 
	 * @param questionId question Id value of answer </br>
	 * @param answerTexts an array of answer content </br>
	 * @return SUCCESS message if answers are created successfully </br>
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
	 * Select all {@link Answer} of a question </br>
	 * @param questionId question id value of selected {@link Answer} </br>
	 * @return an {@link Answer#toJSON()} array </br>
	 * @return an empty array if there is no answer for this question </br>
	 * @return FAILURE message if there is an exception thrown </br>
	 */
	public Object doGetAllAnswer(long questionId) throws Exception;
	/**
	 * Delete answers of a question </br>
	 * @param questionId question id value of answers will be deleted </br>
	 * @return SUCCESS if answers are deleted successfully </br>
	 * @throws exception if answers are not deleted unsuccessfully </br>
	 */
	public Object doDelete(long questionId) throws Exception;
		
	
}
