package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService client;
	
	@Override
	public Item finItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById/"+itemId;
		String get = client.doGet(url);
		Item item = JsonUtils.toObject(get, Item.class);
		return item;
	}
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
		String get = client.doGet(url);
		ItemDesc item = JsonUtils.toObject(get, ItemDesc.class);
		return item;
	}
}
