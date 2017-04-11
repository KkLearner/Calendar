package com.gxl.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxl.common.utils.ResultReturn;
import com.gxl.dao.TestDao;
import com.gxl.dao.impl.TestDaoImpl;
import com.gxl.entity.Test;
import com.gxl.service.TestService;

@Controller
@RequestMapping(value="/Test")
public class TestAct {

	@Autowired
	private TestService testService;
	@Autowired
	private TestDaoImpl td;
	
	@ResponseBody		
	@RequestMapping(value = "/Add", method = RequestMethod.POST,headers = "Accept=application/json" )
	public Map<String,Object> add(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		Test test=new Test("拉力赛的", new Date(), 9.123);
		testService.add(test);
		return ResultReturn.setMap(result, 0, "success", null);
	}
	
	@ResponseBody		
	@RequestMapping(value = "/Delete", method = RequestMethod.POST,headers = "Accept=application/json" )
	public Map<String,Object> delete(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		Test t=testService.findByUniqueProperty("id", 1);
		testService.delete(t);
		return ResultReturn.setMap(result, 0, "success", null);
	}
	
	@ResponseBody		
	@RequestMapping(value = "/Find", method = RequestMethod.POST,headers = "Accept=application/json" )
	public Map<String,Object> find(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		String sql="from Test a";
		map.put("money", 9.11);
		return ResultReturn.setMap(result, 0, "success",testService.find(sql));
	}
	
	@ResponseBody		
	@RequestMapping(value = "/Update", method = RequestMethod.POST,headers = "Accept=application/json" )
	public Map<String,Object> update(@RequestParam Map<String,Object>map,HttpServletRequest request, HttpServletResponse response,HttpSession session ,Model model) throws UnsupportedEncodingException, ClassNotFoundException, NoSuchFieldException, SecurityException, ParseException {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Test test=testService.getById(1);
			test.setBirthday(new Date());
			testService.update(test);
			//testService.check();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultReturn.setMap(result, 2, "false",null);
		}
		
		return ResultReturn.setMap(result, 0, "success",null);
	}
}
