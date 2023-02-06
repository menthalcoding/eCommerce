package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_ProductPhotoDto {

	@SerializedName("productPhotoId")
	@Expose
	private String ProductPhotoId;

	@SerializedName("productId")
	@Expose
	private Integer ProductId;

	@SerializedName("url")
	@Expose
	private String Url;

	public String getProductPhotoId() { return (ProductPhotoId == null ? new String() : ProductPhotoId); }

	public void setProductPhotoId(String ProductPhotoId) { this.ProductPhotoId = ProductPhotoId; }

	public Integer getProductId() { return ProductId; }

	public void setProductId(Integer ProductId) { this.ProductId = ProductId; }

	public String getUrl() { return (Url == null ? new String() : Url); }

	public void setUrl(String Url) { this.Url = Url; }

}