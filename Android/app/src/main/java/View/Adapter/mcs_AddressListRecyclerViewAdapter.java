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

import HttpClient.DataTransferObjects.mcs_AddressDto;
import Util.Constant;
import Util.Converter;
import View.UI.mcs_AddressListActivity;
import View.ViewHolder.mcs_AddressListViewHolder;

public class mcs_AddressListRecyclerViewAdapter extends RecyclerView.Adapter<mcs_AddressListViewHolder> {

    List<mcs_AddressDto> list = Collections.emptyList();
    Context context;
    Activity activity;
    boolean isLive;

    public mcs_AddressListRecyclerViewAdapter(List<mcs_AddressDto> data, Application application, boolean isLive) {
        this.list = data;
        this.context = application;
        this.isLive = isLive;
    }

    @NonNull
    @Override
    public mcs_AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcs_address_list_row_layout, parent, false);
        mcs_AddressListViewHolder holder = new mcs_AddressListViewHolder(v);
        //context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull mcs_AddressListViewHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        mcs_AddressDto item = list.get(position);
		holder.tvAddressId.setText((item.getAddressId() != null ? String.valueOf(item.getAddressId()) : ""));
		holder.tvUserId.setText((item.getUserId() != null ? String.valueOf(item.getUserId()) : ""));
		holder.tvName.setText((item.getName() != null ? String.valueOf(item.getName()) : ""));
		holder.tvGPS.setText((item.getGPS() != null ? String.valueOf(item.getGPS()) : ""));
		holder.tvCountry.setText((item.getCountry() != null ? String.valueOf(item.getCountry()) : ""));
		holder.tvCity.setText((item.getCity() != null ? String.valueOf(item.getCity()) : ""));
		holder.tvZipCode.setText((item.getZipCode() != null ? String.valueOf(item.getZipCode()) : ""));
		holder.tvCountryCode.setText((item.getCountryCode() != null ? String.valueOf(item.getCountryCode()) : ""));
		holder.tvDetail.setText((item.getDetail() != null ? String.valueOf(item.getDetail()) : ""));
		holder.tvPrimary.setText((item.getPrimary() != null ? String.valueOf(item.getPrimary()) : ""));
		holder.tvActive.setText((item.getActive() != null ? String.valueOf(item.getActive()) : ""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
