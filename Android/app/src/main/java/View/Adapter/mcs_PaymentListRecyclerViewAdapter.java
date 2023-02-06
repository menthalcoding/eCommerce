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

import HttpClient.DataTransferObjects.mcs_PaymentDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_PaymentListActivity;
import View.ViewHolder.mcs_PaymentListViewHolder;

public class mcs_PaymentListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_PaymentListViewHolder> {

    List<mcs_PaymentDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_PaymentListRecyclerViewAdapter(List<mcs_PaymentDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_payment_list_row_layout, parent, false);
        mcs_PaymentListViewHolder holder = new mcs_PaymentListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_PaymentListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_PaymentDto item = list.get(position);
		holder.tvPaymentId.setText((item.getPaymentId() != null ? String.valueOf(item.getPaymentId()) : ""));
		holder.tvUserId.setText((item.getUserId() != null ? String.valueOf(item.getUserId()) : ""));
		holder.tvPaymentType.setText((item.getPaymentType() != null ? String.valueOf(item.getPaymentType()) : ""));
		holder.tvCreated_at.setText((item.getCreated_at() != null ? Converter.formatDate(item.getCreated_at()) : ""));
		holder.tvAmount.setText((item.getAmount() != null ? String.valueOf(item.getAmount()) : ""));
		holder.tvCurrency.setText((item.getCurrency() != null ? String.valueOf(item.getCurrency()) : ""));
		holder.tvOrderId.setText((item.getOrderId() != null ? String.valueOf(item.getOrderId()) : ""));
		holder.tvAddressId.setText((item.getAddressId() != null ? String.valueOf(item.getAddressId()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
