package com.evidon.privacy_caas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evidon.privacy_caas.R;


public class PrivacyPreferencesActivity extends AppCompatActivity {

    TextView vendor_link1, vendor_link2, vendor_link3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_preferences);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Privacy preferences");

//        vendor_link1 = findViewById(R.id.vendor_link);
//        vendor_link2 = findViewById(R.id.vendor_link2);
//        vendor_link3 = findViewById(R.id.vendor_link3);



    }

    public void vendorOnClick(View v){
        Intent intent = new Intent(this, VendorListActivity.class);
        startActivity(intent);
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
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_privacy_preferences);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setTitle("Privacy Preferences");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, countries);
//
//        /** Setting the arrayadapter for this listview  **/
//        getListView().setAdapter(adapter);
//
//        /** Defining checkbox click event listener **/
//        OnClickListener clickListener = new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                CheckBox chk = (CheckBox) v;
//                int itemCount = getListView().getCount();
//                for (int i = 0; i < itemCount; i++) {
//                    getListView().setItemChecked(i, chk.isChecked());
//                }
//            }
//        };
//
//
//        /** Defining click event listener for the listitem checkbox */
//        OnItemClickListener itemClickListener = new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                CheckBox chk = (CheckBox) findViewById(R.id.chkAll);
//                int checkedItemCount = getCheckedItemCount();
//
//                if (getListView().getCount() == checkedItemCount)
//                    chk.setChecked(true);
//                else
//                    chk.setChecked(false);
//            }
//        };
//
//        /** Getting reference to checkbox available in the main.xml layout */
//        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAll);
//
//        /** Setting a click listener for the checkbox **/
//        chkAll.setOnClickListener(clickListener);
//
//        /** Setting a click listener for the listitem checkbox **/
//        getListView().setOnItemClickListener(itemClickListener);
//
//    }
//
//    /**
//     * Returns the number of checked items
//     */
//    private int getCheckedItemCount() {
//        int cnt = 0;
//        SparseBooleanArray positions = getListView().getCheckedItemPositions();
//        int itemCount = getListView().getCount();
//
//        for (int i = 0; i < itemCount; i++) {
//            if (positions.get(i))
//                cnt++;
//        }
//        return cnt;
//    }
//}
