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

import HttpClient.DataTransferObjects.mcs_CartDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_CartListActivity;
import View.ViewHolder.mcs_CartListViewHolder;

public class mcs_CartListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_CartListViewHolder> {

    List<mcs_CartDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_CartListRecyclerViewAdapter(List<mcs_CartDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_cart_list_row_layout, parent, false);
        mcs_CartListViewHolder holder = new mcs_CartListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_CartListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_CartDto item = list.get(position);
		holder.tvCartId.setText((item.getCartId() != null ? String.valueOf(item.getCartId()) : ""));
		holder.tvUserId.setText((item.getUserId() != null ? String.valueOf(item.getUserId()) : ""));
		holder.tvStatus.setText((item.getStatus() != null ? String.valueOf(item.getStatus()) : ""));
		holder.tvCreated_at.setText((item.getCreated_at() != null ? Converter.formatDate(item.getCreated_at()) : ""));
		holder.tvUpdated_at.setText((item.getUpdated_at() != null ? Converter.formatDate(item.getUpdated_at()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
