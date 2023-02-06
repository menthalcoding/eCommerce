package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_OrderRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_OrderCriteria Criteria;

    /// <summary>
    /// mcs_Order object.
    /// </summary>
    public mcs_OrderDto mcs_Order;
}
