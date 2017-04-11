package com.gxl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxl.dao.TestDao;
import com.gxl.entity.Test;
import com.gxl.service.TestService;

@Transactional
@Service("testService")
public class TestServiceImpl extends BaseServiceImpl<Test> implements TestService {

	@Autowired
	private TestDao testDao;
	public void check(){
		testDao.check();
	}
}
