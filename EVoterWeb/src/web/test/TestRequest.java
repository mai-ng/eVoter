/**
 * 
 */
package web.test;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
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

/**
 * @author maint
 * 
 */
public class TestRequest {

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException, IOException {

		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		// Start the client

		// Execute request
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", "nvluong"));
		params.add(new BasicNameValuePair("passwd", "12345678"));
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);

		HttpPost request = null;
		request = new HttpPost(Configuration.get_urlLogin());
		request.setEntity(ent);

		Future<HttpResponse> future = httpclient.execute(request,
				new FutureCallback<HttpResponse>() {
					public void completed(final HttpResponse response) {
						System.out.println("->" + response.getStatusLine());
					}

					public void failed(final Exception ex) {
						System.out.println("->" + ex);
					}

					public void cancelled() {
						System.out.println(" cancelled");
					}
				});
		request.releaseConnection();
		httpclient.close();
	}

}
