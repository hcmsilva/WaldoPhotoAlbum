package com.demos.henrique.waldophotos.Networking;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;




public class NetworkStateCheckerTask extends AsyncTask<Boolean, String, Boolean> {

    private NetworkResponseListener hostFragment;

    public NetworkStateCheckerTask(NetworkResponseListener context)
    {
        hostFragment = context;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        return NetworkChecker.isOnline( ((android.support.v4.app.Fragment)hostFragment).getActivity() );
    }


    @Override
    protected void onPostExecute(Boolean result)
    {
        hostFragment.receivedIsOnline(result);
    }


    public interface NetworkResponseListener
    {
        public void receivedIsOnline(boolean isConnected);
    }
}
