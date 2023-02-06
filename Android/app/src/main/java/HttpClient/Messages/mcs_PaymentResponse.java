package HttpClient.Messages;

import java.util.List;
import HttpClient.DataTransferObjects.mcs_PaymentDto;
import HttpClient.Messages.Criteria.mcs_PaymentCriteria;
import HttpClient.Messages.MessageBase.ResponseBase;

public class mcs_PaymentResponse extends ResponseBase {
    /// <summary>
    /// mcs_Payment object.
    /// </summary>
    public mcs_PaymentDto mcs_Payment;

    /// <summary>
    /// List of mcs_Payment.
    /// </summary>
    public List<mcs_PaymentDto>
    mcs_PaymentList;

    public int ItemCount;
    }
