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

    public void getAlbum(String query, ResultListener mListener, String authCookie)
    {

        String requestUrl = "";
        final String qHardcoded = "https://core-graphql.dev.waldo.photos/gql?query=query%7Balbum(id%3A%22YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4%22)%7Bid%2Cname%2Cphotos%7Brecords%7Bid%7D%7D%7D%7D";
        final String baseUrl = "https://core-graphql.dev.waldo.photos/gql?query=";
        final String q = "query{album(id:\"YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4\"){id,name,photos{records{id, urls{size_code, url}}}}}";

        //final String q = "query{album(id:\"YWxidW06YTczOGUxODctNWY1MC00NmNiLTllZjUtMDgyZTYxMGFhYWY4\"){id,name}}";
        try {
            requestUrl = baseUrl+URLEncoder.encode(q, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] tmp = authCookie.split("; ");

        // cookie that works: "_ga=GA1.2.1758442781.1480356821; __dev.waldo.auth__=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiYmZmNjNkNDYtMjYzZS00ZDBkLThmYjQtMjJhMjA3ZTBjZDFmIiwicm9sZXMiOlsiYWRtaW5pc3RyYXRvciJdLCJpc3MiOiJ3YWxkbzpjb3JlIiwiZ3JhbnRzIjpbImFsYnVtczpkZWxldGU6KiIsImFsYnVtczpjcmVhdGU6KiIsImFsYnVtczplZGl0OioiLCJhbGJ1bXM6dmlldzoqIl0sImV4cCI6MTQ4Mjk3MTM0MSwiaWF0IjoxNDgwMzc5MzQxfQ.j_O4Jm2Jx5vcC6vx29K4B_C3VpZ8h5LpZyQZy-cRBqI"
        new RequesterTask(mListener).execute(requestUrl, tmp[0]);//authCookie);


        return ;
    }

    @Override
    public boolean isAuthenticated(boolean authResult, String authCookie) {
        this.authCookie = authCookie;//---------------------------->>>>>>>>>>>>>>>>>
        return authResult;
    }

}
