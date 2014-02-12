package evoter.server.http.request;

import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;

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
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestQuestionService {

	Map<String, Object> parameters;
	@Before
	public void setUp(){
		parameters = new HashMap<String, Object>();
	}
	@After
	public void tearDown(){
		parameters = null;
	}
	@Autowired
	IQuestionService questionService;
	/**
	 * Test for {@link IQuestionService#doGetAll(java.util.Map)} </br>
	 * Expect returning a {@link JSONArray} </br>
	 */

	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetAll() { 
		
		String expected_response= "" +
		"[{\"USER_ID\":1,\"SESSION_ID\":4,\"QUESTION_TYPE_ID\":1,\"ID\":2,\"column1\"" +
		":[{\"ANSWER_TEXT\":\"add a new answer\",\"ID\":9,\"QUESTION_ID\":2}," +
		"{\"ANSWER_TEXT\":\"add a new answer\",\"ID\":10,\"QUESTION_ID\":2}]," +
		"\"QUESTION_TEXT\":\"Abstract can implement an interface?\"," +
		"\"CREATION_DATE\":\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";

		String sessionId = "4";
		parameters.put(QuestionSessionDAO.SESSION_ID, sessionId);
		Object response = questionService.doGetAll(parameters);
		assertEquals(response.toString(), expected_response);
		
	}
	/**
	 * Test for {@link IQuestionService#doView(Map)} </br>
	 * Expect returning {@link Question} </br>
	 * @see Question#toJSON() </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doView(){
		
		String expected_response = "" + 
		"[{\"USER_ID\":1,\"QUESTION_TYPE_ID\":1,\"ID\":1,\"QUESTION_TEXT\":\"Interface can implement an interface?\"," +
		"\"CREATION_DATE\":\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";
		
		String questionId = "1";
		parameters.put(QuestionDAO.ID, questionId);
		Object response = questionService.doView(parameters);
		assertEquals(response.toString(), expected_response);
	}
	/**
	 * Test for {@link IQuestionService#getAnswersOfQuestion(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_getAnswersOfQuestion(){
		
		String expected_response = ""+
		"[{\"ANSWER_TEXT\":\"1\",\"ID\":1,\"QUESTION_ID\":3},{\"ANSWER_TEXT\":\"2\",\"ID\":2,\"QUESTION_ID\":3}," +
		"{\"ANSWER_TEXT\":\"0\",\"ID\":3,\"QUESTION_ID\":3},{\"ANSWER_TEXT\":\"unlimited\",\"ID\":4,\"QUESTION_ID\":3}]";
		long questionId = 3;
		Object response = questionService.getAnswersOfQuestion(questionId);
		assertEquals(response.toString(), expected_response);
		
		//return an empty array
		response = questionService.getAnswersOfQuestion(1);
		assertEquals(response.toString(), "[]");
	}
	/**
	 * Test for {@link IQuestionService#doCreate(Map)} </br>
	 * Expect returning a SUCCESS message </br>
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
	 * Expect returning a SUCCESS message </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doDelete(){
		
		parameters.put(QuestionDAO.ID, "1");
		Object response = questionService.doDelete(parameters);
		assertEquals(response.toString(), "SUCCESS");
	}
	/**
	 * Test for {@link IQuestionService#doSend(Map)} </br>
	 * Expect returning a SUCCESS message </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doSend(){
		
		parameters.put(QuestionDAO.ID, "1");
		parameters.put(QuestionSessionDAO.SESSION_ID, "1");
		Object response = questionService.doSend(parameters);
		assertEquals(response.toString(), "SUCCESS");
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
		questionService.addSentQuestion(sessionId, questionId);
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		String expected_response = "" +
		"[{\"USER_ID\":1,\"QUESTION_TYPE_ID\":1,\"answers\":[],\"ID\":1,\"QUESTION_TEXT\":\"Interface can implement an interface?\"," +
		"\"CREATION_DATE\":\"2014-01-09 10:31:17.0\",\"PARENT_ID\":0}]";
		
		Object response = questionService.doGetLatest(parameters);
		assertEquals("doGetLatest", response.toString(), expected_response);
		
		//System.out.println(response);
		
	}
	
	/**
	 * Test for {@link IQuestionService#doGetLatest(Map)} </br>
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
	 * Expect returning a SUCCESS message </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doStopSend(){
		
		long sessionId = 1;
		long questionId = 1;
		parameters.put(QuestionSessionDAO.SESSION_ID, String.valueOf(sessionId));
		questionService.addSentQuestion(sessionId, questionId);
		
		assertTrue("canSendQuestion returns true",
				questionService.canSendQuestion(sessionId));
		Object response = questionService.doStopSend(parameters);
		assertEquals("doStopSend() returns SUCCESS message", 
				response.toString(), "SUCCESS");
		assertFalse("canSendQuestion returns false",
				questionService.canSendQuestion(sessionId));

	}
	
	/**
	 * Test for {@link IQuestionService#doEdit(Map)} </br>
	 * Expect returning a SUCCESS message </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doEdit(){
		
		String questionId = "1";
		String question_text = "the tested datat";
		parameters.put(QuestionDAO.ID, questionId);
		parameters.put(QuestionDAO.QUESTION_TEXT, question_text);
		
		Object response = questionService.doEdit(parameters);
		assertEquals("doEdit() returns SUCCESS message", 
				response.toString(), "SUCCESS");

	}

}
