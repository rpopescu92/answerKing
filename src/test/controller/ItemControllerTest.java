package controller;

import answer.king.controller.ItemController;
import answer.king.exception.InvalidItemException;
import answer.king.model.Item;
import answer.king.service.ItemService;
import org.junit.Assert;
import org.junit.Before;
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
//@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllItems() {
        List<Item> itemsMock = new ArrayList<>();
        itemsMock.add(new Item(1L,"chips",new BigDecimal(34)));
        Mockito.when(itemService.getAll()).thenReturn(itemsMock);

        List<Item> items = itemController.getAll();
        Assert.assertTrue(items.size() == 1);
    }

    @Test
    public void testGetAllItemsWhenNoItems() {
        List<Item> itemsMock = new ArrayList<>();
        Mockito.when(itemService.getAll()).thenReturn(itemsMock);

        List<Item> items = itemController.getAll();
        Assert.assertTrue(items.size() == 0);
    }

    @Test
    public void testCreateItem() {
        Item itemMock = new Item(2L, "burger", new BigDecimal(1.99));
        Mockito.when(itemService.save(itemMock)).thenReturn(itemMock);

        Item item = itemController.create(itemMock);
        Assert.assertTrue(item.getName().equals(itemMock.getName()));
        Assert.assertTrue(item.getPrice().compareTo(itemMock.getPrice()) == 0);
    }

    @Test(expected = InvalidItemException.class)
    public void testCreateInvalidItemName() {
        Item itemMock = new Item(3L, "", new BigDecimal(0));
        Mockito.when(itemService.save(itemMock)).thenThrow(new InvalidItemException("Cannot save invalid name item"));

        itemController.create(itemMock);
    }

    @Test(expected = InvalidItemException.class)
    public void testCreateInvalidItemPrice() {
        Item itemMock = new Item(3L, "nachos", new BigDecimal(-3));
        Mockito.when(itemService.save(itemMock)).thenThrow(new InvalidItemException("Cannot save invalid price item"));

        itemController.create(itemMock);
    }

    @Test
    public void testUpdateItemPrice() {
        Item itemMock = new Item(2L, "biscuits", new BigDecimal(3));
        Mockito.when(itemService.update(1L, new BigDecimal(3))).thenReturn(itemMock);

        Item item = itemController.update(1L,new BigDecimal(3));
        Assert.assertNotNull(item);
        Assert.assertTrue(item.getPrice().equals(new BigDecimal(3)));
    }
}
