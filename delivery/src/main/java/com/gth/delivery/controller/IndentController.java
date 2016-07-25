package com.gth.delivery.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gth.delivery.model.Indent;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;
import com.gth.delivery.util.ReturnPackage;
import com.gth.delivery.util.StringUtils;

@Controller
public class IndentController {

	@Autowired
	DeliveryService deliveryService;

	@RequestMapping(value = "/createIndent", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage createIndent(HttpServletRequest request, Model model,
			@RequestParam(value = "fromuserid", required = false) String fromuserid,
			@RequestParam(value = "touserid", required = false) String touserid,
			@RequestParam(value = "fromphone", required = false) String fromphone,
			@RequestParam(value = "tophone", required = false) String tophone,
			@RequestParam(value = "fromaddress", required = false) String fromaddress,
			@RequestParam(value = "toaddress", required = false) String toaddress,
			@RequestParam(value = "price", required = false) String price
	// @RequestParam(value = "courierid", required = false) String courierid,
	// @RequestParam(value = "state", required = false) String state,
	// @RequestParam(value = "secret", required = false) String secret
	) {
		if (StringUtils.isNull(fromuserid))
			return new ReturnPackage(-1, "fromuserid is null");
		if (StringUtils.isNull(touserid))
			return new ReturnPackage(-1, "touserid is null");
		if (StringUtils.isNull(fromphone))
			return new ReturnPackage(-1, "fromphone is null");
		if (StringUtils.isNull(tophone))
			return new ReturnPackage(-1, "tophone is null");
		if (StringUtils.isNull(fromaddress))
			return new ReturnPackage(-1, "fromaddress is null");
		if (StringUtils.isNull(toaddress))
			return new ReturnPackage(-1, "toaddress is null");
		if (!StringUtils.isNumber(fromuserid))
			return new ReturnPackage(-1, "fromuserid seems not a number");
		if (!StringUtils.isNumber(touserid))
			return new ReturnPackage(-1, "touserid seems not a number");
		if (!StringUtils.isNumber(fromphone))
			return new ReturnPackage(-1, "fromphone seems not a number");
		if (!StringUtils.isNumber(tophone))
			return new ReturnPackage(-1, "tophone seems not a number");
		if (!StringUtils.isNull(price) && !StringUtils.checkPrice(price))
			return new ReturnPackage(-1, "price seems not a float number");

		if (deliveryService.findUserById(Integer.parseInt(fromuserid)) == null)
			return new ReturnPackage(-1, "from user doesn't exist");
		if (deliveryService.findUserById(Integer.parseInt(touserid)) == null)
			return new ReturnPackage(-1, "to user doesn't exist");

		Indent indent = new Indent(null, Integer.parseInt(fromuserid), Integer.parseInt(touserid),
				price == null ? null : Double.parseDouble(price), null, 0, new Date(), fromphone.trim(), tophone.trim(),
				fromaddress.trim(), toaddress.trim(), StringUtils.genRandom(20));
		try {
			deliveryService.createIndent(indent);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(indent.getId(), "create indent success");
	}

	@RequestMapping(value = "/getSendedIndent", method = RequestMethod.GET)
	@ResponseBody
	public List<Indent> getSendedIndent(HttpServletRequest request, Model model,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "id", required = false) String id) {
		List<Indent> lstIndent = new ArrayList<Indent>();
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {

			lstIndent.add(new Indent(-1, "lack param id & username"));
			return lstIndent;
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			lstIndent.add(new Indent(-1, "reduplicative param id & username"));
			return lstIndent;
		}
		User user;
		if (!StringUtils.isNull(id)) {
			if (!StringUtils.isNumber(id)) {
				lstIndent.add(new Indent(-1, "id seems not a number"));
				return lstIndent;
			}
			user = deliveryService.findUserById(Integer.parseInt(id));
			if (user == null) {
				lstIndent.add(new Indent(-1, "the user doesn't exist"));
				return lstIndent;
			}
		} else {
			user = deliveryService.findUserByUsername(username);
			if (user == null) {
				lstIndent.add(new Indent(-1, "the user doesn't exist"));
				return lstIndent;
			}
		}
		return deliveryService.findIndentByFromUserId(user.getId());
	}

	@RequestMapping(value = "/getReceivedIndent", method = RequestMethod.GET)
	@ResponseBody
	public List<Indent> getReceivedIndent(HttpServletRequest request, Model model,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "id", required = false) String id) {
		List<Indent> lstIndent = new ArrayList<Indent>();
		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {

			lstIndent.add(new Indent(-1, "lack param id & username"));
			return lstIndent;
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			lstIndent.add(new Indent(-1, "reduplicative param id & username"));
			return lstIndent;
		}
		User user;
		if (!StringUtils.isNull(id)) {
			if (!StringUtils.isNumber(id)) {
				lstIndent.add(new Indent(-1, "id seems not a number"));
				return lstIndent;
			}
			user = deliveryService.findUserById(Integer.parseInt(id));
			if (user == null) {
				lstIndent.add(new Indent(-1, "the user doesn't exist"));
				return lstIndent;
			}
		} else {
			user = deliveryService.findUserByUsername(username);
			if (user == null) {
				lstIndent.add(new Indent(-1, "the user doesn't exist"));
				return lstIndent;
			}
		}
		return deliveryService.findIndentByToUserId(user.getId());
	}

	@RequestMapping(value = "/getIndent", method = RequestMethod.GET)
	@ResponseBody
	public Indent getIndent(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id) {
		if (id == null)
			return new Indent(-1, "id is null");
		if (!StringUtils.isNumber(id))
			return new Indent(-1, "id seems not a number");
		Indent indent = deliveryService.findIndentById(Integer.parseInt(id));
		if (indent == null)
			return new Indent(-1, "indent doesn't exist");
		return indent;
	}
}
