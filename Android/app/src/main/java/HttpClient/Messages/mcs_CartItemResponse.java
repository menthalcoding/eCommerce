package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CartItemDto;
import HttpClient.Messages.Criteria.mcs_CartItemCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_CartItemResponse extends ResponseBase {
    /// <summary>
    /// mcs_CartItem object.
    /// </summary>
    public mcs_CartItemDto mcs_CartItem;

    /// <summary>
    /// List of mcs_CartItem.
    /// </summary>
    public List<mcs_CartItemDto>
    mcs_CartItemList;

    public int ItemCount;
    }
