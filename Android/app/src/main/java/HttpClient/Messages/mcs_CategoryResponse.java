package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_CategoryDto;
import HttpClient.Messages.Criteria.mcs_CategoryCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_CategoryResponse extends ResponseBase {
    /// <summary>
    /// mcs_Category object.
    /// </summary>
    public mcs_CategoryDto mcs_Category;

    /// <summary>
    /// List of mcs_Category.
    /// </summary>
    public List<mcs_CategoryDto>
    mcs_CategoryList;

    public int ItemCount;
    }
