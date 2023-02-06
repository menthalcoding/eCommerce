package HttpClient.DataTransferObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileCustomerDto {
        @SerializedName("NameSurname")
        @Expose
        public String NameSurname;

        @SerializedName("Phone")
        @Expose
        public String Phone;

        @SerializedName("BusinessPhone")
        @Expose
        public String BusinessPhone;

        @SerializedName("CellPhone")
        @Expose
        public String CellPhone;

        @SerializedName("EMail")
        @Expose
        public String EMail;

        @SerializedName("Address")
        @Expose
        public String Address;

        @SerializedName("CityId")
        @Expose
        public String CityId;

        @SerializedName("CountyId")
        @Expose
        public String CountyId;

        @SerializedName("districtId")
        @Expose
        public String districtId;

        @SerializedName("Photo")
        @Expose
        public String Photo;
}
