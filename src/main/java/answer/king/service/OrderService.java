package answer.king.service;

import java.math.BigDecimal;
import java.util.List;

import answer.king.exception.AddItemToOrderException;
import answer.king.exception.InsufficientPaymentException;
import answer.king.repo.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import answer.king.model.Item;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order save(Order order) {
        return orderRepository.save(order);
	}

	public void addItem(Long id, Long itemId) {
		Order order = orderRepository.findOne(id);
		Item item = itemRepository.findOne(itemId);
		if(item == null) {
            throw new AddItemToOrderException("Cannot add non existing item to order");
		}
		item.setOrder(order);
		order.getItems().add(item);

		orderRepository.save(order);
	}

	public Receipt pay(Long id, BigDecimal payment) {
		Order order = orderRepository.findOne(id);
		order.setPaid(true);
        BigDecimal totalPrice = order.getTotalPrice();

		if(payment.compareTo(totalPrice) >=0 ) {
			Receipt receipt = new Receipt();
			receipt.setPayment(payment);
			receipt.setOrder(order);
            receiptRepository.save(receipt);

			return receipt;
		}
		else throw new InsufficientPaymentException("You don't have enough funds to pay");
	}
}
