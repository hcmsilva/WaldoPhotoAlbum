package com.demos.henrique.waldophotos.Networking;

import android.os.AsyncTask;

import com.demos.henrique.waldophotos.Listeners.ResultListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;




public class RequesterTask extends AsyncTask<String, String, String> {


    ResultListener mListener;


    public RequesterTask(ResultListener listener) {
        super();
        mListener = listener;


    }

    @Override
    protected String doInBackground(String... params) {

        InputStream is = null;
        String payload = null;

        try {



            URL url = new URL(params[0]);
            //URL url = new URL("https://core-graphql.dev.waldo.photos/gql?query=%7Balbum(id%3A%20%22YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4%22)%7Bid%2C%0A%20%20%20%20name%2C%0A%20%20%20%20photos%20%7B%0A%20%20%20%20%20%20records%20%7B%0A%20%20%20%20%20%20%20urls%20%7B%0A%20%20%20%20%20%20%20%20size_code%0A%20%20%20%20%20%20%20%20url%0A%20%20%20%20%20%20%20%20width%0A%20%20%20%20%20%20%20%20height%0A%20%20%20%20%20%20%20%20quality%0A%20%20%20%20%20%20%20%20mime%0A%20%20%20%20%20%20%7D%20%0A%20%20%20%20%20%20%7D%0A%20%20%20%20%7D%7D%7D");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();


            //// TODO: 04-12-2016 --> recheck timeout values
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(6000);
            conn.setInstanceFollowRedirects( true );
            conn.setRequestMethod( "GET" );
            conn.setRequestProperty( "Cookie", params[1]);
            conn.setRequestProperty( "Accept", "application/json");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Accept-Encoding", "gzip, deflate, sdch, br");

            conn.setRequestProperty( "Connection", "keep-alive");



            conn.connect();

            is = conn.getInputStream();
            int payloadSize = conn.getContentLength();
            
            //payload = readIt(is, payloadSize);
            payload = readItLowLevel(is, payloadSize);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return payload;
    }



    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int maxLen) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");

        char[] buffer = new char[maxLen];
        //reader.//.read(buffer);
        reader.read(buffer, 1351, maxLen-1351);


        stream.close();
        return new String(buffer);
    }


    public String readItLowLevel(InputStream stream, int maxLen) throws IOException
    {


        byte[] byteBuffer = new byte[maxLen];
        int bytesRead;
        int current = 0;

        do {
            bytesRead = stream.read(byteBuffer, current,
                    (byteBuffer.length - current));
            if (bytesRead >= 0)
                current += bytesRead;
        } while (bytesRead > -1);

        String pld = new String(byteBuffer);
        return pld;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mListener.getResult(result);
    }
}
