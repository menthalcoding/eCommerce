package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_ProductListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvProductId;
	public TextView tvName;
	public TextView tvTitle;
	public TextView tvDescription;
	public TextView tvCategoryId;
	public TextView tvPrice;
	public TextView tvDefaultPhoto;
	public TextView tvStatus;
	public TextView tvCreated_at;
	public TextView tvUpdated_at;

    public mcs_ProductListViewHolder(View itemView) {
        super(itemView);
		tvProductId = (TextView)itemView.findViewById(R.id.tvProductId);
		tvName = (TextView)itemView.findViewById(R.id.tvName);
		tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
		tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
		tvCategoryId = (TextView)itemView.findViewById(R.id.tvCategoryId);
		tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
		tvDefaultPhoto = (TextView)itemView.findViewById(R.id.tvDefaultPhoto);
		tvStatus = (TextView)itemView.findViewById(R.id.tvStatus);
		tvCreated_at = (TextView)itemView.findViewById(R.id.tvCreated_at);
		tvUpdated_at = (TextView)itemView.findViewById(R.id.tvUpdated_at);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
