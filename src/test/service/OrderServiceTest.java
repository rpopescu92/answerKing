package service;

import answer.king.exception.AddItemToOrderException;
import answer.king.exception.InsufficientPaymentException;
import answer.king.model.Item;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.repo.ItemRepository;
import answer.king.repo.OrderRepository;
import answer.king.repo.ReceiptRepository;
import answer.king.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Or;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAlL() {
        List<Order> ordersMock = new ArrayList<>();
        Order order = new Order();
        List<Item> items = new ArrayList<>();
        items.add(new Item(1L,"chips",new BigDecimal(34)));
        order.setItems(items);
        ordersMock.add(order);

        Mockito.when(orderRepository.findAll()).thenReturn(ordersMock);
        List<Order> orders = orderService.getAll();
        Assert.assertTrue(orders.size() == 1);
        Assert.assertTrue(orders.get(0).getItems().size() ==1);
    }

    @Test
    public void testSaveOrder() {
        Order orderMock = new Order();
        List<Item> items = new ArrayList<>();
        items.add(new Item(1L,"chips",new BigDecimal(3)));
        orderMock.setItems(items);
        Mockito.when(orderRepository.save(orderMock)).thenReturn(orderMock);

        Order order = orderService.save(orderMock);
        Assert.assertNotNull(order);
        Assert.assertTrue(order.getItems().size() == 1);
    }

    @Test
    public void testAddItemToOrder() {
        Order orderMock = new Order();
        orderMock.setId(1L);
        List<Item> items = new ArrayList<>();
        Item itemMock = new Item(1L,"chips",new BigDecimal(3));
        items.add(itemMock);
        orderMock.setItems(items);

        Mockito.when(itemRepository.findOne(1L)).thenReturn(itemMock);

        orderService.addItem(1L, 1L);
        Mockito.verify(orderService, Mockito.times(1)).addItem(1L, 1L);

    }

    @Test(expected = AddItemToOrderException.class)
    public void testAddItemToOrderException() {
        Order orderMock = new Order();
        orderMock.setId(1L);

        Mockito.when(itemRepository.findOne(1L)).thenReturn(null);

        orderService.addItem(1L, 1L);
    }

    @Test(expected = InsufficientPaymentException.class)
    public void testPayException() {
        Order orderMock = new Order();
        orderMock.setId(1L);
        List<Item> items = new ArrayList<>();
        Item itemMock = new Item(1L,"chips",new BigDecimal(3));
        items.add(itemMock);
        orderMock.setItems(items);
        Mockito.when(orderRepository.findOne(1L)).thenReturn(orderMock);

        orderService.pay(1L, new BigDecimal(1));
        Mockito.verify(orderService,Mockito.times(1)).pay(1L, new BigDecimal(1));
    }

    @Test
    public void testPay() {
        Order orderMock = new Order();
        orderMock.setId(1L);
        List<Item> items = new ArrayList<>();
        Item itemMock = new Item(1L,"chips",new BigDecimal(3));
        items.add(itemMock);
        orderMock.setItems(items);
        Mockito.when(orderRepository.findOne(1L)).thenReturn(orderMock);

        Receipt receipt = orderService.pay(1L, new BigDecimal(5));
        Assert.assertTrue(receipt.getChange().equals(new BigDecimal(2)));
        Assert.assertTrue(receipt.getPayment().equals(new BigDecimal(5)));
        Assert.assertTrue(receipt.getOrder().getPaid());
    }
}
