package com.wsn.courier.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wsn.courier.R;
import com.wsn.courier.Session;
import com.wsn.courier.indent.indentVo;
import com.wsn.courier.user.userVo;

public class createIndentActivity extends Activity{
	private TextView tv;
	private userVo friend;
	private EditText et_fromUserPhone,et_fromUserAddress,et_toUserPhone,et_toUserAddress;
	private String[] from_phones,from_addresses,to_phones,to_addresses;
//	private String url = "http://10.128.19.233:8080/delivery/";//服务器地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_indent);
		friend = (userVo) getIntent().getSerializableExtra("friend");
		initView();
	}
	
	
	private void initView() {
		TextView FromUserName = (TextView) findViewById(R.id.from_username);
		et_fromUserPhone = (EditText) findViewById(R.id.from_user_phone);
		et_fromUserAddress = (EditText) findViewById(R.id.from_user_address);
		TextView ToUserName = (TextView) findViewById(R.id.to_username);
		et_toUserPhone = (EditText) findViewById(R.id.to_user_phone);
		et_toUserAddress = (EditText) findViewById(R.id.to_user_address);
		Button create_ok = (Button) findViewById(R.id.btn_create_ok);
		Button create_cancel = (Button) findViewById(R.id.btn_create_cancel);
		
		FromUserName.setText(Session.getCurrUserVO().getName());
		
		from_phones =  Session.getCurrUserVO().getPhone().split(";");
		et_fromUserPhone.setText(from_phones[0]);
		et_fromUserPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder fromPhones = new AlertDialog.Builder(createIndentActivity.this);
				fromPhones.setTitle("请选择寄件人电话");
				fromPhones.setSingleChoiceItems(from_phones, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_fromUserPhone.setText(from_phones[which]);
						dialog.dismiss();
					}
				});
				fromPhones.show();
			}
		});
		
		from_addresses = Session.getCurrUserVO().getAddress().split(";");
		et_fromUserAddress.setText(from_addresses[0]);
		et_fromUserAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder fromAdd = new AlertDialog.Builder(createIndentActivity.this);
				fromAdd.setTitle("请选择寄件人地址");
				fromAdd.setSingleChoiceItems(from_addresses, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_fromUserAddress.setText(from_addresses[which]);
						dialog.dismiss();
					}
				});
				fromAdd.show();
			}
		});

		ToUserName.setText(friend.getName());
		
		to_phones =  Session.getCurrUserVO().getPhone().split(";");
		et_toUserPhone.setText(to_phones[0]);
		et_toUserPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder toPhones = new AlertDialog.Builder(createIndentActivity.this);
				toPhones.setTitle("请选择收件人电话");
				toPhones.setSingleChoiceItems(to_phones, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_toUserPhone.setText(to_phones[which]);
						dialog.dismiss();
					}
				});
				toPhones.show();
			}
		});
		
		to_addresses = Session.getCurrUserVO().getAddress().split(";");
		et_toUserAddress.setText(to_addresses[0]);
		et_toUserAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder toAdd = new AlertDialog.Builder(createIndentActivity.this);
				toAdd.setTitle("请选择寄件人地址");
				toAdd.setSingleChoiceItems(to_addresses, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_toUserAddress.setText(to_addresses[which]);
						dialog.dismiss();
					}
				});
				toAdd.show();
			}
		});
	
		create_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				createIndent?
//				fromuserid=xxx&touserid=xxx&
//				fromphone=xxx&tophone=xxx&
//				fromaddress=xxx&toaddress=xxx
//				&[price=xxx]
				RequestQueue rq = Volley.newRequestQueue(createIndentActivity.this);
				String URL = getResources().getString(R.string.url)+"createIndent?fromuserid="+Session.getCurrUserVO().getUserId()+"&touserid="+friend.getUserId()+
						"&fromphone="+et_fromUserPhone.getText()+"&tophone="+et_toUserPhone.getText()+
						"&fromaddress="+et_fromUserAddress.getText()+"&toaddress="+et_toUserAddress.getText();
				URL = URL.replaceAll(" ", "%20");
				Request<JSONObject> request_create = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							int code = response.getInt("code");//订单号
							String desc = response.getString("desc");
							if(code == -1){
								Toast.makeText(createIndentActivity.this, desc, Toast.LENGTH_LONG).show();
							}else{
								gotoDetailActivity(code);
							}
						} catch (JSONException e) {
							Toast.makeText(createIndentActivity.this, e.toString(), Toast.LENGTH_LONG).show();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(createIndentActivity.this, "操作出错："+error.toString(), Toast.LENGTH_LONG).show();
					}
				});
				rq.add(request_create);
			}
		});
	
		create_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private void gotoDetailActivity(int code){
		RequestQueue rq = Volley.newRequestQueue(createIndentActivity.this);
		String URL = getResources().getString(R.string.url)+"getIndent?id="+code;
		Request<JSONObject> request_getIndent = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				indentVo vo = newIndentVo(response);
				Intent intent = new Intent(createIndentActivity.this, indentDetail.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("indent", vo);
				intent.putExtras(bundle);
				startActivity(intent);	
				finish();
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(createIndentActivity.this, "操作出错："+error.toString(), Toast.LENGTH_LONG).show();
			}
		});	
		rq.add(request_getIndent);
	}
	private indentVo newIndentVo(JSONObject jo) {
		indentVo vo = new indentVo();
		try {
			vo.setId(jo.getString("id"));
			vo.setFromuserid(jo.getString("fromuserid"));
			vo.setFromphone(jo.getString("fromphone"));
			vo.setFromaddress(jo.getString("fromaddress"));
			vo.setTouserid(jo.getString("touserid"));
			vo.setTophone(jo.getString("tophone"));
			vo.setToaddress(jo.getString("toaddress"));
			vo.setPrice(jo.getString("price"));
			vo.setCourierid(jo.getString("courierid"));
			vo.setState(jo.getString("state"));
			vo.setTime(jo.getString("time"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return vo;
	}
}
