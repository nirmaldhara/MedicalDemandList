/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.model;

/**
 *
 * @author ndhara
 */
public class EBillDetails {
    private String transactionID;
    private String paymentDate;
    private String paymentGateway;
    private String billingOffice;
    private String consumerId;
    private String customerName;
    private String invoiceNumber;
    private String billPaidFor;
    private String paymentMode;
    private String receivedAmount;
    private String serviceCharge;
    private String created;

    /**
     * @return the transactionID
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * @return the paymentDate
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the paymentGateway
     */
    public String getPaymentGateway() {
        return paymentGateway;
    }

    /**
     * @param paymentGateway the paymentGateway to set
     */
    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    /**
     * @return the billingOffice
     */
    public String getBillingOffice() {
        return billingOffice;
    }

    /**
     * @param billingOffice the billingOffice to set
     */
    public void setBillingOffice(String billingOffice) {
        this.billingOffice = billingOffice;
    }

    /**
     * @return the consumerId
     */
    public String getConsumerId() {
        return consumerId;
    }

    /**
     * @param consumerId the consumerId to set
     */
    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the billPaidFor
     */
    public String getBillPaidFor() {
        return billPaidFor;
    }

    /**
     * @param billPaidFor the billPaidFor to set
     */
    public void setBillPaidFor(String billPaidFor) {
        this.billPaidFor = billPaidFor;
    }

    /**
     * @return the paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

   

    /**
     * @return the serviceCharge
     */
    public String getServiceCharge() {
        return serviceCharge;
    }

    /**
     * @param serviceCharge the serviceCharge to set
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * @return the receivedAmount
     */
    public String getReceivedAmount() {
        return receivedAmount;
    }

    /**
     * @param receivedAmount the receivedAmount to set
     */
    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    /**
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
    }
}
