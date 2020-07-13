package com.evidon.privacy_caas.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.evidon.privacy_caas.R;
import com.evidon.privacy_caas.activities.DSARActivity;
import com.evidon.privacy_caas.activities.PrivacyPreferencesActivity;
import com.evidon.privacy_caas.activities.VendorListActivity;
import com.evidon.privacy_caas.dialogs.DoNotSellDialog;
import com.evidon.privacy_caas.utils.PreferencesUtility;


public class PrivacySettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String PRIVACY_PRERENCES = "privacy_preferences_selector";
    private static final String AGE_GATE = "age_gate_selector";

    private static final String INFORMATION_WE_COLLECT = "information_we_collect_selector";

    private static final String DO_NOT_SELL = "do_not_sell_selector";
    private static final String DSAR = "dsar_selector";

    private PreferencesUtility mPreferences;
    private Preference privacyPreferenceSelector, ageGateSelector, informationWeCollectSelector, doNotSellSelector, dsarSelector;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.privacy_preferences);

        mPreferences = PreferencesUtility.getInstance(getActivity());

        privacyPreferenceSelector = findPreference(PRIVACY_PRERENCES);
        ageGateSelector = findPreference(AGE_GATE);
        informationWeCollectSelector = findPreference(INFORMATION_WE_COLLECT);
        doNotSellSelector = findPreference(DO_NOT_SELL);
        dsarSelector = findPreference(DSAR);


        setPreferenceClickListeners();


    }

    private void setPreferenceClickListeners() {

        privacyPreferenceSelector.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), VendorListActivity.class));
                return true;
            }
        });

        doNotSellSelector.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DoNotSellDialog doNotSellDialog = new DoNotSellDialog();
                doNotSellDialog.setTargetFragment(PrivacySettingsFragment.this, 0);
                doNotSellDialog.show(getFragmentManager(), DoNotSellDialog.FRAGMENT_NAME);
                return true;
            }
        });
// TODO
//        ageGateSelector.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                AgeVerificationDialog ageVerificationDialog = new AgeVerificationDialog();
//                ageVerificationDialog.setTargetFragment(PrivacySettingsFragment.this, 0);
//                ageVerificationDialog.show(getFragmentManager(), AgeVerificationDialog.FRAGMENT_NAME);
//                return true;
//
//
//            }
//        });

        dsarSelector.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), DSARActivity.class));
                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
