package com.evidon.privacy_caas.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.evidon.privacy_caas.R;


import com.evidon.privacy_caas.VendorListViewItemDTO;
import com.evidon.privacy_caas.VendorListViewItemViewHolder;

import java.util.List;

public class VendorArrayAdapter extends BaseAdapter {

    private List<VendorListViewItemDTO> vendorListViewItemDtoList = null;

    private Context ctx = null;

    public VendorArrayAdapter(Context ctx, List<VendorListViewItemDTO> vendorListViewItemDtoList) {
        this.ctx = ctx;
        this.vendorListViewItemDtoList = vendorListViewItemDtoList;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(vendorListViewItemDtoList!=null)
        {
            ret = vendorListViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(vendorListViewItemDtoList!=null) {
            ret = vendorListViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        VendorListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (VendorListViewItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.vendor_list_item, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.opt_in_out_checkbox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.vendor_name);

            viewHolder = new VendorListViewItemViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        VendorListViewItemDTO vendorListViewItemDto = vendorListViewItemDtoList.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(vendorListViewItemDto.isChecked());
        viewHolder.getItemTextView().setText(vendorListViewItemDto.getItemText());

        return convertView;
    }
}