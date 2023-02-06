package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_CartListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvCartId;
	public TextView tvUserId;
	public TextView tvStatus;
	public TextView tvCreated_at;
	public TextView tvUpdated_at;

    public mcs_CartListViewHolder(View itemView) {
        super(itemView);
		tvCartId = (TextView)itemView.findViewById(R.id.tvCartId);
		tvUserId = (TextView)itemView.findViewById(R.id.tvUserId);
		tvStatus = (TextView)itemView.findViewById(R.id.tvStatus);
		tvCreated_at = (TextView)itemView.findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView)itemView.findViewById(R.id.tvUpdated_at);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
