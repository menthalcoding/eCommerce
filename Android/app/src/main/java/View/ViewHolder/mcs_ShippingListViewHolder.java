package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_ShippingListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvShippingId;
	public TextView tvOrderId;
	public TextView tvAddressId;
	public TextView tvShippingMethod;
	public TextView tvStatus;
	public TextView tvShippingProvider;
	public TextView tvCost;

    public mcs_ShippingListViewHolder(View itemView) {
        super(itemView);
		tvShippingId = (TextView)itemView.findViewById(R.id.tvShippingId);
		tvOrderId = (TextView)itemView.findViewById(R.id.tvOrderId);
		tvAddressId = (TextView)itemView.findViewById(R.id.tvAddressId);
		tvShippingMethod = (TextView)itemView.findViewById(R.id.tvShippingMethod);
		tvStatus = (TextView)itemView.findViewById(R.id.tvStatus);
		tvShippingProvider = (TextView)itemView.findViewById(R.id.tvShippingProvider);
		tvCost = (TextView)itemView.findViewById(R.id.tvCost);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
