package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_ProductPhotoDto;
import HttpClient.Messages.Criteria.mcs_ProductPhotoCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_ProductPhotoResponse extends ResponseBase {
    /// <summary>
    /// mcs_ProductPhoto object.
    /// </summary>
    public mcs_ProductPhotoDto mcs_ProductPhoto;

    /// <summary>
    /// List of mcs_ProductPhoto.
    /// </summary>
    public List<mcs_ProductPhotoDto>
    mcs_ProductPhotoList;

    public int ItemCount;
    }
