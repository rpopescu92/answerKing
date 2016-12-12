package answer.king.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "T_RECEIPT")
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal payment;

	@OneToOne
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getChange() {
		BigDecimal totalOrderPrice = order.getItems()
			.stream()
			.map(Item::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		return payment.subtract(totalOrderPrice);
	}
}
