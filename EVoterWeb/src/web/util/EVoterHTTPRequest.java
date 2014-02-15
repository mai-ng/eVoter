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
import java.util.zip.GZIPInputStream;

import org.apache.http.NameValuePair;

/**
 * @author maint
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
			//			connection.setRequestProperty("Content-Type",
			//					"application/json; charset=ISO-8859-1");
			//			connection.setRequestProperty("Content-Language", "en-US");
			//			connection.setRequestProperty("Content-Encoding", "gzip");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "ISO-8859-1"));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			connection.connect();
			
//			System.out.println("Content encoding: " + connection.getContentEncoding());
//			System.out.println("Content type: " + connection.getContentType());
//			System.out.println("Response code: " + connection.getResponseCode());
			InputStream is = connection.getInputStream();
			GZIPInputStream zippedInputStream = new GZIPInputStream(is);
			BufferedReader rd = new BufferedReader(new InputStreamReader(zippedInputStream));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line + "\n");
			}
			rd.close();
			System.out.println("Response: " +  response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
