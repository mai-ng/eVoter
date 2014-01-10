package evoter.mobile.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAccountValidation {
	/**
	 * ^             	# Start of the line
	 * [a-zA-Z0-9_-]	# Match characters and symbols in the list, a-z, A-Z, 0-9, underscore, hyphen
	 * {6,15} 			# Length at least 6 characters and maximum length of 15 
	 * $				# End of the line
	 */
	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{6,15}$";
	
	/**
	 * ^             	# Start of the line
	 * [a-zA-Z0-9]		# Match characters and symbols in the list, a-z, A-Z, 0-9
	 * {6,15} 			# Length at least 6 characters and maximum length of 15 
	 * $				# End of the line
	 */
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{6,15}$";
	
	/**
	 * ^             	# Start of the line
	 * [a-zA-Z0-9_.]	# Match characters and symbols in first part, a-z, A-Z, 0-9, underscore, dot
	 * @				# Essential character follows the first part
	 * [a-zA-Z]			# Follow "@" are  characters and symbols, a-z, A-Z
	 * $				# End of the line
	 */
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.]*@[a-zA-Z]*.[a-zA-Z]*$";
	
	private static Pattern usrname_pattern = Pattern.compile(USERNAME_PATTERN);
	private static Pattern password_pattern = Pattern.compile(PASSWORD_PATTERN);
	private static Pattern email_pattern = Pattern.compile(EMAIL_PATTERN);
	
	private static Matcher matcher;

	/**
	 * @param username
	 * @return true if all characters are matched with defined pattern.
	 */
	public static boolean isValidUserName(final String username) {
		matcher = usrname_pattern.matcher(username);
		return matcher.matches();
	}
	
	/**
	 * @param pass
	 * @return true if all characters are matched with defined pattern.
	 */
	public static boolean isValidPassword(final String pass) {
		matcher = password_pattern.matcher(pass);
		return matcher.matches();
	}
	
	/**
	 * @param email
	 * @return true if all characters are matched with defined pattern.
	 */
	public static boolean isValidEmail(final String email) {
		matcher = email_pattern.matcher(email);
		return matcher.matches();
	}
}
