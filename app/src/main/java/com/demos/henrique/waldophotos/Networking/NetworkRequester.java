package com.demos.henrique.waldophotos.Networking;


import com.demos.henrique.waldophotos.Listeners.AuthenticationListener;
import com.demos.henrique.waldophotos.Listeners.ResultListener;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;




public class NetworkRequester implements com.demos.henrique.waldophotos.Listeners.AuthenticationListener{

    private static NetworkRequester singleton = new NetworkRequester();
    private static boolean authenticated = false;
    //private static OkHttpClient httpClient;
    private final static String url = "https://auth.dev.waldo.photos/";
    private static String authCookie;

    private NetworkRequester()
    {
    }


    public static NetworkRequester getInstance()
    {
            return singleton;
    }

    public void authenticateNetworkRequester(AuthenticationListener authenticationListener)
    {
        new AuthenticationTask(authenticationListener, this).execute(url);
    }

    public void getAlbum(String graphQlQuery, ResultListener mListener, String authCookie)
    {

        String requestUrl = "";

        //for debug
        //final String qHardcoded = "https://core-graphql.dev.waldo.photos/gql?query=query%7Balbum(id%3A%22YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4%22)%7Bid%2Cname%2Cphotos%7Brecords%7Bid%7D%7D%7D%7D";
        //final String q2 = "query{album(id:\"YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4\"){id,name}}";


        final String baseUrl = "https://core-graphql.dev.waldo.photos/gql?query=";

        try {
            requestUrl = baseUrl+URLEncoder.encode(graphQlQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] tmp = authCookie.split("; ");


        new RequesterTask(mListener).execute(requestUrl, tmp[0]);


        return ;
    }

    @Override
    public boolean isAuthenticated(boolean authResult, String authCookie) {
        this.authCookie = authCookie;//---------------------------->>>>>>>>>>>>>>>>>
        return authResult;
    }

}
