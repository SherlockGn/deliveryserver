package com.wsn.delivery.courier;

import java.io.Serializable;

import android.content.ContentValues;
import android.text.TextUtils;

public class CourierVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String loginName;//用户名
	private String password;//密码
	private String name;//昵称
	private String phone;//电话
	private int issaved;//是否选中保存密码  1保存，0不保存
	private String lastLoginTime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIssaved() {
		return issaved;
	}

	public void setIssaved(int i) {
		this.issaved = i;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	protected ContentValues toContVals() {
		ContentValues userContent = new ContentValues();
	    userContent.put("userId", this.userId);
	    userContent.put("userName", this.name);
	    userContent.put("loginName", this.loginName);
	    if (!TextUtils.isEmpty(this.password))
	      userContent.put("password", this.password);
	    userContent.put("lastLoginTime", this.lastLoginTime);
	    userContent.put("issaved", this.issaved);
	    return userContent;
	}
}
