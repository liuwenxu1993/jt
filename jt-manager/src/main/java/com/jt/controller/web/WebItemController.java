package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService service;
	
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable Long itemId) {
		return service.findItemById(itemId);
	}
	
	@RequestMapping("/findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		return service.findItemDescById(itemId);
	}
}
