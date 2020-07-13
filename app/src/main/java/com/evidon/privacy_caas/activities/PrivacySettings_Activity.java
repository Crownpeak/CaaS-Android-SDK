package com.evidon.privacy_caas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.evidon.privacy_caas.R;
import com.evidon.privacy_caas.fragments.PrivacySettingsFragment;

public class PrivacySettings_Activity extends AppCompatActivity {

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        action = getIntent().getAction();

        //TODO
        //open respective fragment on selection
        getSupportActionBar().setTitle("Privacy Settings");
        PreferenceFragment fragment = new PrivacySettingsFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_privacy_container, fragment).commit();

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