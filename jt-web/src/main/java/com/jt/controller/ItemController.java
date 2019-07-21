package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService service;
	
	@RequestMapping("/{itemId}") 
	public String findItems(@PathVariable Long itemId,Model model) {
		Item item = service.finItemById(itemId);
		ItemDesc itemDesc = service.findItemDescById(itemId);
		System.out.println(itemDesc);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}

}
