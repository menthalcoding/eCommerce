package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_CartItemDto {

	@SerializedName("cartItemId")
	@Expose
	private Integer CartItemId;

	@SerializedName("cartId")
	@Expose
	private Integer CartId;

	@SerializedName("productId")
	@Expose
	private Integer ProductId;

	@SerializedName("quantity")
	@Expose
	private Integer Quantity;

	@SerializedName("created_at")
	@Expose
	private Date Created_at;

	@SerializedName("updated_at")
	@Expose
	private Date Updated_at;

	public Integer getCartItemId() { return CartItemId; }

	public void setCartItemId(Integer CartItemId) { this.CartItemId = CartItemId; }

	public Integer getCartId() { return CartId; }

	public void setCartId(Integer CartId) { this.CartId = CartId; }

	public Integer getProductId() { return ProductId; }

	public void setProductId(Integer ProductId) { this.ProductId = ProductId; }

	public Integer getQuantity() { return Quantity; }

	public void setQuantity(Integer Quantity) { this.Quantity = Quantity; }

	public Date getCreated_at() { return Created_at; }

	public void setCreated_at(Date Created_at) { this.Created_at = Created_at; }

	public Date getUpdated_at() { return Updated_at; }

	public void setUpdated_at(Date Updated_at) { this.Updated_at = Updated_at; }

}