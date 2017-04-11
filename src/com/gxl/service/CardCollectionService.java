package com.gxl.service;

import java.util.List;
import java.util.Map;

import com.gxl.entity.CardCollection;

public interface CardCollectionService extends BaseService<CardCollection> {

	public List<Map<String, Object>> shareCardHolders(Integer userid);
}
