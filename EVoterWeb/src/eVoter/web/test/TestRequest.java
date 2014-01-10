/**
 * 
 */
package eVoter.web.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.AbstractHttpAsyncClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;

import evoter.server.dao.UserDAO;

/**
 * @author maint
 * 
 */
public class TestRequest {

	static CloseableHttpClient client = HttpClients.createDefault();

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(UserDAO.USER_NAME, "nvluong"));
		params.add(new BasicNameValuePair(UserDAO.PASSWORD, "12345678"));

		HttpResponse responseLogin = getResponse(Configuration.get_urlLogin(),
				params);
		if (responseLogin != null) {
			System.out.println(readResponse(responseLogin));
		} else {
			System.out.println("Cannot get response");
		}
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static HttpResponse getResponse(String get_urlLogin,
			List<NameValuePair> params) {
		HttpResponse response = null;
		try {
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);

			HttpPost request = null;
			request = new HttpPost(get_urlLogin);
			request.setEntity(ent);
			response = client.execute(request);
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	protected static String readResponse(HttpResponse response) {
		String ret = null;
		if (response != null) {
			System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// GzipDecompressingEntity decom = entity.getContent();
				// System.out.println(entity.toString());
				try {
					InputStream in = entity.getContent();
					InputStreamReader reader = new InputStreamReader(in);
					BufferedReader bfreader = new BufferedReader(reader);
					String read = bfreader.readLine();
					StringBuffer sb = new StringBuffer(read);
					while ((read = bfreader.readLine()) != null) {
						sb.append(read);
					}
					ret = sb.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
}
