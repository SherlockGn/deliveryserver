package com.wsn.delivery.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wsn.delivery.R;
import com.wsn.delivery.indent.indentVo;

public class indentDetail extends Activity {
	TextView id;
	TextView tv_fromUserName, tv_fromUserPhone, tv_fromUserAddress;
	TextView tv_toUserName, tv_toUserPhone, tv_toUserAddress;
	LinearLayout ll_courier,ll_price;
	TextView tv_courierName, tv_courierPhone;
	TextView tv_price,tv_state;
//	private String url = "http://10.128.19.233:8080/delivery/";//服务器地址
	indentVo indent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indent_detail);
		indent = (indentVo) getIntent().getSerializableExtra("indent");
		System.out.println(indent.toString());
		initView();
		init();
	}

	private void initView() {
		id = (TextView) findViewById(R.id.tv_indent_id);
		tv_fromUserName = (TextView) findViewById(R.id.tv_from_username);
		tv_fromUserPhone = (TextView) findViewById(R.id.tv_from_user_phone);
		tv_fromUserAddress = (TextView) findViewById(R.id.tv_from_user_address);
		tv_toUserName = (TextView) findViewById(R.id.tv_to_username);
		tv_toUserPhone = (TextView) findViewById(R.id.tv_to_user_phone);
		tv_toUserAddress = (TextView) findViewById(R.id.tv_to_user_address);
		ll_courier = (LinearLayout) findViewById(R.id.ll_courier);
		tv_courierName = (TextView) findViewById(R.id.tv_courier_name);
		tv_courierPhone = (TextView) findViewById(R.id.tv_courier_phone);
		ll_price = (LinearLayout) findViewById(R.id.ll_price);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_state = (TextView) findViewById(R.id.tv_indent_state);
	}
	
	private void init() {
		if(indent.getId() != null && !"-1".equals(indent.getId())){
			id.setText("订单号："+indent.getId());
			if(indent.getFromuserid() != null && !"".equals(indent.getFromuserid())){
				setfromUser(indent.getFromuserid());
			}
			if(indent.getTouserid() != null && !"".equals(indent.getTouserid())){
				setToUser(indent.getTouserid());
			}
			if(indent.getCourierid() == null || "".equals(indent.getCourierid()) || "null".equals(indent.getCourierid())){
				ll_courier.setVisibility(View.GONE);
			}else{
				System.out.println("indent.getCourierid: "+indent.getCourierid());
				setCourier(indent.getCourierid());
			}
			if(indent.getPrice() == null || "".equals(indent.getPrice()) || "null".equals(indent.getPrice())){
				ll_price.setVisibility(View.GONE);
			}else{
				System.out.println("indent.getPrice: "+indent.getPrice());
				tv_price.setText("人民币: "+indent.getPrice()+"￥");
			}
			if(indent.getState() != null && !"".equals(indent.getState()) && !"null".equals(indent.getState())){
				setIndentState(indent.getState());
			}
		}else{
			Toast.makeText(indentDetail.this, "订单出错", Toast.LENGTH_LONG).show();
		}
	}


	private void setIndentState(String state) {
		if("0".equals(state)){
			tv_state.setText("快递员尚未派件");
		}else if("1".equals(state)){
			tv_state.setText("快递员正在派件");
		}else if("2".equals(state)){
			tv_state.setText("收件人已签收");
		}
	}

	private void setCourier(String courierid) {
		RequestQueue rq = Volley.newRequestQueue(indentDetail.this);
		String URL = getResources().getString(R.string.url)+"getCourier?id="+courierid;
		System.out.println(URL);
		Request<JSONObject> req_Courier = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if(response.getString("id") != null &&!"-1".equals(response.getString("id"))){
						tv_courierName.setText(response.getString("name"));
						tv_courierPhone.setText(response.getString("phone"));
					}else{
						Toast.makeText(indentDetail.this, "快递员不存在"+response.getString("name"), Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(indentDetail.this, error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		rq.add(req_Courier);
	}

	private void setfromUser(String fromuserid) {
		tv_fromUserPhone.setText(indent.getFromphone());
		tv_fromUserAddress.setText(indent.getFromaddress());
		RequestQueue rq = Volley.newRequestQueue(indentDetail.this);
		String URL = getResources().getString(R.string.url)+"getUser?id="+fromuserid;
		System.out.println(URL);
		Request<JSONObject> req_loginUser = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if(response.getString("id") != null &&!"-1".equals(response.getString("id"))){
						tv_fromUserName.setText(response.getString("name"));
					}else{
						Toast.makeText(indentDetail.this, "寄件人不存在", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(indentDetail.this, error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		rq.add(req_loginUser);
	}
	
	private void setToUser(String touserid) {
		tv_toUserPhone.setText(indent.getTophone());
		tv_toUserAddress.setText(indent.getToaddress());
		RequestQueue rq = Volley.newRequestQueue(indentDetail.this);
		String URL = getResources().getString(R.string.url)+"getUser?id="+touserid;
		Request<JSONObject> req_loginUser = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					if(response.getString("id") != null && !"-1".equals(response.getString("id"))){
						tv_toUserName.setText(response.getString("name"));
					}else{
						Toast.makeText(indentDetail.this, "收件人不存在", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(indentDetail.this, error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		rq.add(req_loginUser);
	}
}
