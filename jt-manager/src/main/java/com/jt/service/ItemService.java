package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.ItemParam;
import com.jt.vo.EasyUITable;

public interface ItemService {

	EasyUITable doGetPageObjects(Integer page, Integer rows);

	void saveItem(Item item,ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

	void deleteItem(Long[] ids);

	void instockItem(Long[] ids, int i);

	ItemDesc queryItemDesc(Long id);

	ItemParam queryItemParam(Long id);

	Item findItemById(Long itemId);

	ItemDesc findItemDescById(Long itemId);

}
