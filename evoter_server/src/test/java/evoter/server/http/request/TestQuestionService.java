package evoter.server.http.request;

import static org.junit.Assert.*;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IQuestionService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
/**
 * 
 * Make test cases for {@link IQuestionService} and {@link QuestionService} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class TestQuestionService {
	//request parameter map
	Map<String, Object> parameters;
	@Before
	public void setUp(){
		parameters = new HashMap<String, Object>();
	}
	@After
	public void tearDown(){
		parameters = null;
	}
	/**
	 * Create an instance of {@link IQuestionService}
	 */
	@Autowired
	IQuestionService questionService;
	/**
	 * Create an instance of {@link QuestionDAO}
	 */
	@Autowired
	QuestionDAO questionDAO;
	/**
	 * Test for {@link IQuestionService#doGetAll(java.util.Map)} </br>
	 * Select all questions and their answers of session having sessionID=4
	 * Expect returning an array of {@link Question#toJSON()} and {@link Answer#toJSON()} </br>
	 */

	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetAll() { 
		
		long sessionId = 4;
		
		String expected_response = ""+
		"[{\"USER_ID\":1,\"SESSION_ID\":4,\"QUESTION_TYPE_ID\":1,\"ID\":2,\"column1\":" +
		"[{\"ANSWER_TEXT\":\"No\",\"ID\":9,\"QUESTION_ID\":2,\"STATISTICS\":\"2\"}," +
		"{\"ANSWER_TEXT\":\"Yes\",\"ID\":10,\"QUESTION_ID\":2,\"STATISTICS\":\"5\"}]," +
		"\"QUESTION_TEXT\":\"Abstract can implement an interface?\",\"STATUS\":0,\"CREATION_DATE\":" +
		"\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";

		
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		Object response = questionService.doGetAll(parameters);
		

		assertEquals(response.toString(), expected_response);
		
	}
	
	/**
	 * Test for {@link IQuestionService#doView(Map)} </br>
	 * Select question information having sessionID=1 </br>
	 * Expect returning {@link Question#toJSON()} array</br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doView(){
		
		String expected_response = "" + 
		"[{\"USER_ID\":1,\"QUESTION_TYPE_ID\":1,\"ID\":1,\"QUESTION_TEXT\":\"Interface can implement an interface?\"," +
		"\"STATUS\":0,\"CREATION_DATE\":\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";
		
		String questionId = "1";
		parameters.put(QuestionDAO.ID, questionId);
		Object response = questionService.doView(parameters);
		assertEquals(response.toString(), expected_response);
	}

	/**
	 * Test for {@link IQuestionService#doCreate(Map)} </br>
	 * Creating a question with its properties such as question test, question type </br>
	 * creation date, creator user, sessionID and anwers </br>
	 * Expect returning a SUCCESS message without an exception </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doCreate(){
		
		parameters.put(QuestionDAO.QUESTION_TEXT, 
				new String[]{"a new question is created"});
		parameters.put(QuestionDAO.QUESTION_TYPE_ID, "2");
		parameters.put(QuestionDAO.CREATION_DATE, "2014-02-09 22:39:24");
		parameters.put(UserDAO.USER_KEY, "123456_3_3");
		parameters.put(QuestionSessionDAO.SESSION_ID, "1");
		parameters.put(AnswerDAO.ANSWER_TEXT, 
				new String[]{"answer b", "answer b", "answer c"});
		
		Object response = questionService.doCreate(parameters);
		assertEquals(response.toString(), "SUCCESS");
		
	}
	/**
	 * Test for {@link IQuestionService#doDelete(Map)} </br>
	 * Delete question having question ID =1 and expect returning a SUCCESS message</br>
	 * After finishing the transaction, expect searching a question having questionID=1 returning empty array</br> 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doDelete(){
		
		long questionId = 1;
		
		parameters.put(QuestionDAO.ID, String.valueOf(questionId));
		Object response = questionService.doDelete(parameters);
		assertEquals(response.toString(), "SUCCESS");
		
		List<Question> questionList = questionDAO.findById(questionId);
		assertTrue(questionList.isEmpty());
		
		
	}
	/**
	 * Test for {@link IQuestionService#doSend(Map)} </br>
	 * Sending a question having questionID=1 </br>
	 * Expect returning a SUCCESS message and question status will changed to 1 </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doSend(){
		
		long questionId = 1;
		long sessionId = 1;
		
		parameters.put(QuestionDAO.ID, String.valueOf(questionId));
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		Object response = questionService.doSend(parameters);
		assertEquals(response.toString(), "SUCCESS");
		
		Question question = questionDAO.findById(questionId).get(0);
		assertEquals("question status is 1", question.getStatus(), 1);
	}
	/**
	 * Test for {@link IQuestionService#doGetLatest(Map)} </br>
	 * Expect returning a array of {@link Question} and their {@link Answer} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetLatest_1(){
		
		long sessionId = 1;
		long questionId = 1;
		
		Question question = new Question(1, 1, 
				"Interface can implement an interface?"
				, Timestamp.valueOf("2014-01-09 10:31:17"), 0);
		question.setId(questionId);
		
		questionService.addSentQuestion(sessionId, question);
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		String expected_response = "" +
		"[{\"USER_ID\":1,\"QUESTION_TYPE_ID\":1," +
		"\"answers\":[{\"ANSWER_TEXT\":\"Yes\",\"ID\":139,\"QUESTION_ID\":1,\"STATISTICS\":\"10\"}," +
		"{\"ANSWER_TEXT\":\"No\",\"ID\":140,\"QUESTION_ID\":1,\"STATISTICS\":\"30\"}]," +
		"\"ID\":1,\"QUESTION_TEXT\":\"Interface can implement an interface?\"," +
		"\"STATUS\":0,\"CREATION_DATE\":\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";
		
		Object response = questionService.doGetLatest(parameters);
		assertEquals("doGetLatest", response.toString(), expected_response);
		
	}
	
	/**
	 * Test for {@link IQuestionService#doGetLatest(Map)} </br>
	 * Get a sending status of a question of sessionID=2. This session has no sending status question </br> 
	 * Expect returning an empty array </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetLatest_2(){
		
		String sessionId = "2";

		parameters.put(QuestionSessionDAO.SESSION_ID, sessionId);
		
		Object response = questionService.doGetLatest(parameters);
		assertEquals("doGetLatest returns an empty array" , 
				response.toString(), "[]");
	}
	/**
	 * Test for {@link IQuestionService#doStopSend(Map)} </br>
	 * Stop sending question of sessionId=1 </br>
	 * Expect returning a SUCCESS message and question status of sessionId=1 is changed to 2 </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doStopSend(){
		
		long sessionId = 1;
		//long questionId = 1;
		Question question = new Question(1, 1, "question text", Timestamp.valueOf("2014-01-09 10:31:17"), 0);
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		questionService.addSentQuestion(sessionId, question);
		
		assertTrue("canSendQuestion returns true",
				questionService.canSendQuestion(sessionId));
		Object response = questionService.doStopSend(parameters);
		assertEquals("doStopSend() returns SUCCESS message", 
				response.toString(), "SUCCESS");
		assertFalse("canSendQuestion returns false",
				questionService.canSendQuestion(sessionId));
		
		assertEquals("question status will be changed to 2", question.getStatus(), 2);

	}
	
	/**
	 * Test for {@link IQuestionService#doEdit(Map)} </br>
	 * Edit question text, answer text of question id=3 </br>
	 * Expect returning a SUCCESS message  and the changes are updated after getting back</br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doEdit(){
		
		long questionId = 3;
		String question_text = "the tested data";
		parameters.put(QuestionDAO.ID, String.valueOf(questionId));
		parameters.put(QuestionDAO.QUESTION_TEXT, question_text);
		parameters.put(AnswerDAO.ANSWER_TEXT, new String[]{"answer 1", "answer 2"});
		
		Object response = questionService.doEdit(parameters);
		assertEquals("doEdit() returns SUCCESS message", 
				response.toString(), "SUCCESS");
		
		Question question = questionDAO.findById(questionId).get(0);
		assertEquals("question text is changed", question.getQuestionText(), question_text);
	}

}
