package com.gxl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.CardCollectionDao;
import com.gxl.entity.CardCollection;
import com.gxl.service.CardCollectionService;

@Transactional
@Service("cardCollectionService")
public class CardCollectionServiceImpl extends BaseServiceImpl<CardCollection> implements CardCollectionService {

	@Autowired
	private CardCollectionDao cardCollectionDao;
	
	@Override
	public List<Map<String, Object>> shareCardHolders(Integer userid){
		return cardCollectionDao.shareCardHolders(userid);
	}
}
