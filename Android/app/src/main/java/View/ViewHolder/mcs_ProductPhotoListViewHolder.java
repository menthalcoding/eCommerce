package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_ProductPhotoListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvProductPhotoId;
	public TextView tvProductId;
	public TextView tvUrl;

    public mcs_ProductPhotoListViewHolder(View itemView) {
        super(itemView);
		tvProductPhotoId = (TextView)itemView.findViewById(R.id.tvProductPhotoId);
		tvProductId = (TextView)itemView.findViewById(R.id.tvProductId);
		tvUrl = (TextView)itemView.findViewById(R.id.tvUrl);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
