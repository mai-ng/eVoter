package evoter.share.utils;

/**
 * Define URI request coming from clients </br>
 * 
 * @author btdiem </br>
 *
 */
public class URIRequest {
	//Message is sent to client when request is processed successfully
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	//Message is sent to client when request is processed failure
	public static final String FAILURE_MESSAGE = "FAILURE";
	//Message is sent to client when creating new user but this user exists in the database
	public static final String USER_EXIST_MESSAGE = "USER EXISTS ALREADY";
	//Message is sent to client when searching for user but getting back an empty response
	public static final String USER_NOT_EXIST_MESSAGE = "USER DOES NOT EXIST";
	//Message is sent to client when creating new user but email of this user exists in the database
	public static final String EMAIL_EXIST_MESSAGE = "EMAIL EXISTS";
	//Message is sent to client when searching for email but getting back an empty response
	public static final String EMAIL_NOT_EXIST_MESSAGE = "EMAIL DOES NOT EXIST";
	//Message is sent to client when searching for subject but getting back an empty response
	public static final String SUBJECT_NOT_EXIST_MESSAGE = "SUBJECT DOES NOT EXIST";
	//Message is sent to client when searching for answer but getting back an empty response
	public static final String ANSWER_NOT_EXIST = "ANSWER DOES NOT EXIST";
	//Message is sent to client when the question could not be found in the database
	public static String QUESTION_NOT_EXIST="QUESTION NOT EXIST";
	
	//login user request
	public static final String LOGIN="/login";
	//reset password request
	public static final String RESET_PASSWORD="/reset_password";	
	//register account request
	public static final String REGISTER="/register";
	//logout request
	public static final String LOGOUT="/logout";	
	//get all subject request
	public static final String GET_ALL_SUBJECT="/get_all_subject";
	//view a subject request
	public static final String VIEW_SUBJECT="/view_subject";
	//delete a subject request
	public static final String DELETE_SUBJECT="/delete_subject";
	//search subject request
	public static final String SEARCH_SUBJECT="/search_subject";
	//edit a subject request
	public static final String UPDATE_SUBJECT = "/update_subject";
	//create a subject request
	public static String CREATE_SUBJECT="/create_subject";
	// get all sessions of a subject request
	public static final String GET_ALL_SESSION="/get_all_session";
	//view a session request
	public static final String VIEW_SESSION="/view_session";
	//create a session request
	public static final String CREATE_SESSION="/create_session";
	//change active status of a session request
	public static final String ACTIVE_SESSION="/active_session";//start a session
	//change inactive status of a session request
	public static final String INACTIVE_SESSION="/inactive_session";//start a session
	//accept session of student user request
	public static final String ACCEPT_SESSION="/accept_session";//accept a session	
	//delete a session request
	public static final String DELETE_SESSION="/delete_session";
	//edit a session request 
	public static final String UPDATE_SESSION="/update_session";
	//search a session request
	public static final String SEARCH_SESSION="/search_session";
	//Get all students of a session that accepted or not accepted yet </br>
	public static final String GET_ALL_STUDENT="/get_all_students";
	//get all questions of a session request
	public static final String GET_ALL_QUESTION="/get_all_question";
	//view a question request
	public static final String VIEW_QUESTION="/view_question";
	//create a question request
	public static final String CREATE_QUESTION="/create_question";
	//delete a question request
	public static final String DELETE_QUESTION="/delete_question";
	//update a question request
	public static final String UPDATE_QUESTION="/update_question";
	//send a question request
	public static final String SEND_QUESTION="/send_question";
	//get a question request
	public static final String GET_QUESTION="/get_question";	
	//stop sending a question request
	public static final String STOP_SEND_QUESTION="/stop_send_question";	
	//search a question request
	public static final String SEARCH_QUESTION="/search_question";
	//get the sending question request
	public static final String GET_LATEST_QUESTION="/get_latest_question";
	//get statistics of a question request
	public static final String GET_STATISTICS="/get_statistics";
	//get all users that are invited to a subject request
	public static final String GET_ALL_USERS_OF_SUBJECT="/get_allusers_ofsubject";
	//get all users in the database request
	public static final String GET_ALL_USER="get_all_user";
	//create a new user request
	public static final String CREATE_USER="create_user";
	//update a new user request
	public static final String EDIT_USER="edit_user";
	//delete a user request
	public static final String DELETE_USER="delete_user";
	//Change the approved status of user request
	public static final String CHANGE_APPROVE_USER="change_approve_user";
	//vote for a question request
	public static final String VOTE_ANSWER="vote_answer";


	
	
}
