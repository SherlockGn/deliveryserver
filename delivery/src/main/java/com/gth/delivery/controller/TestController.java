package com.gth.delivery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gth.delivery.service.DeliveryService;

@Controller
public class TestController {
	
	@SuppressWarnings("unused")
	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String rootTest(HttpServletRequest request, Model model) {
		return "test";
	}
	
	@RequestMapping(value = "/getArgs", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<String>> getArgs(HttpServletRequest request, Model model) {
		Map<String, List<String>> map = new TreeMap<String, List<String>>();
		map.put("getUser", new ArrayList<String>());
		map.get("getUser").add("id");
		map.get("getUser").add("username");
		map.put("registerUser", new ArrayList<String>());
		map.get("registerUser").add("username");
		map.get("registerUser").add("password");
		map.get("registerUser").add("gender");
		map.get("registerUser").add("name");
		map.get("registerUser").add("phone");
		map.get("registerUser").add("address");
		map.put("setUser", new ArrayList<String>());
		map.get("setUser").add("id");
		map.get("setUser").add("username");
		map.get("setUser").add("password");
		map.get("setUser").add("gender");
		map.get("setUser").add("name");
		map.get("setUser").add("phone");
		map.get("setUser").add("address");
		map.put("appendUser", new ArrayList<String>());
		map.get("appendUser").add("id");
		map.get("appendUser").add("username");
		map.get("appendUser").add("phone");
		map.get("appendUser").add("address");
		map.put("loginUser", new ArrayList<String>());
		map.get("loginUser").add("username");
		map.get("loginUser").add("password");
		map.put("registerCourier", new ArrayList<String>());
		map.get("registerCourier").add("username");
		map.get("registerCourier").add("password");
		map.get("registerCourier").add("name");
		map.get("registerCourier").add("phone");
		map.put("getCourier", new ArrayList<String>());
		map.get("getCourier").add("id");
		map.get("getCourier").add("username");
		map.put("setUser", new ArrayList<String>());
		map.get("setUser").add("id");
		map.get("setUser").add("username");
		map.get("setUser").add("password");
		map.get("setUser").add("name");
		map.get("setUser").add("phone");
		map.put("loginUser", new ArrayList<String>());
		map.get("loginUser").add("username");
		map.get("loginUser").add("password");
		map.put("addFriend", new ArrayList<String>());
		map.get("addFriend").add("id1");
		map.get("addFriend").add("username1");
		map.get("addFriend").add("id2");
		map.get("addFriend").add("username2");
		map.put("getFriend", new ArrayList<String>());
		map.get("getFriend").add("id");
		map.get("getFriend").add("username");
		map.put("createIndent", new ArrayList<String>());
		map.get("createIndent").add("fromuserid");
		map.get("createIndent").add("touserid");
		map.get("createIndent").add("fromphone");
		map.get("createIndent").add("tophone");
		map.get("createIndent").add("fromaddress");
		map.get("createIndent").add("toaddress");
		map.get("createIndent").add("price");
		map.put("getSendedIndent", new ArrayList<String>());
		map.get("getSendedIndent").add("username");
		map.get("getSendedIndent").add("id");
		map.put("getReceivedIndent", new ArrayList<String>());
		map.get("getReceivedIndent").add("username");
		map.get("getReceivedIndent").add("id");
		map.put("getIndent", new ArrayList<String>());
		map.get("getIndent").add("id");
		map.put("remainingIndent", new ArrayList<String>());
		map.get("remainingIndent").add("id");
		map.get("remainingIndent").add("username");
		map.put("courierScan", new ArrayList<String>());
		map.get("courierScan").add("courierid");
		map.get("courierScan").add("courierusername");
		map.get("courierScan").add("indentid");
		map.put("userScan", new ArrayList<String>());
		map.get("userScan").add("userid");
		map.get("userScan").add("userusername");
		map.get("userScan").add("indentid");
		map.put("getArgs", new ArrayList<String>());
		return map;
	}
}
