package com.evidon.privacy.caassdk.callbacks;

import org.json.JSONArray;

public interface JSONGetterCallback {
    public void onTaskDone(String statusUpdate);
    public void onGetCaaSDone(JSONArray getCaaSResp);
}
