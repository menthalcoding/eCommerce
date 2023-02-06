package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_OrderDto;
import HttpClient.Messages.Criteria.mcs_OrderCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_OrderResponse extends ResponseBase {
    /// <summary>
    /// mcs_Order object.
    /// </summary>
    public mcs_OrderDto mcs_Order;

    /// <summary>
    /// List of mcs_Order.
    /// </summary>
    public List<mcs_OrderDto>
    mcs_OrderList;

    public int ItemCount;
    }
