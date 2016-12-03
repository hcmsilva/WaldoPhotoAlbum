package com.demos.henrique.waldophotos.Networking;


import com.demos.henrique.waldophotos.Listeners.AuthenticationListener;
import com.demos.henrique.waldophotos.Listeners.ResultListener;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;




public class NetworkRequester implements com.demos.henrique.waldophotos.Listeners.AuthenticationListener{

    private static NetworkRequester singleton = new NetworkRequester();
    private static boolean authenticated = false;



    private static String authCookie;

    private NetworkRequester()
    {
    }


    public static NetworkRequester getInstance()
    {
            return singleton;
    }

    public void authenticateNetworkRequester(AuthenticationListener authenticationListener, String authBaseUrl)
    {
        new AuthenticationTask(authenticationListener, this).execute(authBaseUrl);
    }

    public void getAlbum(String graphQlQuery, ResultListener mListener, String authCookie, String baseQueryUrl)
    {

        String requestUrl = "";

        //for debug
        //final String qHardcoded = "https://core-graphql.dev.waldo.photos/gql?query=query%7Balbum(id%3A%22YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4%22)%7Bid%2Cname%2Cphotos%7Brecords%7Bid%7D%7D%7D%7D";
        //final String q2 = "query{album(id:\"YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4\"){id,name}}";


        final String baseUrl = baseQueryUrl;

        try {
            requestUrl = baseUrl+URLEncoder.encode(graphQlQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] croppedCookie = null;
        if(authCookie != null && !authCookie.isEmpty())
            croppedCookie = authCookie.split("; ");


        new RequesterTask(mListener).execute(requestUrl, croppedCookie[0]);


        return ;
    }

    @Override
    public boolean isAuthenticated(boolean authResult, String authCookie) {
        this.authCookie = authCookie;//---------------------------->>>>>>>>>>>>>>>>>
        return authResult;
    }

}
