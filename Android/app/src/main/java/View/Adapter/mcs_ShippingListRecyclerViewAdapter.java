package View.Adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import HttpClient.DataTransferObjects.mcs_ShippingDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_ShippingListActivity;
import View.ViewHolder.mcs_ShippingListViewHolder;

public class mcs_ShippingListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_ShippingListViewHolder> {

    List<mcs_ShippingDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_ShippingListRecyclerViewAdapter(List<mcs_ShippingDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_ShippingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_shipping_list_row_layout, parent, false);
        mcs_ShippingListViewHolder holder = new mcs_ShippingListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_ShippingListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_ShippingDto item = list.get(position);
		holder.tvShippingId.setText((item.getShippingId() != null ? String.valueOf(item.getShippingId()) : ""));
		holder.tvOrderId.setText((item.getOrderId() != null ? String.valueOf(item.getOrderId()) : ""));
		holder.tvAddressId.setText((item.getAddressId() != null ? String.valueOf(item.getAddressId()) : ""));
		holder.tvShippingMethod.setText((item.getShippingMethod() != null ? String.valueOf(item.getShippingMethod()) : ""));
		holder.tvStatus.setText((item.getStatus() != null ? String.valueOf(item.getStatus()) : ""));
		holder.tvShippingProvider.setText((item.getShippingProvider() != null ? String.valueOf(item.getShippingProvider()) : ""));
		holder.tvCost.setText((item.getCost() != null ? String.valueOf(item.getCost()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
