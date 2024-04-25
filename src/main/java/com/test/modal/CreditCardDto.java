package com.test.modal;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.test.common.AppConstants.PaymentMethodEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "CreditCard")
public class CreditCardDto implements Serializable {
	
	private static final long serialVersionUID = 12345L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Long userId;
	private PaymentMethodEnum paymentMethod = PaymentMethodEnum.CreditCard;
	private String customerId;
	private String cardId;
	private String number;
	private String numberFull;	//Real/Original card-number
	private Integer expireMonth;
	private Integer expireYear;
	private String type;
	private String payerId;
	private String payerEmail;
	private String agreementId;
	private boolean active = true;
	private LocalDateTime createdOn;
	@Transient
	private Integer cvv2;
	
	public CreditCardDto() { }
	public CreditCardDto(Long id, PaymentMethodEnum paymentMethod, String number, String numberFull, String type, String payerEmail) {
		this.id = id;
		this.paymentMethod = paymentMethod;
		this.number = number;
		this.numberFull = numberFull;
		this.type = type;
		this.payerEmail = payerEmail;
	}

}
