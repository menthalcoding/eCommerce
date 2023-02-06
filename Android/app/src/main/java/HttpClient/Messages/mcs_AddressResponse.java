package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_AddressResponse extends ResponseBase {
    /// <summary>
    /// mcs_Address object.
    /// </summary>
    public mcs_AddressDto mcs_Address;

    /// <summary>
    /// List of mcs_Address.
    /// </summary>
    public List<mcs_AddressDto>
    mcs_AddressList;

    public int ItemCount;
    }
