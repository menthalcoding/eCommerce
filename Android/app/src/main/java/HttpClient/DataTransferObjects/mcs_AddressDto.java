package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class mcs_AddressDto {

	@SerializedName("addressId")
	@Expose
	private Integer AddressId;

	@SerializedName("userId")
	@Expose
	private String UserId;

	@SerializedName("name")
	@Expose
	private String Name;

	@SerializedName("gPS")
	@Expose
	private String GPS;

	@SerializedName("country")
	@Expose
	private String Country;

	@SerializedName("city")
	@Expose
	private String City;

	@SerializedName("zipCode")
	@Expose
	private String ZipCode;

	@SerializedName("countryCode")
	@Expose
	private String CountryCode;

	@SerializedName("detail")
	@Expose
	private String Detail;

	@SerializedName("primary")
	@Expose
	private Boolean Primary;

	@SerializedName("active")
	@Expose
	private Boolean Active;

	public Integer getAddressId() { return AddressId; }

	public void setAddressId(Integer AddressId) { this.AddressId = AddressId; }

	public String getUserId() { return (UserId == null ? new String() : UserId); }

	public void setUserId(String UserId) { this.UserId = UserId; }

	public String getName() { return (Name == null ? new String() : Name); }

	public void setName(String Name) { this.Name = Name; }

	public String getGPS() { return (GPS == null ? new String() : GPS); }

	public void setGPS(String GPS) { this.GPS = GPS; }

	public String getCountry() { return (Country == null ? new String() : Country); }

	public void setCountry(String Country) { this.Country = Country; }

	public String getCity() { return (City == null ? new String() : City); }

	public void setCity(String City) { this.City = City; }

	public String getZipCode() { return (ZipCode == null ? new String() : ZipCode); }

	public void setZipCode(String ZipCode) { this.ZipCode = ZipCode; }

	public String getCountryCode() { return (CountryCode == null ? new String() : CountryCode); }

	public void setCountryCode(String CountryCode) { this.CountryCode = CountryCode; }

	public String getDetail() { return (Detail == null ? new String() : Detail); }

	public void setDetail(String Detail) { this.Detail = Detail; }

	public Boolean getPrimary() { return Primary; }

	public void setPrimary(Boolean Primary) { this.Primary = Primary; }

	public Boolean getActive() { return Active; }

	public void setActive(Boolean Active) { this.Active = Active; }

}