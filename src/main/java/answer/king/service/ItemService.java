package answer.king.service;

import java.math.BigDecimal;
import java.util.List;

import answer.king.exception.InvalidItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import answer.king.model.Item;
import answer.king.repo.ItemRepository;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public List<Item> getAll() {
		return itemRepository.findAll();
	}

	public Item save(Item item) {
		if(StringUtils.isEmpty(item.getName())) {
			throw new InvalidItemException("Cannot save invalid name item");
		}
		else if(item.getPrice().compareTo(new BigDecimal(0))== -1) {
			throw new InvalidItemException("Cannot save invalid price item");
		}
		return itemRepository.save(item);
	}


	public Item update(Long id, BigDecimal price) {
		Item updatedItem = itemRepository.findOne(id);
		updatedItem.setPrice(price);

		return updatedItem;
	}
}
