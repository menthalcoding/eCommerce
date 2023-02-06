package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_ShippingDto {

	@SerializedName("shippingId")
	@Expose
	private Integer ShippingId;

	@SerializedName("orderId")
	@Expose
	private Integer OrderId;

	@SerializedName("addressId")
	@Expose
	private Integer AddressId;

	@SerializedName("shippingMethod")
	@Expose
	private String ShippingMethod;

	@SerializedName("status")
	@Expose
	private String Status;

	@SerializedName("shippingProvider")
	@Expose
	private String ShippingProvider;

	@SerializedName("cost")
	@Expose
	private BigDecimal Cost;

	public Integer getShippingId() { return ShippingId; }

	public void setShippingId(Integer ShippingId) { this.ShippingId = ShippingId; }

	public Integer getOrderId() { return OrderId; }

	public void setOrderId(Integer OrderId) { this.OrderId = OrderId; }

	public Integer getAddressId() { return AddressId; }

	public void setAddressId(Integer AddressId) { this.AddressId = AddressId; }

	public String getShippingMethod() { return (ShippingMethod == null ? new String() : ShippingMethod); }

	public void setShippingMethod(String ShippingMethod) { this.ShippingMethod = ShippingMethod; }

	public String getStatus() { return (Status == null ? new String() : Status); }

	public void setStatus(String Status) { this.Status = Status; }

	public String getShippingProvider() { return (ShippingProvider == null ? new String() : ShippingProvider); }

	public void setShippingProvider(String ShippingProvider) { this.ShippingProvider = ShippingProvider; }

	public BigDecimal getCost() { return Cost; }

	public void setCost(BigDecimal Cost) { this.Cost = Cost; }

}