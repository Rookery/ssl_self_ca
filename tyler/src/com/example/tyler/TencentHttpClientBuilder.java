package com.example.tyler;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;
/*
 * http://blog.antoine.li/2010/10/22/android-trusting-ssl-certificates/
 */
public class TencentHttpClientBuilder {
    
	public static HttpClient buildHttpClient() {

		final int SET_CONNECTION_TIMEOUT = 15 * 1000;
		final int SET_SOCKET_TIMEOUT = 30 * 1000;
		DefaultHttpClient client = null;

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new TencentSSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);

			// Setting up parameters
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "utf-8");
			params.setBooleanParameter("http.protocol.expect-continue", false);

			// Setting timeout
			HttpConnectionParams.setConnectionTimeout(params, SET_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);
			
			//Setting User Agent
		    String userAgentString = "AndroidSDK_" + android.os.Build.VERSION.SDK_INT
		                + "_" + android.os.Build.DEVICE + "_"
		                + android.os.Build.VERSION.RELEASE;
		    HttpProtocolParams.setUserAgent(params, userAgentString);

			// Registering schemes for both HTTP and HTTPS
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			// Creating thread safe client connection manager
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			// Creating HTTP client
			client = new DefaultHttpClient(ccm, params);

		} catch (Exception e) {
			client = new DefaultHttpClient();
			Log.d("getHttpClient", e.toString());
		}

		return client;
	}
}
