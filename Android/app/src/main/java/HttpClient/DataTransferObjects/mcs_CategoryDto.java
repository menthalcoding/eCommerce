package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_CategoryDto {

	@SerializedName("categoryId")
	@Expose
	private Integer CategoryId;

	@SerializedName("categoryParentId")
	@Expose
	private Integer CategoryParentId;

	@SerializedName("name")
	@Expose
	private String Name;

	@SerializedName("description")
	@Expose
	private String Description;

	public Integer getCategoryId() { return CategoryId; }

	public void setCategoryId(Integer CategoryId) { this.CategoryId = CategoryId; }

	public Integer getCategoryParentId() { return CategoryParentId; }

	public void setCategoryParentId(Integer CategoryParentId) { this.CategoryParentId = CategoryParentId; }

	public String getName() { return (Name == null ? new String() : Name); }

	public void setName(String Name) { this.Name = Name; }

	public String getDescription() { return (Description == null ? new String() : Description); }

	public void setDescription(String Description) { this.Description = Description; }

}