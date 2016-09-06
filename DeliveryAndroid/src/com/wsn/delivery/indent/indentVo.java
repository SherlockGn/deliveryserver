package com.wsn.delivery.indent;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class indentVo implements Serializable{
	private static final long serialVersionUID = -1234253376072573032L;
	private String id;//������
	private String fromuserid,touserid;//�ռ���id���ļ���id
	private String fromphone,tophone;//�ռ��˵绰���ļ��˵绰
	private String fromaddress,toaddress;//�ռ��˵�ַ���ļ��˵�ַ
	private String price;//��ݼ۸�
	private String courierid;//���Աid
	private String state;//״̬
	private String time;//��������ʱ��
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFromuserid() {
		return fromuserid;
	}
	public void setFromuserid(String fromuserid) {
		this.fromuserid = fromuserid;
	}
	
	public String getTouserid() {
		return touserid;
	}
	public void setTouserid(String touserid) {
		this.touserid = touserid;
	}
	public String getFromphone() {
		return fromphone;
	}
	public void setFromphone(String fromphone) {
		this.fromphone = fromphone;
	}
	public String getTophone() {
		return tophone;
	}
	public void setTophone(String tophone) {
		this.tophone = tophone;
	}
	public String getFromaddress() {
		return fromaddress;
	}
	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}
	public String getToaddress() {
		return toaddress;
	}
	public void setToaddress(String toaddress) {
		this.toaddress = toaddress;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCourierid() {
		return courierid;
	}
	public void setCourierid(String courierid) {
		this.courierid = courierid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Long times = Long.valueOf(time); 
	    String d = format.format(times);
		this.time = d;
		System.out.println(d);
	}
	@Override
	public String toString() {
		return "id:"+id+", fromuserid:"+fromuserid+", touserid:"+touserid
				+", fromphone:"+fromphone+", tophone:"+tophone
				+", fromaddress:"+fromaddress+", toaddress:"+toaddress
				+", price:"+price+", courierid:"+courierid
				+", state:"+state+", time:"+time;
	}
}
