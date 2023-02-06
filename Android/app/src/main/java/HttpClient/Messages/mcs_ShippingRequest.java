package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ShippingDto;
import HttpClient.Messages.Criteria.mcs_ShippingCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_ShippingRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_ShippingCriteria Criteria;

    /// <summary>
    /// mcs_Shipping object.
    /// </summary>
    public mcs_ShippingDto mcs_Shipping;
}
