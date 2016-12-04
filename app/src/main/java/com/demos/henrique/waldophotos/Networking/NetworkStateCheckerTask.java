package com.demos.henrique.waldophotos.Networking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;




public class NetworkStateCheckerTask extends AsyncTask<Boolean, String, Boolean> {

    private NetworkResponseListener networkResponseListener;
    private Context hostContext;

    public NetworkStateCheckerTask(NetworkResponseListener networkResponseListener, Context ctx)
    {
        this.networkResponseListener = networkResponseListener;
        hostContext = ctx;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        return NetworkChecker.isOnline( hostContext );
    }


    @Override
    protected void onPostExecute(Boolean result)
    {
        networkResponseListener.receivedIsOnline(result);
    }


    public interface NetworkResponseListener
    {
        public void receivedIsOnline(boolean isConnected);
    }
}
