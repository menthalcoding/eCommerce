package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_CartItemListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvCartItemId;
	public TextView tvCartId;
	public TextView tvProductId;
	public TextView tvQuantity;
	public TextView tvCreated_at;
	public TextView tvUpdated_at;

    public mcs_CartItemListViewHolder(View itemView) {
        super(itemView);
		tvCartItemId = (TextView)itemView.findViewById(R.id.tvCartItemId);
		tvCartId = (TextView)itemView.findViewById(R.id.tvCartId);
		tvProductId = (TextView)itemView.findViewById(R.id.tvProductId);
		tvQuantity = (TextView)itemView.findViewById(R.id.tvQuantity);
		tvCreated_at = (TextView)itemView.findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView)itemView.findViewById(R.id.tvUpdated_at);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
