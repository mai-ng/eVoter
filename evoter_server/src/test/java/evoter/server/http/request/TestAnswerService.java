package evoter.server.http.request;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.IAnswerService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.QuestionType;
/**
 * Create unit test cases for {@link IAnswerService} and {@link AnswerService} </br>
 * 
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
public class TestAnswerService {
	//username is used to create login case
	String username = "paul_gibson";
	//password is used to create login case
	String password = "12345678";
	
	/**
	 * Create a {@link IAnswerService} instance
	 */
	@Autowired
	private IAnswerService answerService;
	/**
	 * Create a {@link IAccountService} instance
	 */
	@Autowired
	private IAccountService accountService;
	/**
	 * Create an instance of {@link QuestionDAO} </br>
	 */
	@Autowired
	private QuestionDAO questionDAO;
	/**
	 * Create an instance of {@link AnswerDAO}
	 */
	@Autowired
	private AnswerDAO answerDAO;
	//request parameter map
	private Map<String, Object> parameters;

	@Before
	public void setUp() throws Exception {
		parameters = new HashMap<String, Object>();
	}

	@After
	public void tearDown() throws Exception {
		parameters = null;
	}

	/**
	 * Test for {@link IAnswerService#doCreate(long, String[])} </br>
	 * Create 2 answers for question having id=1 </br>
	 * Expect returning SUCCESS message and returning 2 answers when searching questionId=1</br>
	 * @throws Exception 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doCreate() throws Exception {
		
		long questionId = 1;
		String[] answerTexts = new String[]{"answer 1", "answer 2"};
		
		Object response = answerService.doCreate(questionId, answerTexts);
		assertEquals("doCreate", response.toString(), "SUCCESS");
		
		List<Answer> answers = answerDAO.findByQuestionId(questionId);
		assertEquals("question has 4 answers (2 old + 2 new)", answers.size(), 4);
		
	}
	/**
	 * Test for {@link IAnswerService#doEdit(long, String[], String[])} </br>
	 * Expect returning SUCCESS message and the answers are updated </br>
	 * @throws Exception </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doEdit() throws Exception{
		
		long questionId = 3;
		String [] answerIds = new String[]{"1", "2"};
		String[] answerTexts = new String[]{"answer 1", "answer 2"};
		
		Object response = answerService.doEdit(questionId, answerIds, answerTexts);
		assertEquals("doEdit", response.toString(), "SUCCESS");
		
		Answer answer_id_1 = answerDAO.findById(1).get(0);
		assertEquals(answer_id_1.getAnswerText(), "answer 1");
		
		Answer answer_id_2 = answerDAO.findById(2).get(0);
		assertEquals(answer_id_2.getAnswerText(), "answer 2");

	}
	/**
	 * Test for {@link IAnswerService#doGetAllAnswer(long)} </br>
	 * Select all answers of question id=1 and expect returning a {@link Answer#toJSON()} array </br>
	 * Select all answers of question id=3 and expect returning an  or an empty array</br>
	 * 
	 * @throws Exception </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetAllAnswer() throws Exception{
		
		long questionId = 3;
		Object response = answerService.doGetAllAnswer(questionId);
		String expected_response = ""+
		"[{\"ANSWER_TEXT\":\"1\",\"ID\":1,\"QUESTION_ID\":3,\"STATISTICS\":null}," +
		"{\"ANSWER_TEXT\":\"2\",\"ID\":2,\"QUESTION_ID\":3,\"STATISTICS\":null}," +
		"{\"ANSWER_TEXT\":\"0\",\"ID\":3,\"QUESTION_ID\":3,\"STATISTICS\":null}," +
		"{\"ANSWER_TEXT\":\"unlimited\",\"ID\":4,\"QUESTION_ID\":3,\"STATISTICS\":null}]";
		assertEquals("doGetAllAnswer", response.toString(), expected_response);
		
		questionId = 1;
		response = answerService.doGetAllAnswer(questionId);
		expected_response = ""+
				"[{\"ANSWER_TEXT\":\"Yes\",\"ID\":139,\"QUESTION_ID\":1,\"STATISTICS\":\"10\"}," +
				"{\"ANSWER_TEXT\":\"No\",\"ID\":140,\"QUESTION_ID\":1,\"STATISTICS\":\"30\"}]";
				
		assertEquals("doGetAllAnswer()", response.toString(), expected_response);
	}
	/**
	 * Test for {@link IAnswerService#doDelete(long)} </br>
	 * Delete all answers of question id=3 and expect returning a SUCCESS message </br>
	 * Select all answers of question id=3 after deleting and expect returning an empty </br> 
	 * @throws Exception </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doDelete() throws Exception{
		
		long questionId = 3;
		Object response = answerService.doDelete(questionId);
		assertEquals("doDelete", response.toString(), "SUCCESS");
		
		List<Answer> answers = answerDAO.findByQuestionId(questionId);
		assertTrue(answers.isEmpty());
	}
	/**
	 * Test for {@link IAnswerService#doGetStatistics(java.util.Map)} </br>
	 * Create a login case to generate userkey. This userkey is verify when 
	 * calling {@link IAnswerService#doGetStatistics(Map)} </br>
	 * Expect returning an {@link Answer#toJSON()} array </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test_doGetStatistics(){
		
		String expected_response=""+
		"[{\"ID\":139,\"STATISTICS\":\"10\"}," +
		"{\"ID\":140,\"STATISTICS\":\"30\"}]";

		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		
		//remove login username and password out of the request parameter 
		parameters.remove(UserDAO.USER_NAME);
		parameters.remove(UserDAO.PASSWORD);
		//add userkey that is generated by system after logging successfully
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(QuestionDAO.ID, "1");
		
		Object response = answerService.doGetStatistics(parameters);
		assertEquals("doGetStatistics() returns Answer array", response.toString(), expected_response);
	}
	/**
	 * Test for {@link IAnswerService#doVote(Map)} </br>
	 * Create a login case to generate userkey. This userkey is verify when </br>
     * calling {@link IAnswerService#doVote(Map)} </br>
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void test_doVote(){
	
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		//remove login username and password out of the request parameter 
		parameters.remove(UserDAO.USER_NAME);
		parameters.remove(UserDAO.PASSWORD);
		//add userkey that is generated by system after logging successfully
		parameters.put("userkey", userKey.get("userkey"));
		//add answer id that is update statistics value
		parameters.put(AnswerDAO.ID, "1");
		parameters.put(QuestionDAO.QUESTION_TYPE_ID, String.valueOf(QuestionType.YES_NO));
		
		Object response = answerService.doVote(parameters);
		assertEquals("doVote", response.toString(), "SUCCESS");
	}

}
