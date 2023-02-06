package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ProductDto;
import HttpClient.Messages.Criteria.mcs_ProductCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_ProductRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_ProductCriteria Criteria;

    /// <summary>
    /// mcs_Product object.
    /// </summary>
    public mcs_ProductDto mcs_Product;
}
