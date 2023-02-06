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

import HttpClient.DataTransferObjects.mcs_ProductDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_ProductListActivity;
import View.ViewHolder.mcs_ProductListViewHolder;

public class mcs_ProductListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_ProductListViewHolder> {

    List<mcs_ProductDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_ProductListRecyclerViewAdapter(List<mcs_ProductDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_product_list_row_layout, parent, false);
        mcs_ProductListViewHolder holder = new mcs_ProductListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_ProductListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_ProductDto item = list.get(position);
		holder.tvProductId.setText((item.getProductId() != null ? String.valueOf(item.getProductId()) : ""));
		holder.tvName.setText((item.getName() != null ? String.valueOf(item.getName()) : ""));
		holder.tvTitle.setText((item.getTitle() != null ? String.valueOf(item.getTitle()) : ""));
		holder.tvDescription.setText((item.getDescription() != null ? String.valueOf(item.getDescription()) : ""));
		holder.tvCategoryId.setText((item.getCategoryId() != null ? String.valueOf(item.getCategoryId()) : ""));
		holder.tvPrice.setText((item.getPrice() != null ? String.valueOf(item.getPrice()) : ""));
		holder.tvDefaultPhoto.setText((item.getDefaultPhoto() != null ? String.valueOf(item.getDefaultPhoto()) : ""));
		holder.tvStatus.setText((item.getStatus() != null ? String.valueOf(item.getStatus()) : ""));
		holder.tvCreated_at.setText((item.getCreated_at() != null ? Converter.formatDate(item.getCreated_at()) : ""));
		holder.tvUpdated_at.setText((item.getUpdated_at() != null ? Converter.formatDate(item.getUpdated_at()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
