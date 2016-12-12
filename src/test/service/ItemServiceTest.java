package service;

import answer.king.exception.InvalidItemException;
import answer.king.model.Item;
import answer.king.repo.ItemRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveItem() {
        Item itemMock = new Item(2L, "burger", new BigDecimal(1.99));
        Mockito.when(itemRepository.save(itemMock)).thenReturn(itemMock);

        Item item = itemService.save(itemMock);
        Assert.assertTrue(item.getName().equals(itemMock.getName()));
        Assert.assertTrue(item.getPrice().compareTo(itemMock.getPrice()) == 0);
    }

    @Test(expected = InvalidItemException.class)
    public void testCreateInvalidItemName() {
        Item itemMock = new Item(3L, "", new BigDecimal(0));

        itemService.save(itemMock);
    }

    @Test(expected = InvalidItemException.class)
    public void testCreateInvalidItemPrice() {
        Item itemMock = new Item(3L, "nachos", new BigDecimal(-3));

        itemService.save(itemMock);
    }

    @Test
    public void testUpdateItemPrice() {
        Item itemMock =  new Item(3L, "", new BigDecimal(5));
        Mockito.when(itemRepository.findOne(3L)).thenReturn(itemMock);

        Item item = itemService.update(3L, new BigDecimal(4));
        Assert.assertTrue(item.getPrice().equals(new BigDecimal(4)));
    }

}
