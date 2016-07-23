package com.gth.delivery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gth.delivery.model.Friend;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;
import com.gth.delivery.util.ReturnPackage;
import com.gth.delivery.util.StringUtils;

@Controller
public class FriendController {

	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(value = "/addFriend", method = RequestMethod.GET)
	@ResponseBody
	public ReturnPackage addFriend(HttpServletRequest request, Model model,
			@RequestParam(value = "id1", required = false) String id1,
			@RequestParam(value = "username1", required = false) String username1,
			@RequestParam(value = "id2", required = false) String id2,
			@RequestParam(value = "username2", required = false) String username2) {
		if (StringUtils.isNull(id1) && StringUtils.isNull(username1)) {
			return new ReturnPackage(-1, "lack param id1 & username1");
		}
		if (!StringUtils.isNull(id1) && !StringUtils.isNull(username1)) {
			return new ReturnPackage(-1, "reduplicative param id1 & username1");
		}
		User user1 = null;
		if (!StringUtils.isNull(id1)) {
			if (!StringUtils.isNumber(id1))
				return new ReturnPackage(-1, "id1 seems not a number");
			user1 = deliveryService.getUserById(Integer.parseInt(id1));
			if (user1 == null)
				return new ReturnPackage(-1, "the user1 doesn't exist");
		} else {
			user1 = deliveryService.getUserByUsername(username1);
			if (user1 == null)
				return new ReturnPackage(-1, "the user1 doesn't exist");
		}

		if (StringUtils.isNull(id2) && StringUtils.isNull(username2)) {
			return new ReturnPackage(-1, "lack param id2 & username2");
		}
		if (!StringUtils.isNull(id2) && !StringUtils.isNull(username2)) {
			return new ReturnPackage(-1, "reduplicative param id2 & username2");
		}
		User user2 = null;
		if (!StringUtils.isNull(id2)) {
			if (!StringUtils.isNumber(id2))
				return new ReturnPackage(-1, "id2 seems not a number");
			user2 = deliveryService.getUserById(Integer.parseInt(id2));
			if (user2 == null)
				return new ReturnPackage(-1, "the user2 doesn't exist");
		} else {
			user2 = deliveryService.getUserByUsername(username2);
			if (user2 == null)
				return new ReturnPackage(-1, "the user2 doesn't exist");
		}

		if (user1.getId().equals(user2.getId()))
			return new ReturnPackage(-1, "the user1 & user2 are the same");

		Friend friend = deliveryService.findFriendById(user1.getId(), user2.getId());
		if (friend != null)
			return new ReturnPackage(-1, "the user1 & user2 have already been friends");
		friend = deliveryService.findFriendById(user2.getId(), user1.getId());
		if (friend != null)
			return new ReturnPackage(-1, "the user1 & user2 have already been friends");

		try {
			deliveryService.insertFriend(user1.getId(), user2.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ReturnPackage(-1, ex.getMessage());
		}
		return new ReturnPackage(1, "add friend success");
	}

	@RequestMapping(value = "/getFriend", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getFriend(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "username", required = false) String username) {

		List<User> returnList = new ArrayList<User>();

		if (StringUtils.isNull(id) && StringUtils.isNull(username)) {
			returnList.add(new User(-1, "lack param id & username"));
			return returnList;
		}
		if (!StringUtils.isNull(id) && !StringUtils.isNull(username)) {
			returnList.add(new User(-1, "reduplicative param id & username"));
			return returnList;
		}
		User user = null;
		if (!StringUtils.isNull(id)) {
			if (!StringUtils.isNumber(id)) {
				returnList.add(new User(-1, "id seems not a number"));
				return returnList;
			}
			user = deliveryService.getUserById(Integer.parseInt(id));
			if (user == null) {
				returnList.add(new User(-1, "the user doesn't exist"));
				return returnList;
			}
		} else {
			user = deliveryService.getUserByUsername(username);
			if (user == null) {
				returnList.add(new User(-1, "the user doesn't exist"));
				return returnList;
			}
		}

		List<Friend> friends = deliveryService.findFriendByOneId(user.getId());
		if (friends == null)
			return returnList;
		List<Integer> otherFriendId = new ArrayList<Integer>();
		for (Friend friend : friends) {
			if (user.getId().equals(friend.getId1()))
				otherFriendId.add(friend.getId2());
			if (user.getId().equals(friend.getId2()))
				otherFriendId.add(friend.getId1());
		}
		returnList = deliveryService.findUsersByIds(otherFriendId);
		if (returnList == null)
			returnList = new ArrayList<User>();
		return returnList;
	}
}
