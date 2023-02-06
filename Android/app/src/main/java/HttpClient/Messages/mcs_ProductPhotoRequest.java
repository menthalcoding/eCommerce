package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_ProductPhotoRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_ProductPhotoCriteria Criteria;

    /// <summary>
    /// mcs_ProductPhoto object.
    /// </summary>
    public mcs_ProductPhotoDto mcs_ProductPhoto;
}
