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

import HttpClient.DataTransferObjects.mcs_OrderDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_OrderListActivity;
import View.ViewHolder.mcs_OrderListViewHolder;

public class mcs_OrderListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_OrderListViewHolder> {

    List<mcs_OrderDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_OrderListRecyclerViewAdapter(List<mcs_OrderDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_order_list_row_layout, parent, false);
        mcs_OrderListViewHolder holder = new mcs_OrderListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_OrderListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_OrderDto item = list.get(position);
		holder.tvOrderId.setText((item.getOrderId() != null ? String.valueOf(item.getOrderId()) : ""));
		holder.tvUserId.setText((item.getUserId() != null ? String.valueOf(item.getUserId()) : ""));
		holder.tvCartId.setText((item.getCartId() != null ? String.valueOf(item.getCartId()) : ""));
		holder.tvCreated_at.setText((item.getCreated_at() != null ? Converter.formatDate(item.getCreated_at()) : ""));
		holder.tvCost.setText((item.getCost() != null ? String.valueOf(item.getCost()) : ""));
		holder.tvTax.setText((item.getTax() != null ? String.valueOf(item.getTax()) : ""));
		holder.tvTotal.setText((item.getTotal() != null ? String.valueOf(item.getTotal()) : ""));
		holder.tvPaid.setText((item.getPaid() != null ? String.valueOf(item.getPaid()) : ""));
		holder.tvCurrency.setText((item.getCurrency() != null ? String.valueOf(item.getCurrency()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
