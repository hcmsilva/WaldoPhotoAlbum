package com.demos.henrique.waldophotos.Model.Networking;

import android.os.AsyncTask;
import com.demos.henrique.waldophotos.Model.Listeners.AuthenticationListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;


public class AuthenticationTask extends AsyncTask<String, String, Boolean> {


    private AuthenticationListener mListener;
    private AuthenticationListener mNetworkRequester;
    Map<String, List<String>> headers;
    String cookie;
    boolean authenticated = false;





    public AuthenticationTask(AuthenticationListener mListener, AuthenticationListener singletonNReq)
    {

        this.mListener = mListener;
        this.mNetworkRequester = singletonNReq;
    }


    @Override
    protected Boolean doInBackground(String... params) {

        InputStream is = null;

        try {



            URL url = new URL(params[0]);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);



            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("dest=%2F&username=andy&password=1234");
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            is = conn.getInputStream();
            headers = conn.getHeaderFields();
            cookie = conn.getHeaderField("Set-Cookie");

            if(cookie != null && !cookie.isEmpty())
                authenticated = true;





        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return authenticated;


    }



    @Override
    protected void onPostExecute(Boolean result)
    {
        mListener.isAuthenticated(result, cookie);
        mNetworkRequester.isAuthenticated(result, cookie);
    }



}
