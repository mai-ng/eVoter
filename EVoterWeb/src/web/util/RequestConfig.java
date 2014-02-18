/**
 * 
 */
package web.util;


/**
 * <li> Delete all fix uri string
 * <li> create method {@link RequestConfig#getURL(String)} to build full url for request
 * <li> Change the class name from Configuration to RequestConfig
 */
public class RequestConfig {
	private static String IPAddress = "192.168.0.12";
	private static int portNumber = 1000;
	private static String context = "/evoter";
	public static int TIME_OUT = 1000;
	
	/**
	 * @return the context
	 */
	public static String getContext() {
		return context;
	}
	
	/**
	 * @param context the context to set
	 */
	public static void setContext(String context) {
		RequestConfig.context = context;
	}
	
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
		RequestConfig.portNumber = portNumber;
	}
	
	/**
	 * Get full request url
	 * @param uri
	 * @return full request url
	 */
	public static String getURL(String uri) {
		return "http://" + getIPAddress() + ":" + getPortNumber() + getContext() + uri;
	};
}
