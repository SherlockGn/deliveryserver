package com.gth.delivery.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;
import com.gth.delivery.util.ReturnPackage;
import com.gth.delivery.util.StringUtils;

@Controller
public class UserController {

	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String rootTest(HttpServletRequest request, Model model) {

		User user = deliveryService.getUserByUsername("abcd");
		System.out.println(JSONObject.toJSONString(user));

		return "mainpage";
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username) {

		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			return new User(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(null) && !StringUtils.isNull(username)) {
			return new User(-1, "reduplicative param id & username");
		}
		if (!StringUtils.isNull(id)) {
			if (StringUtils.isNumber(id) == false)
				return new User(-1, "id seems not a number: " + id);
			User user = deliveryService.getUserById(Integer.parseInt(id));
			if (user == null)
				return new User(-1, "the user doesn't exist");
			else
				return user;
		}
		User user = deliveryService.getUserByUsername(username);
		if (user == null)
			return new User(-1, "the user doesn't exist");
		else
			return user;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage registerUser(HttpServletRequest request, Model model,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address) {

		boolean noNull = true, first = true;
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNull(username)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("username");
		}
		if (StringUtils.isNull(password)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("password");
		}
		if (StringUtils.isNull(gender)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("gender");
		}
		if (StringUtils.isNull(name)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("name");
		}
		if (StringUtils.isNull(phone)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("phone");
		}
		if (StringUtils.isNull(address)) {
			noNull = false;
			if (!first)
				sb.append(" ");
			first = false;
			sb.append("address");
		}
		if (noNull == false) {
			return new ReturnPackage(-1, sb.toString() + " is null");
		}
		User user = deliveryService.getUserByUsername(username);
		if (user != null) {
			return new ReturnPackage(-1, "this username has exsisted");
		}
		int genderConverse = StringUtils.legalGender(gender);
		if (genderConverse == -1) {
			return new ReturnPackage(-1, "gender input is not legal");
		}
		String phoneSettle = StringUtils.phoneSettle(phone);
		if (phoneSettle == null) {
			return new ReturnPackage(-1, "phone input is not legal");
		}
		String addressSettle = StringUtils.addressSettle(address);
		if (addressSettle == null) {
			return new ReturnPackage(-1, "address input is not legal");
		}
		user = new User(null, genderConverse == 1 ? true : false, new Date(), name.trim(), username, password.trim(),
				phoneSettle, addressSettle);

		int id = -1;
		try {
			deliveryService.insertUser(user);
			id = deliveryService.getUserByUsername(username).getId();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(id, "register success");
	}

	@RequestMapping(value = "/setUser", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage setUser(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address) {
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "reduplicative param id & username");
		}
		if (StringUtils.isNull(password) && StringUtils.isNull(gender) && StringUtils.isNull(name)
				&& StringUtils.isNull(phone) && StringUtils.isNull(address)) {
			return new ReturnPackage(-1, "args are all null");
		}
		User user;
		if (!StringUtils.isNull(id)) {
			if (StringUtils.isNumber(id) == false)
				return new ReturnPackage(-1, "id seems not a number: " + id);
			user = deliveryService.getUserById(Integer.parseInt(id));
			if (user == null)
				return new ReturnPackage(-1, "the user doesn't exist");
		} else {
			user = deliveryService.getUserByUsername(username);
			if (user == null)
				return new ReturnPackage(-1, "the user doesn't exist");
		}
		if (!StringUtils.isNull(password))
			password = password.trim();
		Boolean genderBool = null;
		if (!StringUtils.isNull(gender)) {
			int genderConverse = StringUtils.legalGender(gender);
			if (genderConverse == -1) {
				return new ReturnPackage(-1, "gender input is not legal");
			}
			genderBool = genderConverse == 1 ? true : false;
		}
		if (!StringUtils.isNull(name))
			name = name.trim();
		if (!StringUtils.isNull(phone)) {
			phone = StringUtils.phoneSettle(phone);
			if (phone == null)
				return new ReturnPackage(-1, "phone input is not legal");
		}
		if (!StringUtils.isNull(address)) {
			address = StringUtils.addressSettle(address);
			if (address == null)
				return new ReturnPackage(-1, "address input is not legal");
		}
		user = new User(user.getId(), genderBool, null, name, null, password, phone, address);

		try {
			deliveryService.updateUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(user.getId(), "reset success");
	}

	@RequestMapping(value = "/appendUser", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage appendUser(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address) {
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "reduplicative param id & username");
		}
		if (StringUtils.isNull(phone) && StringUtils.isNull(address)) {
			return new ReturnPackage(-1, "phone and address are all null");
		}
		User user;
		if (!StringUtils.isNull(id)) {
			if (StringUtils.isNumber(id) == false)
				return new ReturnPackage(-1, "id seems not a number: " + id);
			user = deliveryService.getUserById(Integer.parseInt(id));
			if (user == null)
				return new ReturnPackage(-1, "the user doesn't exist");
		} else {
			user = deliveryService.getUserByUsername(username);
			if (user == null)
				return new ReturnPackage(-1, "the user doesn't exist");
		}

		if (!StringUtils.isNull(phone)) {
			phone = phone.trim();
			if (!StringUtils.isNumber(phone))
				return new ReturnPackage(-1, "the phone seems not a number");
			user.setPhone(user.getPhone() + ";" + phone);
		} else
			user.setPhone(null);

		if (!StringUtils.isNull(address)) {
			address = address.trim();
			if (address.contains(";"))
				return new ReturnPackage(-1, "the address contains character \";\"");
			user.setAddress(user.getAddress() + ";" + address);
		} else
			user.setAddress(null);
		user = new User(user.getId(), null, null, null, null, null, user.getPhone(), user.getAddress());
		try {
			deliveryService.updateUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(user.getId(), "append success");
	}
}
