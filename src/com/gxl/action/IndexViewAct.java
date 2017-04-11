package com.gxl.action;

import javax.naming.NamingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexViewAct {
	
	@RequestMapping("Test")
	public String Test(Model model) throws NamingException{
		return "Tests";
	}
	
	@RequestMapping("GxlUser")
	public String GxlUser(Model model) throws NamingException{
		return "GxlUsers";
	}
	
	@RequestMapping("Task")
	public String Task(Model model) throws NamingException{
		return "Tasks";
	}
	
	@RequestMapping("Friend")
	public String Friend(Model model) throws NamingException{
		return "Friends";
	}
	
	@RequestMapping("Share")
	public String Share(Model model) throws NamingException{
		return "Shares";
	}
}
