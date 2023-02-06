package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_ProductResponse extends ResponseBase {
    /// <summary>
    /// mcs_Product object.
    /// </summary>
    public mcs_ProductDto mcs_Product;

    /// <summary>
    /// List of mcs_Product.
    /// </summary>
    public List<mcs_ProductDto>
    mcs_ProductList;

    public int ItemCount;
    }
