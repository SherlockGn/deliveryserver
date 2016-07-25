package com.gth.delivery.model;

import java.util.Date;

public class Indent {
	private Integer id;

	private Integer fromuserid;

	private Integer touserid;

	private Double price;

	private Integer courierid;

	private Integer state;

	private Date time;

	private String fromphone;

	private String tophone;

	private String fromaddress;

	private String toaddress;

	private String secretcode;

	public Indent(Integer id, Integer fromuserid, Integer touserid, Double price, Integer courierid, Integer state,
			Date time, String fromphone, String tophone, String fromaddress, String toaddress, String secretcode) {
		this.id = id;
		this.fromuserid = fromuserid;
		this.touserid = touserid;
		this.price = price;
		this.courierid = courierid;
		this.state = state;
		this.time = time;
		this.fromphone = fromphone;
		this.tophone = tophone;
		this.fromaddress = fromaddress;
		this.toaddress = toaddress;
		this.secretcode = secretcode;
	}

	public Indent() {
	}

	public Indent(Integer id, String secretcode) {
		this.id = id;
		this.secretcode = secretcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromuserid() {
		return fromuserid;
	}

	public void setFromuserid(Integer fromuserid) {
		this.fromuserid = fromuserid;
	}

	public Integer getTouserid() {
		return touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCourierid() {
		return courierid;
	}

	public void setCourierid(Integer courierid) {
		this.courierid = courierid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFromphone() {
		return fromphone;
	}

	public void setFromphone(String fromphone) {
		this.fromphone = fromphone == null ? null : fromphone.trim();
	}

	public String getTophone() {
		return tophone;
	}

	public void setTophone(String tophone) {
		this.tophone = tophone == null ? null : tophone.trim();
	}

	public String getFromaddress() {
		return fromaddress;
	}

	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress == null ? null : fromaddress.trim();
	}

	public String getToaddress() {
		return toaddress;
	}

	public void setToaddress(String toaddress) {
		this.toaddress = toaddress == null ? null : toaddress.trim();
	}

	public String getSecretcode() {
		return secretcode;
	}

	public void setSecretcode(String secretcode) {
		this.secretcode = secretcode == null ? null : secretcode.trim();
	}
}