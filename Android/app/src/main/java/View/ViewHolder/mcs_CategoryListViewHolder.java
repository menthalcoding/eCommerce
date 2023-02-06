package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_CategoryListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvCategoryId;
	public TextView tvCategoryParentId;
	public TextView tvName;
	public TextView tvDescription;

    public mcs_CategoryListViewHolder(View itemView) {
        super(itemView);
		tvCategoryId = (TextView)itemView.findViewById(R.id.tvCategoryId);
		tvCategoryParentId = (TextView)itemView.findViewById(R.id.tvCategoryParentId);
		tvName = (TextView)itemView.findViewById(R.id.tvName);
		tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
