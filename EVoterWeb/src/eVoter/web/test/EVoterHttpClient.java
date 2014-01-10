/**
 * 
 */
package eVoter.web.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 * @author luongnv89
 * 
 */
public class EVoterHttpClient {

	CloseableHttpClient client;
	HttpResponse response;
	final int TIME_OUT = 1000;

	/**
	 * 
	 */
	public EVoterHttpClient() {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(TIME_OUT).build();
		client = HttpClients.custom().setDefaultRequestConfig(config)
				.setRetryHandler(new HttpRequestRetryHandler() {

					@Override
					public boolean retryRequest(IOException exception,
							int nbRetry, HttpContext context) {
						if (nbRetry >= 5) {
							System.out.println("Retry maximum!");
							return false;
						}

						if (exception instanceof NoHttpResponseException) {
							exception.printStackTrace();
							return true;
						}

						if (exception instanceof java.net.SocketTimeoutException) {
							// Do not retry on SSL handshake exception
							System.out
									.println("java.net.SocketTimeoutException exception");
							return false;
						}
						HttpRequest request = (HttpRequest) context
								.getAttribute(ExecutionContext.HTTP_REQUEST);
						boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
						if (idempotent) {
							System.out.println("idempotent exception");
							// Retry if the request is considered idempotent
							return true;
						}
						return false;
					}
				}).build();
	}

	public int post(String get_urlLogin, List<NameValuePair> params) {
		HttpPost request = null;
		try {
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
			request = new HttpPost(get_urlLogin);
			request.setEntity(ent);
			response = client.execute(request);
			if (response != null) {
				System.out.println("Response status code: "
						+ response.getStatusLine().getStatusCode());
				return response.getStatusLine().getStatusCode();
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("Response status code: -1");
		return -1;
	}

	public String getResponseContent() {
		String ret = null;
		if (response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
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
