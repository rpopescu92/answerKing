package controller;

import answer.king.controller.OrderController;
import answer.king.exception.InsufficientPaymentException;
import answer.king.model.Item;
import answer.king.model.Order;
import answer.king.model.Receipt;
import answer.king.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> ordersMock = new ArrayList<>();
        Order order = new Order();
        List<Item> items = new ArrayList<>();
        items.add(new Item(1L,"chips",new BigDecimal(34)));
        order.setItems(items);
        ordersMock.add(order);
        Mockito.when(orderService.getAll()).thenReturn(ordersMock);

        List<Order> orders = orderController.getAll();
        Assert.assertTrue(orders.size() == 1);
        Assert.assertTrue(orders.get(0).getItems().size() == 1);
    }

    @Test
    public void testGetAllOrdersEmpty() {
        List<Order> ordersMock = new ArrayList<>();
        Mockito.when(orderService.getAll()).thenReturn(ordersMock);

        List<Order> orders = orderController.getAll();
        Assert.assertNotNull(ordersMock);
        Assert.assertTrue(orders.size() == 0);
    }

    @Test
    @Ignore
    public void testCreateOrder() {
        Order orderMock = new Order();
        orderMock.setId(2L);
        //Mockito.when(orderService.save(new Order())).thenReturn(new Order());

        Order order = orderController.create();
        Assert.assertNotNull(order);
    }

    @Test
    public void testAddItemToOrder() {
        Order orderMock = new Order();
        orderMock.setId(1L);
        Item itemMock = new Item(1L, "chips", new BigDecimal(2));
        List<Item> items = new ArrayList<>();
        items.add(itemMock);
        orderMock.setItems(items);

        orderController.addItem(1l, 1L);
        Mockito.verify(orderService, Mockito.times(1)).addItem(1L, 1L);
    }

    @Test
    public void testPay() {
        Order orderMock = new Order();
        orderMock.setId(1L);
        Receipt receiptMock = new Receipt();
        receiptMock.setOrder(orderMock);
        receiptMock.setPayment(new BigDecimal(10));

        Mockito.when(orderService.pay(1L,new BigDecimal(10))).thenReturn(receiptMock);

        Receipt receipt = orderController.pay(1L, new BigDecimal(10));
        Assert.assertTrue(receipt.getPayment().equals(receiptMock.getPayment()));
        Assert.assertTrue(receipt.getOrder().getId().equals(orderMock.getId()));
    }
}
