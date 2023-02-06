package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_OrderListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvOrderId;
	public TextView tvUserId;
	public TextView tvCartId;
	public TextView tvCreated_at;
	public TextView tvCost;
	public TextView tvTax;
	public TextView tvTotal;
	public TextView tvPaid;
	public TextView tvCurrency;

    public mcs_OrderListViewHolder(View itemView) {
        super(itemView);
		tvOrderId = (TextView)itemView.findViewById(R.id.tvOrderId);
		tvUserId = (TextView)itemView.findViewById(R.id.tvUserId);
		tvCartId = (TextView)itemView.findViewById(R.id.tvCartId);
		tvCreated_at = (TextView)itemView.findViewById(R.id.tvCreated_at);
		tvCost = (TextView)itemView.findViewById(R.id.tvCost);
		tvTax = (TextView)itemView.findViewById(R.id.tvTax);
		tvTotal = (TextView)itemView.findViewById(R.id.tvTotal);
		tvPaid = (TextView)itemView.findViewById(R.id.tvPaid);
		tvCurrency = (TextView)itemView.findViewById(R.id.tvCurrency);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
