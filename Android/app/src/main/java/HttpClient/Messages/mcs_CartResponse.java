package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CartDto;
import HttpClient.Messages.Criteria.mcs_CartCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_CartResponse extends ResponseBase {
    /// <summary>
    /// mcs_Cart object.
    /// </summary>
    public mcs_CartDto mcs_Cart;

    /// <summary>
    /// List of mcs_Cart.
    /// </summary>
    public List<mcs_CartDto>
    mcs_CartList;

    public int ItemCount;
    }
