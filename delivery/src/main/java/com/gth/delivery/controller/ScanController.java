package com.gth.delivery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gth.delivery.model.Courier;
import com.gth.delivery.model.Indent;
import com.gth.delivery.service.DeliveryService;
import com.gth.delivery.util.ReturnPackage;
import com.gth.delivery.util.StringUtils;

@Controller
public class ScanController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@RequestMapping(value="/courierScan", method=RequestMethod.GET)
	@ResponseBody
	public ReturnPackage courierScan(HttpServletRequest request, Model model,
			@RequestParam(value="courierid", required=false) String courierid,
			@RequestParam(value="courierusername", required=false) String courierusername,
			@RequestParam(value="indentid", required=false) String indentid
			) {
		if (StringUtils.isNull(courierid) && StringUtils.isNull(courierusername)) {
			return new ReturnPackage(-1, "lack param id & username");
		}
		if (!StringUtils.isNull(courierid) && !StringUtils.isNull(courierusername)) {
			return new ReturnPackage(-1, "reduplicative param id & username");
		}
		Courier courier;
		if (!StringUtils.isNull(courierid)) {
			if (StringUtils.isNumber(courierid) == false)
				return new ReturnPackage(-1, "courier id seems not a number: " + courierid);
			courier = deliveryService.findCourierById(Integer.parseInt(courierid));
			if (courier == null)
				return new ReturnPackage(-1, "the courier doesn't exist");
		} else {
			courier = deliveryService.findCourierByUsername(courierusername);
			if (courier == null)
				return new ReturnPackage(-1, "the courier doesn't exist");
		}
		
		if (indentid == null)
			return new ReturnPackage(-1, "id is null");
		if (!StringUtils.isNumber(indentid))
			return new ReturnPackage(-1, "id seems not a number");
		Indent indent = deliveryService.findIndentById(Integer.parseInt(indentid));
		if (indent == null)
			return new ReturnPackage(-1, "indent doesn't exist");
		
		deliveryService.updateIndent(new Indent(indent.getId(), null, null, null, courier.getId(), 1, null, null, null, null, null, null));
		return new ReturnPackage(-1, "courier scan success");
	}
}
