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

import HttpClient.DataTransferObjects.mcs_StockDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_StockListActivity;
import View.ViewHolder.mcs_StockListViewHolder;

public class mcs_StockListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_StockListViewHolder> {

    List<mcs_StockDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_StockListRecyclerViewAdapter(List<mcs_StockDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_StockListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_stock_list_row_layout, parent, false);
        mcs_StockListViewHolder holder = new mcs_StockListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_StockListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_StockDto item = list.get(position);
		holder.tvStockId.setText((item.getStockId() != null ? String.valueOf(item.getStockId()) : ""));
		holder.tvProductId.setText((item.getProductId() != null ? String.valueOf(item.getProductId()) : ""));
		holder.tvQuantity.setText((item.getQuantity() != null ? String.valueOf(item.getQuantity()) : ""));
		holder.tvEntryPrice.setText((item.getEntryPrice() != null ? String.valueOf(item.getEntryPrice()) : ""));
		holder.tvEntryDate.setText((item.getEntryDate() != null ? Converter.formatDate(item.getEntryDate()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
