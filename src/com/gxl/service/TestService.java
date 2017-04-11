package com.gxl.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.gxl.entity.Test;

public interface TestService extends BaseService<Test> {
	public void check();
}
