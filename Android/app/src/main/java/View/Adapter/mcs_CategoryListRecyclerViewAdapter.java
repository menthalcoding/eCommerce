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

import HttpClient.DataTransferObjects.mcs_CategoryDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_CategoryListActivity;
import View.ViewHolder.mcs_CategoryListViewHolder;

public class mcs_CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_CategoryListViewHolder> {

    List<mcs_CategoryDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_CategoryListRecyclerViewAdapter(List<mcs_CategoryDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_category_list_row_layout, parent, false);
        mcs_CategoryListViewHolder holder = new mcs_CategoryListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_CategoryListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_CategoryDto item = list.get(position);
		holder.tvCategoryId.setText((item.getCategoryId() != null ? String.valueOf(item.getCategoryId()) : ""));
		holder.tvCategoryParentId.setText((item.getCategoryParentId() != null ? String.valueOf(item.getCategoryParentId()) : ""));
		holder.tvName.setText((item.getName() != null ? String.valueOf(item.getName()) : ""));
		holder.tvDescription.setText((item.getDescription() != null ? String.valueOf(item.getDescription()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
