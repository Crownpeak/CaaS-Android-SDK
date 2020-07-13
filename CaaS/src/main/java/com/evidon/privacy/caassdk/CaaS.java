package com.evidon.privacy.caassdk;

import android.app.Activity;
import android.content.Context;

import com.evidon.privacy.caassdk.callbacks.CaaS_Callback;
import com.evidon.privacy.caassdk.callbacks.JSONGetterCallback;
import com.evidon.privacy.caassdk.model.CaaSData;

import org.json.JSONArray;
import org.json.JSONObject;

public class CaaS implements JSONGetterCallback{
    Context context;
    CaaS_Callback caas_callback;


    public void postCaaS(Activity activity, String userID, int companyId, String applicationId, String recordType, JSONObject payload, String caas_token, CaaS_Callback caas_callback){
        this.context = activity;
        this.caas_callback = caas_callback;
        callPostMethod(activity, userID, companyId, applicationId, recordType, payload, caas_token);
    }

    public void getCaaS(Activity activity, int companyId, String caas_token, CaaS_Callback caas_callback){
        this.context = activity;
        this.caas_callback = caas_callback;
        callGetMethod(activity, companyId, caas_token);
    }

    private void callPostMethod(Activity activity, String userID, int companyId,String applicationId,  String recordType, JSONObject payload, String caas_token) {
        CaaSData temp = new CaaSData();
        temp.callStorePayload(CaaS.this, (Activity) context, userID, companyId, applicationId, recordType, payload, caas_token);
    }

    private void callGetMethod(Activity activity, int companyId, String caas_token) {
        CaaSData temp = new CaaSData();
        temp.callGetPayload(CaaS.this, (Activity) context, companyId, caas_token);
    }

    @Override
    public void onTaskDone(String status) {
        caas_callback.onSuccess(status);
    }

    @Override
    public void onGetCaaSDone(JSONArray getCaaSResp) {
        caas_callback.onGetCaaSDone(getCaaSResp);
    }
}
