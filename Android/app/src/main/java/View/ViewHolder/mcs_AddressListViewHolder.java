package View.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerce.R;

public class mcs_AddressListViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout llContainer;
	public TextView tvAddressId;
	public TextView tvUserId;
	public TextView tvName;
	public TextView tvGPS;
	public TextView tvCountry;
	public TextView tvCity;
	public TextView tvZipCode;
	public TextView tvCountryCode;
	public TextView tvDetail;
	public TextView tvPrimary;
	public TextView tvActive;

    public mcs_AddressListViewHolder(View itemView) {
        super(itemView);
		tvAddressId = (TextView)itemView.findViewById(R.id.tvAddressId);
		tvUserId = (TextView)itemView.findViewById(R.id.tvUserId);
		tvName = (TextView)itemView.findViewById(R.id.tvName);
		tvGPS = (TextView)itemView.findViewById(R.id.tvGPS);
		tvCountry = (TextView)itemView.findViewById(R.id.tvCountry);
		tvCity = (TextView)itemView.findViewById(R.id.tvCity);
		tvZipCode = (TextView)itemView.findViewById(R.id.tvZipCode);
		tvCountryCode = (TextView)itemView.findViewById(R.id.tvCountryCode);
		tvDetail = (TextView)itemView.findViewById(R.id.tvDetail);
		tvPrimary = (TextView)itemView.findViewById(R.id.tvPrimary);
		tvActive = (TextView)itemView.findViewById(R.id.tvActive);
        llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
    }
}
