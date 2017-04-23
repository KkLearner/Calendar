package com.gxl.dao;

import java.util.List;
import java.util.Map;

import com.gxl.entity.CardCollection;

public interface CardCollectionDao extends BaseDao<CardCollection> {

	public List<Map<String, Object>> shareCardHolders(Integer userid,String group_name);

}
