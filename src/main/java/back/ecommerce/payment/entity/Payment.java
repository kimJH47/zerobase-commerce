package back.ecommerce.payment.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import back.ecommerce.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String transactionId;
	private String cid; //가맹점 번호
	private String userEmail;
	private String orderCode;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private Long totalPrice;
	private Long taxFreePrice;

	public static Payment createReadyPayment(String transactionId, String cid, String orderCode, String userEmail,
		Long totalPrice) {
		return new Payment(null, transactionId, cid, userEmail, orderCode, PaymentStatus.READY, totalPrice, 0L);
	}

	public boolean isNotReady() {
		return !paymentStatus.equals(PaymentStatus.READY);
	}
}
