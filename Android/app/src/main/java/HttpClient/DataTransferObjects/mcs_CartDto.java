package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_CartDto {

	@SerializedName("cartId")
	@Expose
	private Integer CartId;

	@SerializedName("userId")
	@Expose
	private String UserId;

	@SerializedName("status")
	@Expose
	private String Status;

	@SerializedName("created_at")
	@Expose
	private Date Created_at;

	@SerializedName("updated_at")
	@Expose
	private Date Updated_at;

	public Integer getCartId() { return CartId; }

	public void setCartId(Integer CartId) { this.CartId = CartId; }

	public String getUserId() { return (UserId == null ? new String() : UserId); }

	public void setUserId(String UserId) { this.UserId = UserId; }

	public String getStatus() { return (Status == null ? new String() : Status); }

	public void setStatus(String Status) { this.Status = Status; }

	public Date getCreated_at() { return Created_at; }

	public void setCreated_at(Date Created_at) { this.Created_at = Created_at; }

	public Date getUpdated_at() { return Updated_at; }

	public void setUpdated_at(Date Updated_at) { this.Updated_at = Updated_at; }

}