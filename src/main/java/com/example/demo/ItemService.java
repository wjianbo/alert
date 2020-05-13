package com.example.demo;

import java.util.List;

public interface ItemService {

	List<Item> getItemList();

	void save(Item item);

	Item findItemById(long id);

	void edit(Item item);

	void delete(long id);

}
