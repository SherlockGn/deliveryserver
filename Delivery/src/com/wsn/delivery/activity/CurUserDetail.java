package com.wsn.delivery.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.wsn.delivery.R;
import com.wsn.delivery.Session;
import com.wsn.delivery.courier.CourierVo;

public class CurUserDetail extends Activity {
	LinearLayout ll_cur_phone;//点击
	LinearLayout ll_user_phone;//存放电话
	TextView tv_cur_name,tv_cur_login,tv_cur_phone;
	private float scale;
	CourierVo vo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cur_user_detail);
		initView();
		init();
	}

	private void init() {
		scale = this.getResources().getDisplayMetrics().density;
		vo = Session.getCurrCourierVO();
		tv_cur_name.setText(vo.getName());
		tv_cur_login.setText(vo.getLoginName());
		setPhone();
//		setAddress();
//		ll_cur_gender.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				final String[] gender = {"男","女"};
//				AlertDialog.Builder selectGender = new AlertDialog.Builder(CurUserDetail.this);
//				selectGender.setTitle("请选择性别");
//				selectGender.setSingleChoiceItems(gender, 0, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						String oldGender = vo.getGender();
//						tv_cur_gender.setText(gender[which]);
//						if("男".equals(gender[which])){
//							vo.setGender("true");
//						}else if("女".equals(gender[which])){
//							vo.setGender("false");
//						}
//						dialog.dismiss();
//						if(!oldGender.equalsIgnoreCase(vo.getGender())){
//							changeGender();
//						}
//					}
//				});
//				selectGender.show();
//			}
//		});
		ll_cur_phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(CurUserDetail.this, ChangePhoneActivity.class);
				i.putExtra("phone", vo.getPhone());
				startActivityForResult(i, 0);
				System.out.println("跳到新的activity设置电话");
			}
		});
//		ll_cur_address.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(CurUserDetail.this, ChangeAddressActivity.class);
//				i.putExtra("address", vo.getAddress());
//				startActivityForResult(i, 1);
//				System.out.println("跳到新的activity设置地址");
//				
//			}
//		});
	}

//	private void changeGender() {
//		//更改用户性别
//		RequestQueue rq = Volley.newRequestQueue(CurUserDetail.this);
////		setUser?id=xxx&username=xxx&[password=xxx]&[gender=xxx]&[name=xxx]&[phone=xxx]&[address=xxx] 
//		String URL = getResources().getString(R.string.url) + "setUser?id=" + vo.getUserId()+"&gender="+ vo.getGender();
//		Request<JSONObject> req_changeGender = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
//			String code,desc;
//			@Override
//			public void onResponse(JSONObject response) {
//				try {
//					code = response.getString("code");
//					desc = response.getString("desc");
//					System.out.println(code+": "+desc);
//					if("-1".equals(code)){
//						Toast.makeText(CurUserDetail.this, "更改失败:"+desc, Toast.LENGTH_LONG).show();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}, new ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				Toast.makeText(CurUserDetail.this, error.toString(), Toast.LENGTH_LONG).show();
//			}
//		});
//		rq.add(req_changeGender);
//	}

//	private void setAddress() {
//		String[] address = vo.getAddress().split(";");
//		if(address.length == 1){
//			tv_cur_address.setText(address[0]);
//		}else if(address.length > 1){
//			tv_cur_address.setText(address[0]);
//			for (int i = 1; i < address.length; i++) {
//				View view = new View(this);
//				LayoutParams p1 = new LayoutParams(LayoutParams.FILL_PARENT, 1);
//				view.setLayoutParams(p1);
//				view.setBackgroundColor(Color.GRAY);
//				ll_user_address.addView(view);
//				TextView tv = new TextView(this);
//				System.out.println(scale);
//				LayoutParams p2 = new LayoutParams(LayoutParams.FILL_PARENT, (int)(48*scale+0.5f));
//				tv.setLayoutParams(p2);
//				tv.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
//				tv.setText(address[i]);
//				ll_user_address.addView(tv);
//			}
//		}
//	}

	private void setPhone() {
		String[] phones = vo.getPhone().split(";");
		if(phones.length == 1){
			tv_cur_phone.setText(phones[0]);
		}else if(phones.length > 1){
			tv_cur_phone.setText(phones[0]);
			for (int i = 1; i < phones.length; i++) {
				View view = new View(this);
				LayoutParams p1 = new LayoutParams(LayoutParams.FILL_PARENT, 1);
				view.setLayoutParams(p1);
				view.setBackgroundColor(Color.GRAY);
				ll_user_phone.addView(view);
				TextView tv = new TextView(this);
				System.out.println(scale);
				LayoutParams p2 = new LayoutParams(LayoutParams.FILL_PARENT, (int)(48*scale+0.5f));
				tv.setLayoutParams(p2);
				tv.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
				tv.setText(phones[i]);
				ll_user_phone.addView(tv);
			}
		}
	}

	private void initView() {
//		ll_cur_gender= (LinearLayout) findViewById(R.id.ll_cur_login_gender);
		ll_cur_phone = (LinearLayout) findViewById(R.id.ll_cur_user_phone);
//		ll_cur_address = (LinearLayout) findViewById(R.id.ll_cur_user_address);
		ll_user_phone= (LinearLayout) findViewById(R.id.ll_user_phone);
//		ll_user_address = (LinearLayout) findViewById(R.id.ll_user_address);
		tv_cur_name = (TextView)findViewById(R.id.tv_cur_name);
		tv_cur_login = (TextView)findViewById(R.id.tv_cur_login);
//		tv_cur_gender = (TextView)findViewById(R.id.tv_cur_gender);
		tv_cur_phone = (TextView)findViewById(R.id.tv_cur_phone);
//		tv_cur_address = (TextView)findViewById(R.id.tv_cur_address);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == RESULT_OK){
				System.out.println("changePhone=================");
				Bundle b = data.getExtras();
				String phone = b.getString("phone");
				System.out.println(phone);
				vo.setPhone(phone);
				int count = ll_user_phone.getChildCount();
				for (int i = 1; i < count; i++) {
					ll_user_phone.removeViewAt(1);
				}
				setPhone();
			}
		}
//		if(requestCode == 1){
//			if(resultCode == RESULT_OK){
//				System.out.println("changeAddress=================");
//				Bundle b = data.getExtras();
//				String address = b.getString("address");
//				System.out.println("address: "+address);
//				vo.setAddress(address);
//				int count = ll_user_address.getChildCount();
//				System.out.println("count1: "+count);
//				for (int i = 1; i < count; i++) {
//					ll_user_address.removeViewAt(1);
//				}
//				System.out.println(vo.getAddress());
//				System.out.println("count2: "+ll_user_address.getChildCount());
//				setAddress();
////				setPhone();
//			}
//		}
	}

}
