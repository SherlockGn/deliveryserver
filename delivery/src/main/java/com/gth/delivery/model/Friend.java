package com.gth.delivery.model;

import java.util.Date;

public class Friend {
	private Integer id1;

	private Integer id2;

	private Date time;

	public Friend(Integer id1, Integer id2, Date time) {
		this.id1 = id1;
		this.id2 = id2;
		this.time = time;
	}

	public Friend() {
	}

	public Integer getId1() {
		return id1;
	}

	public void setId1(Integer id1) {
		this.id1 = id1;
	}

	public Integer getId2() {
		return id2;
	}

	public void setId2(Integer id2) {
		this.id2 = id2;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}