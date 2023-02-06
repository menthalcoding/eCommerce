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

import HttpClient.DataTransferObjects.mcs_CartItemDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_CartItemListActivity;
import View.ViewHolder.mcs_CartItemListViewHolder;

public class mcs_CartItemListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_CartItemListViewHolder> {

    List<mcs_CartItemDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_CartItemListRecyclerViewAdapter(List<mcs_CartItemDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_CartItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_cartıtem_list_row_layout, parent, false);
        mcs_CartItemListViewHolder holder = new mcs_CartItemListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_CartItemListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_CartItemDto item = list.get(position);
		holder.tvCartItemId.setText((item.getCartItemId() != null ? String.valueOf(item.getCartItemId()) : ""));
		holder.tvCartId.setText((item.getCartId() != null ? String.valueOf(item.getCartId()) : ""));
		holder.tvProductId.setText((item.getProductId() != null ? String.valueOf(item.getProductId()) : ""));
		holder.tvQuantity.setText((item.getQuantity() != null ? String.valueOf(item.getQuantity()) : ""));
		holder.tvCreated_at.setText((item.getCreated_at() != null ? Converter.formatDate(item.getCreated_at()) : ""));
		holder.tvUpdated_at.setText((item.getUpdated_at() != null ? Converter.formatDate(item.getUpdated_at()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
