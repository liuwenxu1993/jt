package com.jt.service;

import java.util.List;

import com.jt.pojo.JsonTree;

public interface ItemCatService {

	String queryItemName(Integer itemCatId);

	List<JsonTree> getJsonTree();
	List<JsonTree> getJsonTree01();

	List<JsonTree> geListTree(Long parentId);

	List<JsonTree> findItemCatByCache(Long parentId);

}
