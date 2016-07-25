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

import com.gth.delivery.model.Courier;
import com.gth.delivery.service.DeliveryService;
import com.gth.delivery.util.ReturnPackage;
import com.gth.delivery.util.StringUtils;

@Controller
public class CourierController {

	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(value = "/registerCourier", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage registerCourier(HttpServletRequest request, Model model,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone) {

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
		if (noNull == false) {
			return new ReturnPackage(-1, sb.toString() + " is null");
		}
		Courier courier = deliveryService.findCourierByUsername(username);
		if (courier != null) {
			return new ReturnPackage(-1, "this username has exsisted");
		}
		if (!StringUtils.isNumber(phone)) {
			return new ReturnPackage(-1, "phone input is not legal");
		}
		courier = new Courier(null, new Date(), name.trim(), username.trim(), password.trim(), phone.trim());

		int id = -1;
		try {
			deliveryService.insertCourier(courier);
			id = deliveryService.findCourierByUsername(username).getId();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(id, "register success");
	}

	@RequestMapping(value = "/getCourier", method = RequestMethod.GET)
	@ResponseBody
	public Courier getCourier(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username) {
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			return new Courier(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			return new Courier(-1, "reduplicative param id & username");
		}
		if (!StringUtils.isNull(id)) {
			if (StringUtils.isNumber(id) == false)
				return new Courier(-1, "id seems not a number: " + id);
			Courier courier = deliveryService.findCourierById(Integer.parseInt(id));
			if (courier == null)
				return new Courier(-1, "the courier doesn't exist");
			else
				return courier;
		}
		Courier courier = deliveryService.findCourierByUsername(username);
		if (courier == null)
			return new Courier(-1, "the courier doesn't exist");
		else
			return courier;
	}

	@RequestMapping(value = "/setCourier", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage setUser(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "phone", required = false) String phone) {
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			return new ReturnPackage(-1, "reduplicative param id & username");
		}
		if (StringUtils.isNull(password) && StringUtils.isNull(name) && StringUtils.isNull(phone)) {
			return new ReturnPackage(-1, "args are all null");
		}
		Courier courier;
		if (!StringUtils.isNull(id)) {
			if (StringUtils.isNumber(id) == false)
				return new ReturnPackage(-1, "id seems not a number: " + id);
			courier = deliveryService.findCourierById(Integer.parseInt(id));
			if (courier == null)
				return new ReturnPackage(-1, "the courier doesn't exist");
		} else {
			courier = deliveryService.findCourierByUsername(username);
			if (courier == null)
				return new ReturnPackage(-1, "the courier doesn't exist");
		}
		if (!StringUtils.isNull(password))
			password = password.trim();
		if (!StringUtils.isNull(name))
			name = name.trim();
		if (!StringUtils.isNumber(phone)) {
			return new ReturnPackage(-1, "phone input is not legal");
		}
		courier = new Courier(courier.getId(), null, name, null, password, phone);

		try {
			deliveryService.updateCourier(courier);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(courier.getId(), "reset success");
	}
}