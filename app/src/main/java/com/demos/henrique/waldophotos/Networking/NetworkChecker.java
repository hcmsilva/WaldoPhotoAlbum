package com.demos.henrique.waldophotos.Networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;




public class NetworkChecker {

    public static boolean hasNetworkConnection(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }


    public static boolean hasAccessToInternet() throws InterruptedException, IOException
    {
        String command = "ping -c 1 -w 4 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }


    public static boolean isOnline(Context ctx)
    {
        try {

            if(hasNetworkConnection(ctx)  && hasAccessToInternet())
                return true;
            else
                return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
