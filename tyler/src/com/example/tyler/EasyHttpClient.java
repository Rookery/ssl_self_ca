package com.example.tyler;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;

import android.content.Context;

public class EasyHttpClient extends DefaultHttpClient 
{    
  final Context context;

  public EasyHttpClient(HttpParams hparms, Context context)
  {
    super(hparms);
    this.context = context;     
  }

  @Override
  protected ClientConnectionManager createClientConnectionManager() {
    SchemeRegistry registry = new SchemeRegistry();
    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

    // Register for port 443 our SSLSocketFactory with our keystore
    // to the ConnectionManager
    SSLSocketFactory sf = null;
    try {
		sf = new TencentSSLSocketFactory(null);
	} catch (KeyManagementException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnrecoverableKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (KeyStoreException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
    registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

    //http://blog.synyx.de/2010/06/android-and-self-signed-ssl-certificates/
    return new SingleClientConnManager(getParams(), registry);
  }
}