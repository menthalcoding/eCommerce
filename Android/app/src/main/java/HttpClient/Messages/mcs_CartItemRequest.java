package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_CartItemRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_CartItemCriteria Criteria;

    /// <summary>
    /// mcs_CartItem object.
    /// </summary>
    public mcs_CartItemDto mcs_CartItem;
}
