package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Messages.MessageBase.RequestBase;

public class mcs_PaymentRequest extends RequestBase {
    /// <summary>
    /// Selection criteria and sort order
    /// </summary>
    public mcs_PaymentCriteria Criteria;

    /// <summary>
    /// mcs_Payment object.
    /// </summary>
    public mcs_PaymentDto mcs_Payment;
}
