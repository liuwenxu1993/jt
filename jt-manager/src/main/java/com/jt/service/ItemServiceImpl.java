package com.jt.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.annotation.Cache;
import com.jt.annotation.CacheUpdate;
import com.jt.exception.ServiceException;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.mapper.ItemParamMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.pojo.ItemParam;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Autowired
	private ItemParamMapper itemParamMapper;
	
	@Override
	public EasyUITable doGetPageObjects(Integer page, Integer rows) {
		if(page<0||rows<0) throw new ServiceException("参数异常");
		EasyUITable result = new EasyUITable();
		
		//1.根据page和rows封装分页查询page对象
		Page<Item> pages = new Page<>(page,rows);
		
		//2.设置QueryWrapper按updated排序
		QueryWrapper<Item> query = new QueryWrapper<>();
		query.orderByDesc("updated");
		
		//3.执行查询
		IPage<Item> records = itemMapper.selectPage(pages, query);
		
		//4.用getRecords()获取查询结果
		result.setRows(records.getRecords());
		
		//5.getTotal()获取总的结果数
		result.setTotal((int) records.getTotal());
		
		return result;
	}
	
	@Transactional
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date())
		.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		itemDesc.setItemId(item.getId()).setCreated(new Date());
		itemDescMapper.insert(itemDesc);
	}
	
	@Transactional
	@Override
	@CacheUpdate
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1).setUpdated(new Date());
		itemMapper.updateById(item);
		
		itemDesc.setItemId(item.getId()).setCreated(new Date());
		itemDescMapper.updateById(itemDesc);
	}
	
	/** 删除商品 */
	@Transactional
	@Override
	@CacheUpdate
	public void deleteItem(Long[] ids) {
		itemMapper.deleteBatchIds(Arrays.asList(ids));
	}
	
	/** 下架 */
	@Transactional
	@Override
	public void instockItem(Long[] ids, int i) {
		Item item = new Item();
		item.setStatus(i).setUpdated(new Date());
		QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
		queryWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item, queryWrapper);
	}
	
	/** 回显商品描述信息 */
	@Override
	@Cache
	public ItemDesc queryItemDesc(Long id) {
		ItemDesc iDesc = itemDescMapper.selectById(id);
		if(iDesc==null) throw new ServiceException("没有改商品描述信息");
		return iDesc;
	}
	
	/** 回显商品规格信息 */
	@Override
	@Cache
	public ItemParam queryItemParam(Long id) {
		ItemParam itemParam = itemParamMapper.selectById(id);
		if(itemParam==null) throw new ServiceException("没有改商品规格信息");
		return itemParam;
	}
	
	/**
	 * 	web item查询
	 */
	@Cache
	@Override
	public Item findItemById(Long itemId) {
		if(itemId==null||itemId<=0) throw new RuntimeException("参数异常");
		Item item = itemMapper.selectById(itemId);
		if(StringUtils.isEmpty(item)) throw new RuntimeException("没有该商品信息");
		return item;
	}
	
	/**
	 * 	web itemdesc查询
	 */
	@Cache
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		if(itemId==null||itemId<=0) throw new RuntimeException("参数异常");
		ItemDesc itemDesc = itemDescMapper.selectById(itemId);
		if(StringUtils.isEmpty(itemDesc)) throw new RuntimeException("没有该商品信息");
		return itemDesc;
	}
}
