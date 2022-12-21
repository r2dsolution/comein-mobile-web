package com.aws.codestar.comein.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "PAYMENT_INFO")
public class PaymentM implements Serializable {
    private static final long serialVersionUID = 1L;
    
    

	public PaymentM() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	private Long id;
	
    private String paymentNO;

    private String gatewayRef;

    private String bookingCode;

    private String gateway;

    private BigDecimal totalAmt;

    private Date createDate;

    private Date updateDate;

    private String status;

    /**
     * @return String return the paymentNO
     */
    public String getPaymentNO() {
        return paymentNO;
    }

    /**
     * @param paymentNO the paymentNO to set
     */
    public void setPaymentNO(String paymentNO) {
        this.paymentNO = paymentNO;
    }

    /**
     * @return String return the gatewayRef
     */
    public String getGatewayRef() {
        return gatewayRef;
    }

    /**
     * @param gatewayRef the gatewayRef to set
     */
    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }

    /**
     * @return String return the gateway
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * @param gateway the gateway to set
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    /**
     * @return BigDecimal return the totalAmt
     */
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    /**
     * @param totalAmt the totalAmt to set
     */
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * @return Date return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Date return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return String return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }


}