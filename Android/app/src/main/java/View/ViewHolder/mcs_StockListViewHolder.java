package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_StockListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvStockId;
	public TextView tvProductId;
	public TextView tvQuantity;
	public TextView tvEntryPrice;
	public TextView tvEntryDate;

    public mcs_StockListViewHolder(View itemView) {
        super(itemView);
		tvStockId = (TextView)itemView.findViewById(R.id.tvStockId);
		tvProductId = (TextView)itemView.findViewById(R.id.tvProductId);
		tvQuantity = (TextView)itemView.findViewById(R.id.tvQuantity);
		tvEntryPrice = (TextView)itemView.findViewById(R.id.tvEntryPrice);
		tvEntryDate = (TextView)itemView.findViewById(R.id.tvEntryDate);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
