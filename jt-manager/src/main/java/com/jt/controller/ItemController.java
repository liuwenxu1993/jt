package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.ItemParam;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("query")
	public EasyUITable doQuery(Integer page,Integer rows) {
		EasyUITable result = itemService.doGetPageObjects(page,rows);
		return result;
	}
	
	/** 
	 * 	统一异常处理,一般需要制定特定的异常类型
	 * */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		itemService.saveItem(item,itemDesc);
		return SysResult.success();
		
	}
	
	/** 实现商品的修改 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}
	
	/** 删除商品 */ 
	@RequestMapping("/delete")
	public SysResult doDeleteItem(Long[] ids) {
		itemService.deleteItem(ids);
		return SysResult.success();
	}
	 
	/** 下架 */
	@RequestMapping("instock")
	public SysResult doInstockItem(Long[] ids) {
		itemService.instockItem(ids,2);
		return SysResult.success();
	}
	/** 上架 */
	@RequestMapping("reshelf")
	public SysResult doreshelfItem(Long[] ids) {
		itemService.instockItem(ids,1);
		return SysResult.success();
	}
	
	/** 商品描述回显 */
	@RequestMapping("/query/item/desc/{id}")
	public SysResult doQueryItemDesc(@PathVariable("id")Long id) {
		ItemDesc iDesc = itemService.queryItemDesc(id);
		return new SysResult(200, "回调成功", iDesc);
	}
	
	/** 商品规格回显 */
	@RequestMapping("/param/item/query/{id}")
	public SysResult doQueryItemParam(@PathVariable("id")Long id) {
		ItemParam iDesc = itemService.queryItemParam(id);
		return new SysResult(200, "回调成功", iDesc);
	}
}
