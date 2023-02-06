package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_AddressDto;
import HttpClient.Messages.Criteria.mcs_AddressCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_AddressRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_AddressCriteria Criteria;

    /// <summary>
    /// mcs_Address object.
    /// </summary>
    public mcs_AddressDto mcs_Address;
}
