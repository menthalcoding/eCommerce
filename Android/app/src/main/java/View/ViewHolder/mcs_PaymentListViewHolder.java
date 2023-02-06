package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_PaymentListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvPaymentId;
	public TextView tvUserId;
	public TextView tvPaymentType;
	public TextView tvCreated_at;
	public TextView tvAmount;
	public TextView tvCurrency;
	public TextView tvOrderId;
	public TextView tvAddressId;

    public mcs_PaymentListViewHolder(View itemView) {
        super(itemView);
		tvPaymentId = (TextView)itemView.findViewById(R.id.tvPaymentId);
		tvUserId = (TextView)itemView.findViewById(R.id.tvUserId);
		tvPaymentType = (TextView)itemView.findViewById(R.id.tvPaymentType);
		tvCreated_at = (TextView)itemView.findViewById(R.id.tvCreated_at);
		tvAmount = (TextView)itemView.findViewById(R.id.tvAmount);
		tvCurrency = (TextView)itemView.findViewById(R.id.tvCurrency);
		tvOrderId = (TextView)itemView.findViewById(R.id.tvOrderId);
		tvAddressId = (TextView)itemView.findViewById(R.id.tvAddressId);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
