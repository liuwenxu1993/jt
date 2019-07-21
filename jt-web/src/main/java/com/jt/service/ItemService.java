package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

public interface ItemService {

	Item finItemById(Long itemId);

	ItemDesc findItemDescById(Long itemId);

}
