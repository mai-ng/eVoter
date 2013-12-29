package csc7326.mylectures;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by luongnv89 on 29/12/13.
 */
public class InternetChecking {
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String getData(String url) {
        BufferedReader bf = null;
        try {
            HttpClient client = new DefaultHttpClient();
            URI website = new URI(url);
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = bf.readLine();
            StringBuffer stringBuffer = new StringBuffer();
            while (line != null) {
                stringBuffer.append(line + "\n");
                line = bf.readLine();
            }
            return stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
