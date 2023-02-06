package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_OrderDto {

	@SerializedName("orderId")
	@Expose
	private Integer OrderId;

	@SerializedName("userId")
	@Expose
	private String UserId;

	@SerializedName("cartId")
	@Expose
	private Integer CartId;

	@SerializedName("created_at")
	@Expose
	private Date Created_at;

	@SerializedName("cost")
	@Expose
	private BigDecimal Cost;

	@SerializedName("tax")
	@Expose
	private BigDecimal Tax;

	@SerializedName("total")
	@Expose
	private BigDecimal Total;

	@SerializedName("paid")
	@Expose
	private Boolean Paid;

	@SerializedName("currency")
	@Expose
	private String Currency;

	public Integer getOrderId() { return OrderId; }

	public void setOrderId(Integer OrderId) { this.OrderId = OrderId; }

	public String getUserId() { return (UserId == null ? new String() : UserId); }

	public void setUserId(String UserId) { this.UserId = UserId; }

	public Integer getCartId() { return CartId; }

	public void setCartId(Integer CartId) { this.CartId = CartId; }

	public Date getCreated_at() { return Created_at; }

	public void setCreated_at(Date Created_at) { this.Created_at = Created_at; }

	public BigDecimal getCost() { return Cost; }

	public void setCost(BigDecimal Cost) { this.Cost = Cost; }

	public BigDecimal getTax() { return Tax; }

	public void setTax(BigDecimal Tax) { this.Tax = Tax; }

	public BigDecimal getTotal() { return Total; }

	public void setTotal(BigDecimal Total) { this.Total = Total; }

	public Boolean getPaid() { return Paid; }

	public void setPaid(Boolean Paid) { this.Paid = Paid; }

	public String getCurrency() { return (Currency == null ? new String() : Currency); }

	public void setCurrency(String Currency) { this.Currency = Currency; }

}