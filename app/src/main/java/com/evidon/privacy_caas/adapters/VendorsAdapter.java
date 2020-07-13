package com.evidon.privacy_caas.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evidon.privacy_caas.activities.VendorListActivity;
import com.evidon.privacy_caas.R;
import com.evidon.privacy_caas.activities.VendorDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VendorsAdapter extends BaseAdapter {

    private static LayoutInflater mInflater = null;
    private static final String TAG = "SDK_CustomListAdapter";
    private JSONArray vendorsList = new JSONArray();
    private Context context;
    int selectedPos = -1;
    int clickedPos = -1;

    public static class ViewHolder {
        public TextView vendorName;
        public CheckBox optInOutCheckBox;
        public Boolean isOn;
        LinearLayout llContainer;
    }

    public VendorsAdapter(Context context, int resource, JSONArray vendorsList) {
        super();
        this.context = context;
        this.vendorsList = vendorsList;
        this.mInflater = ((Activity)context).getLayoutInflater();
        this.notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (vendorsList != null) {
            count = vendorsList.length();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder holder;
        JSONObject venodr = null;
        try {
            venodr = (JSONObject) vendorsList.get(position);


            itemView = mInflater.inflate(R.layout.vendor_list_item, parent, false);

            holder = new ViewHolder();
            holder.vendorName = (TextView) itemView.findViewById(R.id.vendor_name);
            holder.optInOutCheckBox = (CheckBox) itemView.findViewById(R.id.opt_in_out_checkbox);
            holder.llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);

            holder.llContainer.setTag(position);
            holder.optInOutCheckBox.setTag(position);
            if (((VendorListActivity)context).listSelected.contains(position)){
                holder.optInOutCheckBox.setChecked(true);
            }else
                holder.optInOutCheckBox.setChecked(false);
            holder.vendorName.setText(venodr.optString("name"));

            holder.llContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedPos = (int) view.getTag();
                    try {
                        JSONObject object = (JSONObject) vendorsList.get(clickedPos);
                        Intent in = new Intent(context, VendorDetailsActivity.class);

                        in.putExtra("name", object.optString("name"));
                        in.putExtra("desc", object.optString("companyDescription"));
                        in.putExtra("isSelected", (((VendorListActivity)context).listSelected.contains(clickedPos)) ? true : false);
                        in.putExtra("logoURL", object.optString("logoUrl"));
                        in.putExtra("privacyUrl", object.optString("privacyUrl"));
                        context.startActivity(in);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


            holder.optInOutCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    selectedPos = (int) compoundButton.getTag();
                    if (((VendorListActivity)context).listSelected.contains(selectedPos)){
                        ((VendorListActivity)context).listSelected.remove(selectedPos);
                    }else{
                        ((VendorListActivity)context).listSelected.add(selectedPos);
                    }

//                ((VendorListActivity)context).listSelected = listSelected;
                    notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemView;
    }
}
