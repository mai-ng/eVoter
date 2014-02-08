/**
 * 
 */
package evoter.mobile.objects;

/**
 * @author luongnv89
 * 
 */
public class Configuration {
	private static String IPAddress ="192.168.0.12";
	private static int portNumber = 1000;
	public static int TIME_OUT=1000;
	// for testing only
	private static String _urlLogin = "/evoter/login";
	private static String _urlResetPassword = "/evoter/reset_password";
	private static String _urlRegister = "/evoter/register";
	
	private static String _urlViewSubject = "/evoter/view_subject";
	private static String _urlGetAllSubject = "/evoter/get_all_subject";
	private static String _urlDeleteSubject = "/evoter/delete_subject";
	private static String _urlSearchSubject = "/evoter/search_subject";
	
	private static String _urlGetAllSession = "/evoter/get_all_session";
	private static String _urlViewSession = "/evoter/view_session";
	private static String _urlActiveSession = "/evoter/active_session";
	private static String _urlAcceptSession = "/evoter/accept_session";
	private static String _urlDeleteSession = "/evoter/delete_session";
	
	private static String _urlGetAllQuestion = "/evoter/get_all_question";
	private static String _urlViewQuestion = "/evoter/view_question";
	private static String _urlDeleteQuestion = "/evoter/delete_question";
	/**
	 * @return the iPAddress
	 */
	public static String getIPAddress() {
		return IPAddress;
	}
	/**
	 * @param iPAddress the iPAddress to set
	 */
	public static void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	/**
	 * @return the portNumber
	 */
	public static int getPortNumber() {
		return portNumber;
	}
	/**
	 * @param portNumber the portNumber to set
	 */
	public static void setPortNumber(int portNumber) {
		Configuration.portNumber = portNumber;
	}
	/**
	 * @return the _urlLogin
	 */
	public static String get_urlLogin() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlLogin;
	}
	/**
	 * @return the _urlViewSubject
	 */
	public static String get_urlViewSubject() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlViewSubject;
	}
	/**
	 * @return the _urlGetAllSubject
	 */
	public static String get_urlGetAllSubject() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlGetAllSubject;
	}
	/**
	 * @return the _urlDeleteSubject
	 */
	public static String get_urlDeleteSubject() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlDeleteSubject;
	}
	/**
	 * @return the _urlSearchSubject
	 */
	public static String get_urlSearchSubject() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlSearchSubject;
	}
	/**
	 * @return the _urlGetAllSession
	 */
	public static String get_urlGetAllSession() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlGetAllSession;
	}
	/**
	 * @return the _urlViewSession
	 */
	public static String get_urlViewSession() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlViewSession;
	}
	/**
	 * @return the _urlActiveSession
	 */
	public static String get_urlActiveSession() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlActiveSession;
	}
	/**
	 * @return the _urlAcceptSession
	 */
	public static String get_urlAcceptSession() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlAcceptSession;
	}
	/**
	 * @return the _urlDeleteSession
	 */
	public static String get_urlDeleteSession() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlDeleteSession;
	}
	/**
	 * @return the _urlGetAllQuestion
	 */
	public static String get_urlGetAllQuestion() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlGetAllQuestion;
	}
	/**
	 * @return the _urlViewQuestion
	 */
	public static String get_urlViewQuestion() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlViewQuestion;
	}
	/**
	 * @return the _urlDeleteQuestion
	 */
	public static String get_urlDeleteQuestion() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlDeleteQuestion;
	}
	/**
	 * @return the _urlResetPassword
	 */
	public static String get_urlResetPassword() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlResetPassword;
	}
	/**
	 * @return the _urlRegister
	 */
	public static String get_urlRegister() {
		return "http://"+getIPAddress()+":"+getPortNumber()+_urlRegister;
	}
	
	

}
