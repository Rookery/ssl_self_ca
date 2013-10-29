package com.example.tyler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private void test(View v){
        Log.d("tyler", "aaa");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.test_btn);
        
        btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(final View v) {
                // TODO Auto-generated method stub
                try{
                    Log.d("tyler", "aaa");
                   // test(v);
                    new AsyncTask<Void, Void, Void>(){

                        @Override
                        protected Void doInBackground(Void... params) {
                            // TODO Auto-generated method stub
                            
                         // Instantiate the custom HttpClient
                            HttpClient client = TencentHttpClientBuilder.buildHttpClient(); //new EasyHttpClient(null, getApplicationContext());// new DefaultHttpClient();// new MyHttpClient(getApplicationContext());
                            
                           HttpGet get = new HttpGet("https://developers.google.com/translate/v2/using_rest?hl=ja");
                            //HttpGet get = new HttpGet("https://id.b.qq.com/login/index");
                            //HttpGet get = new HttpGet("https://copiagenda.movistar.es/cp/ps/Main/login/Agenda");
                            
                            // Execute the GET call and obtain the response
                            HttpResponse getResponse = null;
                            try {
                                getResponse = client.execute(get);
                                Log.d("tyler", "status code " + getResponse.getStatusLine().getStatusCode());
                            } catch (ClientProtocolException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            HttpEntity responseEntity = getResponse.getEntity();
                            return null;
                        }
                        
                    }.execute();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        

        
//        PackageManager pm = this.getPackageManager();
//        String packageName = this.getPackageName();
//        int flags = PackageManager.GET_SIGNATURES;
//
//        PackageInfo packageInfo = null;
//
//        try {
//                packageInfo = pm.getPackageInfo(packageName, flags);
//        } catch (NameNotFoundException e) {
//                e.printStackTrace();
//        }
//        Signature[] signatures = packageInfo.signatures;
//
//        byte[] cert = signatures[0].toByteArray();
//
//        InputStream input = new ByteArrayInputStream(cert);
//
//        CertificateFactory cf = null;
//        try {
//                cf = CertificateFactory.getInstance("X509");
//
//
//        } catch (CertificateException e) {
//                e.printStackTrace();
//        }
//        X509Certificate c = null;
//        try {
//                c = (X509Certificate) cf.generateCertificate(input);
//        } catch (CertificateException e) {
//                e.printStackTrace();
//        }
//
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            byte[] publicKey = md.digest(c.getEncoded());
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] md5publicKey = md5.digest(c.getEncoded());
//            String hexString = getHexString(publicKey);
//            String md5HexString = getHexString(md5publicKey);
//
//            Log.d("Example", "Cer: "+ hexString.toString());
//            Log.d("Example", "Cer: md5"+ md5HexString.toString());
//
//        } catch (NoSuchAlgorithmException e1) {
//            e1.printStackTrace();
//        } catch (CertificateEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } 
//        VersionUtility.hasNewVersion("1.2.0.1178", "2.0.0.419");
    }


    private String getHexString(byte[] publicKey) {
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<publicKey.length;i++) {
            String appendString = Integer.toHexString(0xFF & publicKey[i]);
            if(appendString.length()==1)hexString.append("0");
            hexString.append(appendString);
            }
        return hexString.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
