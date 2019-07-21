package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.Cache;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.pojo.JsonTree;
import com.jt.pojo.SonJsonTree;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//使用分片
	//@Autowired
	//@Qualifier("shardedJedis")
	//private ShardedJedis jedis;
	
	//使用哨兵
	//@Autowired
	//@Qualifier("jedis")
	//private Jedis jedisSentinel;
	
	//使用集群
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster cluster;
	
	/** 显示商品分类名 */
	@Cache
	public String queryItemName(Integer itemCatId) {
		if(itemCatId==null) return null;
		QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
		queryWrapper.select("name");
		String name = itemCatMapper.queryNameById(itemCatId);
		return name;
	}
	
	//先查Redis缓存
	@Cache
	public List<JsonTree> findItemCatByCache(Long parentId) {
		List<JsonTree> tree = geListTree(parentId);
		return tree;
	}
	
	
	/** 获取商品分类信息树json */
	@Override
	public List<JsonTree> getJsonTree() {
		//1.获取一级目录
		List<JsonTree> result = itemCatMapper.findfirstTree();
		//2.获取一级目录对应的子目录
		result.forEach(e->{
			Long id = e.getId();
			List<JsonTree> secondTree = itemCatMapper.findsecondTree(id);
			//3.获取三级目录对应的子目录
			secondTree.forEach(f->{
				Long id2 = f.getId();
				List<SonJsonTree> thirdTree = itemCatMapper.findthirdTree(id2);
				f.setChildren(thirdTree);
			});
			e.setChildren(secondTree);
		});
		System.out.println(result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<JsonTree> getJsonTree01() {
		//1.获取一级目录
		List<JsonTree> result = itemCatMapper.findTree();
		result.forEach(e->{
			e.setState("closed");
			List<JsonTree> children = (List<JsonTree>) e.getChildren();
			children.forEach(f->{
				f.setState("closed");
			});
		});
		return result;
	}
	
	@Override
	public List<JsonTree> geListTree(Long parentId) {
		List<JsonTree> jsonTree = new ArrayList<JsonTree>();
		List<ItemCat> resultList = getResultList(parentId);
		for(ItemCat cat:resultList) {
			Long id = cat.getId();
			String text = cat.getName();
			JsonTree jt = new JsonTree(id,text);
			jt.setState(cat.getIsParent()?"closed":null);
			jsonTree.add(jt);
		}
		return jsonTree;
	}

	private List<ItemCat> getResultList(Long parentId) {
		QueryWrapper<ItemCat> query = new QueryWrapper<>();
		query.eq("parent_id", parentId);
		List<ItemCat> result = itemCatMapper.selectList(query);
		return result;
	}
	
	
}
