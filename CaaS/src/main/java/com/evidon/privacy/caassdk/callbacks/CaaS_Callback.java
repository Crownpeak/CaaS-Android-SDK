package com.evidon.privacy.caassdk.callbacks;

import org.json.JSONArray;

public interface CaaS_Callback {
    public void onSuccess(String statusUpdate);
    public void onGetCaaSDone(JSONArray getCaaSResp);
}
