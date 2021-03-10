package com.example.android.memes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Handles API call
 * @return URL for meme
 */
public  final  class QueryUtilis {

    private static final String LOG_TAG=QueryUtilis.class.getSimpleName();

    private QueryUtilis(){

    }

    public static String imageURL(String test_url){
        URL url = createURL(test_url);

        String jsonResponse="";
        String image_URL="";
        try {
            jsonResponse=makeHTTPRequest(url);
            image_URL=ExtractJson(jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG,"IO Exception\n",e);
        }
        return image_URL;
    }

    private static URL createURL(String test_url) {
        URL url = null;
        try {
            url = new URL(test_url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL Exception\n", e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException{
        if(url==null){
            return "";
        }

        HttpURLConnection httpURLConnection=null;
        String jsonResponse="";
        InputStream inputStream=null;

        try {
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);//miliseconds
            httpURLConnection.setReadTimeout(10000);//miliseconds
            httpURLConnection.connect();
            int responseCode=httpURLConnection.getResponseCode();
            if(responseCode==200){
                 inputStream=httpURLConnection.getInputStream();
                 jsonResponse=readFromInputStream(inputStream);
            }
            else{
                Log.e(LOG_TAG,"Response Code :"+responseCode);
            }

        } catch (IOException e) {
            Log.e(LOG_TAG,"IO Exception at makeHTTPRequest\n",e);
        }
        finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder jsonResponse=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                jsonResponse.append(line);
                line=bufferedReader.readLine();
            }
        }
        return jsonResponse.toString();
    }

    private static String ExtractJson(String jsonResponse){

        String image_URL="";
        try {
            JSONObject info=new JSONObject(jsonResponse);
             image_URL=info.getString("url");
        } catch (JSONException e) {
            Log.e(LOG_TAG,"JSON Exception\n",e);
        }
        return image_URL;

    }

}
