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

import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_ProductPhotoListActivity;
import View.ViewHolder.mcs_ProductPhotoListViewHolder;

public class mcs_ProductPhotoListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_ProductPhotoListViewHolder> {

    List<mcs_ProductPhotoDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_ProductPhotoListRecyclerViewAdapter(List<mcs_ProductPhotoDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_ProductPhotoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_productphoto_list_row_layout, parent, false);
        mcs_ProductPhotoListViewHolder holder = new mcs_ProductPhotoListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_ProductPhotoListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_ProductPhotoDto item = list.get(position);
		holder.tvProductPhotoId.setText((item.getProductPhotoId() != null ? String.valueOf(item.getProductPhotoId()) : ""));
		holder.tvProductId.setText((item.getProductId() != null ? String.valueOf(item.getProductId()) : ""));
		holder.tvUrl.setText((item.getUrl() != null ? String.valueOf(item.getUrl()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
