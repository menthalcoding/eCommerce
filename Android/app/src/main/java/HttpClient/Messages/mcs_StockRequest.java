package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_StockRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_StockCriteria Criteria;

    /// <summary>
    /// mcs_Stock object.
    /// </summary>
    public mcs_StockDto mcs_Stock;
}
