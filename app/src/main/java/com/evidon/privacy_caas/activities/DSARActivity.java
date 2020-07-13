package com.evidon.privacy_caas.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.evidon.privacy.caassdk.CaaS;
import com.evidon.privacy.caassdk.callbacks.CaaS_Callback;
import com.evidon.privacy_caas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class DSARActivity extends AppCompatActivity implements CaaS_Callback {


    // Evidon Variables
    private static final String EVIDON_RECORD_TYPE = "dsar";
    private static final String EVIDON_TOKEN = "bd795577e02f4157ae534383bcc9a309";
    private static final String EVDION_APPLICATIONID = "5";
    private static final int EVIDON_COMPANYID = 242;
    private static final String USER_ID = "38400000-8cf0-11bd-b23e-10b96e40000d";   // get advertisingID or pass Custom UserID.

    private String action = "DSAR";
    private EditText dsar_firstname, dsar_lastname, dsar_emailid, dsar_comments;
    private RadioGroup dataOptionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dsar);
        TextView descriptionText = findViewById(R.id.descriptionTV);
        Button dsar_cancel_button = findViewById(R.id.dsar_cancel_button);
        Button dsar_submit_button = findViewById(R.id.dsar_submit_button);

        dsar_firstname = findViewById(R.id.dsar_firstname);
        dsar_lastname = findViewById(R.id.dsar_lastname);
        dsar_emailid = findViewById(R.id.dsar_emailid);
        dsar_comments = findViewById(R.id.dsar_comments);
        dataOptionsGroup = findViewById(R.id.rg_data_options);
        RadioButton firstRadioButton = (RadioButton) dataOptionsGroup.getChildAt(0);
        firstRadioButton.setChecked(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.dsar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Subject Access Request");
        action = getIntent().getAction();

        descriptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://ico.org.uk/for-organisations/guide-to-the-general-data-protection-regulation-gdpr/individual-rights/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        if (!dsar_firstname.toString().isEmpty() && !dsar_lastname.toString().isEmpty() && !dsar_emailid.toString().isEmpty())
            dsar_submit_button.setEnabled(true);
        else
            dsar_submit_button.setEnabled(false);

        dsar_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDSARRequest();
            }
        });


        dsar_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DSARActivity.super.onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitDSARRequest() {

        if (!isFieldsEmpty()) {
            final JSONObject dsarDataJsonObject = new JSONObject();
            try {
                dsarDataJsonObject.put("applicationName", "Privacy_CaaS");
                dsarDataJsonObject.put("countryId", "US");
                dsarDataJsonObject.put("languageId", "US-EN");
                dsarDataJsonObject.put("languageCode", "" + Locale.getDefault().getLanguage());
                RadioButton radioButton = dataOptionsGroup.findViewById(dataOptionsGroup.getCheckedRadioButtonId());
                dsarDataJsonObject.put("requestType", radioButton.getTag());

                dsarDataJsonObject.put("firstName", dsar_firstname.getText().toString());
                dsarDataJsonObject.put("lastName", dsar_lastname.getText().toString());

                dsarDataJsonObject.put("emailAddress", dsar_emailid.getText().toString());
                dsarDataJsonObject.put("comments", dsar_comments.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("JSONData", String.valueOf(dsarDataJsonObject));

            CaaS caas = new CaaS();
            caas.postCaaS(DSARActivity.this, USER_ID, EVIDON_COMPANYID, EVDION_APPLICATIONID,EVIDON_RECORD_TYPE,dsarDataJsonObject,EVIDON_TOKEN, DSARActivity.this);

        }
    }

    private boolean isFieldsEmpty() {
        boolean isEmpty = false;

        if (TextUtils.isEmpty(dsar_firstname.getText().toString())) {
            dsar_firstname.setError("Required");
            isEmpty = true;
        }
        if (TextUtils.isEmpty(dsar_lastname.getText().toString())) {
            dsar_lastname.setError("Required");

            isEmpty = true;
        }
        if (TextUtils.isEmpty(dsar_emailid.getText().toString())) {
            dsar_emailid.setError("Required");
            isEmpty = true;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(dsar_emailid.getText().toString()).matches()) {
                dsar_emailid.setError("Enter Valid ID");
                isEmpty = true;
            }

        }

        return isEmpty;
    }

    @Override
    public void onSuccess(String statusUpdate) {
        Toast.makeText(this, statusUpdate, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onGetCaaSDone(JSONArray getCaaSResp) {
        Toast.makeText(this, getCaaSResp.toString(), Toast.LENGTH_LONG).show();
    }
}