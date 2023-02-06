package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_StockDto {

	@SerializedName("stockId")
	@Expose
	private Integer StockId;

	@SerializedName("productId")
	@Expose
	private Integer ProductId;

	@SerializedName("quantity")
	@Expose
	private Integer Quantity;

	@SerializedName("entryPrice")
	@Expose
	private BigDecimal EntryPrice;

	@SerializedName("entryDate")
	@Expose
	private Date EntryDate;

	public Integer getStockId() { return StockId; }

	public void setStockId(Integer StockId) { this.StockId = StockId; }

	public Integer getProductId() { return ProductId; }

	public void setProductId(Integer ProductId) { this.ProductId = ProductId; }

	public Integer getQuantity() { return Quantity; }

	public void setQuantity(Integer Quantity) { this.Quantity = Quantity; }

	public BigDecimal getEntryPrice() { return EntryPrice; }

	public void setEntryPrice(BigDecimal EntryPrice) { this.EntryPrice = EntryPrice; }

	public Date getEntryDate() { return EntryDate; }

	public void setEntryDate(Date EntryDate) { this.EntryDate = EntryDate; }

}