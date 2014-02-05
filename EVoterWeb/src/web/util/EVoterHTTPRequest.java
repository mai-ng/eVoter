/**
 * 
 */
package web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author luongnv89
 */
public class EVoterHTTPRequest {
	/**
	 * Execute a post request with parameter using HttpURLConnection
	 * 
	 * @param targetURL
	 * @param params
	 * @return
	 */
	public static String excutePost(String targetURL, List<NameValuePair> params) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			// connection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "UTF-8"));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
			String str1 = response.toString();
			return str1;
		} catch (Exception e) {
			e.printStackTrace();
			return "Exeption: " + e.toString();
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}
	
	/**
	 * Convert parameter to string parameter
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getQuery(List<NameValuePair> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");
			
			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}
		
		return result.toString();
	}
}
