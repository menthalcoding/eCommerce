package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_CategoryRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_CategoryCriteria Criteria;

    /// <summary>
    /// mcs_Category object.
    /// </summary>
    public mcs_CategoryDto mcs_Category;
}
