package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
import com.jt.pojo.JsonTree;
import com.jt.pojo.SonJsonTree;

public interface ItemCatMapper extends BaseMapper<ItemCat> {

	@Select("select name from tb_item_cat where id=#{id}")
	String queryNameById(@Param("id")Integer itemCatId);

	List<JsonTree> findfirstTree();
	
	List<JsonTree> findsecondTree(@Param("id")Long id);

	List<SonJsonTree> findthirdTree(@Param("id")Long id);

	List<JsonTree> findTree();
	
}
