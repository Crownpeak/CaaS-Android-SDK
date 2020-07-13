package com.evidon.privacy_caas.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evidon.privacy.caassdk.CaaS;
import com.evidon.privacy.caassdk.callbacks.CaaS_Callback;
import com.evidon.privacy_caas.R;
import com.evidon.privacy_caas.adapters.VendorsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VendorListActivity extends AppCompatActivity implements CaaS_Callback {



    //Evidon Variables
    private static final String EVIDON_RECORD_TYPE = "Consent";
    private static final String EVIDON_TOKEN = "bd795577e02f4157ae534383bcc9a309";
    private static final String EVDION_APPLICATIONID = "5";
    private static final int EVIDON_COMPANYID = 242;
    private static final String USER_ID = "38400000-8cf0-11bd-b23e-10b96e40000d";   // get advertisingID or pass Custom UserID.

    ListView vendorListView;
    public ArrayList<Integer> listSelected = new ArrayList<>();
    JSONArray getCaaSResp;
    protected ProgressDialog pDialog;

    Button preferences_button_accept, preferences_button_decline;
    CheckBox selectAllCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vendor list");

        preferences_button_accept = findViewById(R.id.preferences_button_accept);
        preferences_button_decline = findViewById(R.id.preferences_button_decline);
        selectAllCheckBox = findViewById(R.id.select_all_checkbox);

        vendorListView = findViewById(R.id.vendor_list_view);
        preferences_button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray payloadArr = new JSONArray();

                if (listSelected != null && listSelected.size() > 0){
                    try {
                        for (int i = 0; i < getCaaSResp.length(); i++) {
                            JSONObject temp = (JSONObject) getCaaSResp.get(i);
                            JSONObject payload = new JSONObject();
                            try {
                                payload.put("vendorID", temp.optString("id"));
                                payload.put("VendorName", temp.optString("name"));
                                payload.put("logoUrl", temp.optString("logoUrl"));
                                payload.put("privacyUrl", temp.optString("privacyUrl"));
                                payload.put("isConsent", listSelected.contains(i) ? true : false);

                                payloadArr.put(payload);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    showProgress("Loading...");
                    try {
                        CaaS temp1 = new CaaS();
                        JSONObject consentJson = new JSONObject();
                        consentJson.put("", payloadArr);
                        temp1.postCaaS(VendorListActivity.this, USER_ID, EVIDON_COMPANYID, EVDION_APPLICATIONID, EVIDON_RECORD_TYPE, consentJson, EVIDON_TOKEN, VendorListActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finish();
                }else{
                    Toast.makeText(VendorListActivity.this, "Please select one", Toast.LENGTH_LONG).show();
                }

            }
        });

        preferences_button_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSelected.clear();

                selectAllCheckBox.setChecked(false);
                if (getCaaSResp != null && getCaaSResp.length() > 0){
                    VendorsAdapter vendorsAdapter = new VendorsAdapter(VendorListActivity.this, R.id.vendor_name, getCaaSResp);
                    vendorListView.setAdapter(vendorsAdapter);
                    vendorListView.setItemsCanFocus(false);
                    vendorListView.setTextFilterEnabled(true);
                }

//                pDialog.dismiss();
//                VendorListActivity.super.onBackPressed();
                finish();
            }
        });


//TODO
        selectAllCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSelected.clear();
                if(selectAllCheckBox.isChecked()) {
                    for (int i = 0; i < getCaaSResp.length(); i++) {
                        listSelected.add(i);
                    }
                }

                if (getCaaSResp != null && getCaaSResp.length() > 0){
                    VendorsAdapter vendorsAdapter = new VendorsAdapter(VendorListActivity.this, R.id.vendor_name, getCaaSResp);
                    vendorListView.setAdapter(vendorsAdapter);
                    vendorListView.setItemsCanFocus(false);
                    vendorListView.setTextFilterEnabled(true);
                }
            }
        });

        getVendorsList();
    }

    @Override
    public void onSuccess(String statusUpdate) {
        hideProgress();
        Toast.makeText(this, statusUpdate, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetCaaSDone(JSONArray getCaaSResp) {
        hideProgress();
        this.getCaaSResp = getCaaSResp;
        if (this.getCaaSResp != null && this.getCaaSResp.length() > 0){
            VendorsAdapter vendorsAdapter = new VendorsAdapter(VendorListActivity.this, R.id.vendor_name, getCaaSResp);
            vendorListView.setAdapter(vendorsAdapter);
            vendorListView.setItemsCanFocus(false);
            vendorListView.setTextFilterEnabled(true);
        }
    }

    public void showProgress(String message){
        if(VendorListActivity.this == null)
            return;
        if(pDialog != null && pDialog.isShowing()){
            pDialog.dismiss();
            pDialog = null;
        }
        pDialog = new ProgressDialog(VendorListActivity.this);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideProgress(){
        if(VendorListActivity.this == null)
            return;
        if(pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();

    }

    public void getVendorsList() {
        CaaS temp1 = new CaaS();
        showProgress("Loading...");
        temp1.getCaaS(VendorListActivity.this, 242,  "bd795577e02f4157ae534383bcc9a309", VendorListActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}