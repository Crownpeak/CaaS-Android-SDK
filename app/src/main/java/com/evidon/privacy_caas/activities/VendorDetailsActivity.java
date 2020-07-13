package com.evidon.privacy_caas.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.evidon.privacy_caas.R;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

public class VendorDetailsActivity extends AppCompatActivity {


    TextView tvDesc, vendorName, textView_learn_more_url;
    ImageView imageView_trackerLogo;
    CheckBox optInOutCheckBox;
    public int selectedPos = -1;
    JSONArray getCaasResp;
    protected ProgressDialog pDialog;
    String name, desc, logoURL, privacyUrl;
    boolean isSelected;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_detail);

        tvDesc = findViewById(R.id.textView_trackerDescription);
//        vendorName = (TextView) findViewById(R.id.vendor_name);
        textView_learn_more_url = (TextView) findViewById(R.id.textView_learn_more_url);
        optInOutCheckBox = (CheckBox) findViewById(R.id.opt_in_out_checkbox);
        imageView_trackerLogo = (ImageView) findViewById(R.id.imageView_trackerLogo);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().hasExtra("name")){
            logoURL = getIntent().getStringExtra("logoURL");
            privacyUrl = getIntent().getStringExtra("privacyUrl");
            name = getIntent().getStringExtra("name");
            desc = getIntent().getStringExtra("desc");
            isSelected = getIntent().getBooleanExtra("isSelected",false);

            textView_learn_more_url.setText(privacyUrl);
//            vendorName.setText(name);
            tvDesc.setText(desc);
            optInOutCheckBox.setChecked(isSelected);
        }
        getSupportActionBar().setTitle(name);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_menu_camera);
        requestOptions.error(R.drawable.ic_menu_share);

        try {
            logoURL = logoURL.replaceAll(" ", "%20");

            Glide.with(this)
                    .load(logoURL /*+ "?auth=" + userDetails.getString("sessionkey")*/)
                    .apply(requestOptions)
                    .into(imageView_trackerLogo);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Glide.with(VendorDetailsActivity.this).load("https://c.evidon.com/logos/1000mercis.png").into(imageView_trackerLogo);


    }


    public void showProgress(String message){
        if(VendorDetailsActivity.this == null)
            return;
        if(pDialog != null && pDialog.isShowing()){
            pDialog.hide();
            pDialog = null;
        }
        pDialog = new ProgressDialog(VendorDetailsActivity.this);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideProgress(){
        if(VendorDetailsActivity.this == null)
            return;
        if(pDialog != null && pDialog.isShowing())
            pDialog.hide();
        pDialog = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
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