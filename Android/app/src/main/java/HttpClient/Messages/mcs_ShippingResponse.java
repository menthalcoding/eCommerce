package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_ShippingResponse extends ResponseBase {
    /// <summary>
    /// mcs_Shipping object.
    /// </summary>
    public mcs_ShippingDto mcs_Shipping;

    /// <summary>
    /// List of mcs_Shipping.
    /// </summary>
    public List<mcs_ShippingDto>
    mcs_ShippingList;

    public int ItemCount;
    }
