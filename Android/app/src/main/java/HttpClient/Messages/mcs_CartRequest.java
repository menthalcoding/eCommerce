package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_CartRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_CartCriteria Criteria;

    /// <summary>
    /// mcs_Cart object.
    /// </summary>
    public mcs_CartDto mcs_Cart;
}
