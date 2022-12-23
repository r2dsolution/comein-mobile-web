package com.aws.codestar.comein.entity.payment;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "payment_credit_card")
public class CreditCardM implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
   	private Long id;
       
    private String cardNO;
       
    private String holderName;
    
    private Long paymentId;
    
    private String transactionDate;
    
    private String transactionTime;
    
    private String currency;
    
    private String status;
    
    private String statusCode;
    private String statusDesc;
    private String recommendation;
    
    private Date createDate;
    private Date updateDate;
 

   	public Long getId() {
   		return id;
   	}

   	public void setId(Long id) {
   		this.id = id;
   	}

   	public String getCardNO() {
   		return cardNO;
   	}

   	public void setCardNO(String cardNO) {
   		this.cardNO = cardNO;
   	}

   	public String getHolderName() {
   		return holderName;
   	}

   	public void setHolderName(String holderName) {
   		this.holderName = holderName;
   	}

   	public static long getSerialversionuid() {
   		return serialVersionUID;
   	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
       

}
