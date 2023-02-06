package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_PaymentDto {

	@SerializedName("paymentId")
	@Expose
	private Integer PaymentId;

	@SerializedName("userId")
	@Expose
	private String UserId;

	@SerializedName("paymentType")
	@Expose
	private String PaymentType;

	@SerializedName("created_at")
	@Expose
	private Date Created_at;

	@SerializedName("amount")
	@Expose
	private BigDecimal Amount;

	@SerializedName("currency")
	@Expose
	private String Currency;

	@SerializedName("orderId")
	@Expose
	private Integer OrderId;

	@SerializedName("addressId")
	@Expose
	private Integer AddressId;

	public Integer getPaymentId() { return PaymentId; }

	public void setPaymentId(Integer PaymentId) { this.PaymentId = PaymentId; }

	public String getUserId() { return (UserId == null ? new String() : UserId); }

	public void setUserId(String UserId) { this.UserId = UserId; }

	public String getPaymentType() { return (PaymentType == null ? new String() : PaymentType); }

	public void setPaymentType(String PaymentType) { this.PaymentType = PaymentType; }

	public Date getCreated_at() { return Created_at; }

	public void setCreated_at(Date Created_at) { this.Created_at = Created_at; }

	public BigDecimal getAmount() { return Amount; }

	public void setAmount(BigDecimal Amount) { this.Amount = Amount; }

	public String getCurrency() { return (Currency == null ? new String() : Currency); }

	public void setCurrency(String Currency) { this.Currency = Currency; }

	public Integer getOrderId() { return OrderId; }

	public void setOrderId(Integer OrderId) { this.OrderId = OrderId; }

	public Integer getAddressId() { return AddressId; }

	public void setAddressId(Integer AddressId) { this.AddressId = AddressId; }

}