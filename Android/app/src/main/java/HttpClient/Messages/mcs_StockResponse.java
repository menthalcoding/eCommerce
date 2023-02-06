package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_StockDto;
import HttpClient.Messages.Criteria.mcs_StockCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_StockResponse extends ResponseBase {
    /// <summary>
    /// mcs_Stock object.
    /// </summary>
    public mcs_StockDto mcs_Stock;

    /// <summary>
    /// List of mcs_Stock.
    /// </summary>
    public List<mcs_StockDto>
    mcs_StockList;

    public int ItemCount;
    }
