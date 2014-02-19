/**
 * 
 */
package evoter.mobile.utils;

/**
 * The message call back when a request to server is successful.
 * @author luongnv89
 */
public class CallBackMessage {
	/**
	 * Request submit excited value
	 */
	public static final String EXCITED_BAR_STATISTIC_EVOTER_REQUEST = "EXCITED";
	/**
	 * Request submit difficult value
	 */
	public static final String DIFFICULT_BAR_STATISTIC_EVOTER_REQUEST = "DIFFICULT";
	/**
	 * Request submit answer
	 */
	public static final String SUBMIT_ANSWER_EVOTER_REQUEST = "SUBMIT_ANSWER_EVOTER_REQUEST";
	/**
	 * Request submit accept session
	 */
	public static final String ACCEPT_SESSION_EVOTER_REQUEST = "ACCEPT_SESSION_EVOTER_REQUEST";
	/**
	 * Request change status of current session: from active to in-active and otherwise
	 */
	public static final String CHANGE_SESSION_STATUS_EVOTER_REQUEST = "CHANGE_SESSION_STATUS_EVOTER_REQUEST";
	/**
	 * Request submit statistic value: excited and difficult value
	 */
	public static final String SUBMIT_STATISTIC_EVOTER_REQUEST = "SUBMIT_STATISTIC_EVOTER_REQUEST";
	/**
	 * Request delete a question
	 */
	public static final String DELETE_QUESTION_EVOTER_REQUEST = "DELETE_QUESTION_EVOTER_REQUEST";
	/**
	 * Request stop receive answer for a question
	 */
	public static final String STOP_RECEIVE_ANSWER_EVOTER_REQUEST = "STOP_RECEIVE_ANSWER_EVOTER_REQUEST";
	/**
	 * Request update status (not send, sent, finish) of a question
	 */
	public static final String CHECK_QUESTION_STATUS_EVOTER_REQUEST = "CHECK_QUESTION_STATUS_EVOTER_REQUEST";
	/**
	 * Request update status (active or in-active) of a session
	 */
	public static final String CHECK_SESSION_STATUS_EVOTER_REQUEST = "CHECK_SESSION_STATUS_EVOTER_REQUEST";
	/**
	 * Request send question
	 */
	public static final String SEND_QUESTION_EVOTER_REQUEST = "SEND_QUESTION_EVOTER_REQUEST";
	/**
	 * Request get all student who has accepted to join current session
	 */
	public static final String GET_LIST_ACCEPTED_STUDENT_EVOTER_REQUEST = "GET_LIST_ACCEPTED_STUDENT_EVOTER_REQUEST";
	/**
	 * Request get all subject of current user
	 */
	public static final String GET_ALL_SUBJECT_EVOTER_REQUEST = "GET_ALL_SUBJECT_EVOTER_REQUEST";
	/**
	 * Request get all session of current subject
	 */
	public static final String GET_ALL_SESSION_EVOTER_REQUEST = "GET_ALL_SESSION_EVOTER_REQUEST";
	/**
	 * Request get all question of current session
	 */
	public static final String GET_ALL_QUESTION_EVOTER_REQUEST = "GET_ALL_QUESTION_EVOTER_REQUEST";
	/**
	 * Request get all user who involve current subject
	 */
	public static final String GET_USER_OF_SUBJECT_EVOTER_REQUEST = "GET_USER_OF_SUBJECT_EVOTER_REQUEST";
	/**
	 * Request delete session
	 */
	public static final String DELETE_SESSION_EVOTER_REQUEST = "DELETE_SESSION_EVOTER_REQUEST";
	/**
	 * Request login to system
	 */
	public static final String LOGIN_EVOTER_REQUEST = "LOGIN_EVOTER_REQUEST";
	/**
	 * Request logout 
	 */
	public static final String LOGOUT_EVOTER_REQUEST = "LOGOUT_EVOTER_REQUEST";
	/**
	 * Request reset password
	 */
	public static final String RESET_PASSWORD_EVOTER_REQUEST = "RESET_PASSWORD_EVOTER_REQUEST";
	/**
	 * Request create new student user
	 */
	public static final String CREATE_USER_EVOTER_REQUEST = "EVOTER_REQUEST_CREATE_USER";
	/**
	 * Request update current question
	 */
	public static final String UPDATE_QUESTION_EVOTER_REQUEST = "CREATE_USER_EVOTER_REQUEST";
	/**
	 * Request update current session
	 */
	public static final String UPDATE_SESSION_EVOTER_REQUEST = "EVOTER_REQUEST_UPDATE_SESSION";
	/**
	 * Request get statistic of current question
	 */
	public static final String GET_STATISTIC_EVOTER_REQUEST = "UPDATE_SESSION_EVOTER_REQUEST";
	/**
	 * Request create new session in current subject
	 */
	public static final String CREATE_SESSION_EVOTER_REQUEST = "CREATE_SESSION_EVOTER_REQUEST";
	/**
	 * Request create new question in current session
	 */
	public static final String CREATE_QUESTION_EVOTER_REQUEST = "CREATE_QUESTION_EVOTER_REQUEST";
	/**
	 * Request edit a session of subject
	 */
	public static final String EDIT_SESSION_EVOTER_REQUEST = "EDIT_SESSION_EVOTER_REQUEST";
	/**
	 * Request edit a question of subject
	 */
	public static final String EDIT_QUESTION_EVOTER_REQUEST = "EDIT_QUESTION_EVOTER_REQUEST";
	
}
