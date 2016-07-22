package com.gth.delivery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;

@Controller
public class UserController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	
	@RequestMapping(value="/getUser", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(HttpServletRequest request, Model model,
			@RequestParam(value="id", required=true) String id) {
		return deliveryService.getUserById(Integer.parseInt(id));
	}
}
