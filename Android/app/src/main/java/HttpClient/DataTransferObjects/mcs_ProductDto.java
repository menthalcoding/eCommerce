package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_ProductDto {

	@SerializedName("productId")
	@Expose
	private Integer ProductId;

	@SerializedName("name")
	@Expose
	private String Name;

	@SerializedName("title")
	@Expose
	private String Title;

	@SerializedName("description")
	@Expose
	private String Description;

	@SerializedName("categoryId")
	@Expose
	private Integer CategoryId;

	@SerializedName("price")
	@Expose
	private BigDecimal Price;

	@SerializedName("defaultPhoto")
	@Expose
	private String DefaultPhoto;

	@SerializedName("status")
	@Expose
	private String Status;

	@SerializedName("created_at")
	@Expose
	private Date Created_at;

	@SerializedName("updated_at")
	@Expose
	private Date Updated_at;

	public Integer getProductId() { return ProductId; }

	public void setProductId(Integer ProductId) { this.ProductId = ProductId; }

	public String getName() { return (Name == null ? new String() : Name); }

	public void setName(String Name) { this.Name = Name; }

	public String getTitle() { return (Title == null ? new String() : Title); }

	public void setTitle(String Title) { this.Title = Title; }

	public String getDescription() { return (Description == null ? new String() : Description); }

	public void setDescription(String Description) { this.Description = Description; }

	public Integer getCategoryId() { return CategoryId; }

	public void setCategoryId(Integer CategoryId) { this.CategoryId = CategoryId; }

	public BigDecimal getPrice() { return Price; }

	public void setPrice(BigDecimal Price) { this.Price = Price; }

	public String getDefaultPhoto() { return (DefaultPhoto == null ? new String() : DefaultPhoto); }

	public void setDefaultPhoto(String DefaultPhoto) { this.DefaultPhoto = DefaultPhoto; }

	public String getStatus() { return (Status == null ? new String() : Status); }

	public void setStatus(String Status) { this.Status = Status; }

	public Date getCreated_at() { return Created_at; }

	public void setCreated_at(Date Created_at) { this.Created_at = Created_at; }

	public Date getUpdated_at() { return Updated_at; }

	public void setUpdated_at(Date Updated_at) { this.Updated_at = Updated_at; }

}