package com.evidon.privacy.caassdk.utils;

import android.content.res.Resources;
import android.util.Log;

import com.evidon.privacy.caassdk.model.CaaSData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class ServiceHandler {
    private static final String TAG = "SDK_ServiceHandler";
    private static int httpConnectTimeout;	// In millis
    private static int httpReadTimeout;	// In millis
    private static String caas_token;

    public ServiceHandler() {
        try {
            httpConnectTimeout = 15000; //CaaSData.appContext.getResources().getInteger(R.integer.evidon_http_connect_timeout);
            httpReadTimeout = 10000; //CaaSData.appContext.getResources().getInteger(R.integer.evidon_http_read_timeout);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Getting req timeout", e);
        }
    }

    public static String getRequest(String urlVal,int companyID, String caas_token) {
        ServiceHandler.caas_token = caas_token;
        String result = getRequest(urlVal, companyID);
        return result;
    }

    public static String getRequest(String urlVal,int companyID) {
        String Content = null;
        BufferedReader bufferedReader = null;
        try {

            URL url = new URL(urlVal);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(httpConnectTimeout);
            httpURLConnection.setReadTimeout(httpReadTimeout);

            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            SSLEngine engine = sslContext.createSSLEngine();

            if (CaaSData.usingToken && caas_token != null) {
                httpURLConnection.setRequestProperty("Token" , caas_token);
                httpURLConnection.setRequestProperty("companyId" , companyID+"");
            }

            bufferedReader = new BufferedReader(new InputStreamReader(
                    httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Content = stringBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error in http get request", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "Closing reader", e);
            }
        }
        return Content;
    }

    public static String postRequest(String urlStr, JSONObject inputJson, String caas_token) {
        ServiceHandler.caas_token = caas_token;
        String result = null;
        try{
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(httpConnectTimeout);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            if (CaaSData.usingToken && caas_token != null) {
                httpURLConnection.setRequestProperty("Token", caas_token);
            }
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(inputJson.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int statusCode = httpURLConnection.getResponseCode();
            if(statusCode ==  204) {
                result = "204";
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }*/
//                result = stringBuilder.toString();
            }else{
                result = ""+ statusCode;
            }
        }catch(Exception e){
            Log.e(TAG, "Error in http post request", e);
        }
        return result;
    }

}

