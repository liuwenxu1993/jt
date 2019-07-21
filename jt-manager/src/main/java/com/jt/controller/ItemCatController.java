package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.JsonTree;
import com.jt.service.ItemCatService;

@RestController
@RequestMapping("item/cat")
public class ItemCatController {
	@Autowired
	ItemCatService itemCatService;
	
	@RequestMapping("/queryItemName")
	public String queryItemName(Integer itemCatId) {
		String name = itemCatService.queryItemName(itemCatId);
		return name;
	}
	
	@RequestMapping("/list")
	public List<JsonTree> showCatList(@RequestParam(name="id",defaultValue = "0") Long parentId) {
		
		return itemCatService.findItemCatByCache(parentId);
	}
	
	
}
