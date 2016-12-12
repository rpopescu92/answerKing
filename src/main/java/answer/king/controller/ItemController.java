package answer.king.controller;

import java.math.BigDecimal;
import java.util.List;

import answer.king.exception.InvalidItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import answer.king.model.Item;
import answer.king.service.ItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Item> getAll() {
		return itemService.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Item create(@Valid @RequestBody Item item) {
		return itemService.save(item);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public Item update(@PathVariable("id")Long id, @RequestBody BigDecimal price) {
		return itemService.update(id, price);

	}
}
