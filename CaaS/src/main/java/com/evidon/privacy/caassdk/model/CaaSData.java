package com.evidon.privacy.caassdk.model;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.evidon.privacy.caassdk.callbacks.JSONGetterCallback;
import com.evidon.privacy.caassdk.utils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CaaSData {

    private static final String TAG = "CaaSData";
    private Activity activity;
    private final Object waitObj = new Object();

    public static Context appContext;
    public static boolean usingToken = true;



    // Init
    public void callStorePayload(final JSONGetterCallback mJSONGetterCallback, Activity activity, String userID, int companyId,String applicationId,  String recordType, JSONObject payload, String caas_token) {
        this.activity = activity;
        appContext = activity;
        synchronized(waitObj) {
            JSONPost mJSONPost = new JSONPost(mJSONGetterCallback, userID, companyId, applicationId, recordType, payload, caas_token);
            mJSONPost.execute();
        }
    }

    public void callGetPayload(final JSONGetterCallback mJSONGetterCallback, Activity activity, int companyId, String caas_token) {
        this.activity = activity;
        appContext = activity;
        synchronized(waitObj) {
            JSONGetter mJSONGetter = new JSONGetter(mJSONGetterCallback, companyId, caas_token);
            mJSONGetter.execute();
        }
    }


    private class JSONPost extends AsyncTask<Void, Void, String> {
        private JSONGetterCallback mJSONGetterCallback;
        int companyId;
        String userID, applicationId,  recordType, caas_token;
        JSONObject payload;

        public JSONPost(JSONGetterCallback mJSONGetterCallback, String userID, int companyId,String applicationId,  String recordType, JSONObject payload, String caas_token) {
            this.mJSONGetterCallback = mJSONGetterCallback;
            this.userID = userID;
            this.companyId = companyId;
            this.applicationId = applicationId;
            this.recordType = recordType;
            this.caas_token = caas_token;
            this.payload = payload;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();

            String url = "https://i.evidondev.com/api/v1/";
            String jsonStr = "";

            JSONObject postDataParams = new JSONObject();
            try {
                postDataParams.put("c", companyId);
                postDataParams.put("r", recordType);
                postDataParams.put("u", userID);
                postDataParams.put("a", applicationId);
                postDataParams.put("d", payload);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonStr = serviceHandler.postRequest(url, postDataParams, caas_token);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            // End the wait-object
            synchronized(waitObj) {
                waitObj.notifyAll();
            }
            if (mJSONGetterCallback != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mJSONGetterCallback.onTaskDone(result);
                    }
                });
            }
        }
    }

    private class JSONGetter extends AsyncTask<Void, Void, String> {
        private JSONGetterCallback mJSONGetterCallback;
        int companyId;
        String caas_token;

        public JSONGetter(JSONGetterCallback mJSONGetterCallback,int companyId, String caas_token) {
            this.mJSONGetterCallback = mJSONGetterCallback;
            this.companyId = companyId;
            this.caas_token = caas_token;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler serviceHandler = new ServiceHandler();

            String url = "https://caasapi.evidondev.com/api/vendor";
            String jsonStr = "";
            jsonStr = serviceHandler.getRequest(url, companyId, caas_token);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            jsonArray = prepareDummyData();

            // End the wait-object
            synchronized(waitObj) {
                waitObj.notifyAll();
            }
            if (mJSONGetterCallback != null) {
                final JSONArray finalJsonArray = jsonArray;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mJSONGetterCallback.onGetCaaSDone(finalJsonArray);
                    }
                });
            }
        }
    }

    private JSONArray prepareDummyData(){
        JSONArray tempArr = new JSONArray();
        try{

            JSONObject temp1 = new JSONObject();
            temp1.put("id", 3640);
            temp1.put("name", "1 by AOL (FKA Vidible)");
            temp1.put("logoUrl", "https://c.evidon.com/logos/1000mercis.png");
            temp1.put("mobileSdkVendor", false);
            temp1.put("permaLink", "vidible");
            temp1.put("privacyUrl", "https://policies.oath.com/us/en/oath/privacy/index.html");
            temp1.put("companyDescription", "1000mercis Group, a pioneer in interactive advertising and marketing, provides innovative solutions for companies.");


            JSONObject temp2 = new JSONObject();
            temp2.put("id", 2262);
            temp2.put("name", "1000mercis");
            temp2.put("logoUrl", "https://c.evidon.com/logos/1000mercis.png");
            temp2.put("mobileSdkVendor", false);
            temp2.put("permaLink", "1000mercis");
            temp2.put("privacyUrl", "http://mmtro.com/privacy/fr/");
            temp2.put("companyDescription", "1000mercis Group, a pioneer in interactive advertising and marketing, provides innovative solutions for companies willing to optimise their customer acquisition and retention through interactive media (Internet, mobile phones and tablets).");

            tempArr.put(temp1);
            tempArr.put(temp2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tempArr;
    }
}
