package com.wsn.delivery.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
import com.wsn.delivery.util.AlertDialogUtils;

public class RegisterActivity extends Activity {
	LinearLayout ll_addPhone,ll_addAddress;
	EditText et_account,et_password,et_secondPassword,et_name,et_gender,et_phone,et_address;
	ImageView iv_add$phone,iv_add$address;
	Button bt_register;
	List<EditText> PhoneList = new ArrayList<EditText>();
	List<EditText> AddressList = new ArrayList<EditText>();
	private float scale;
	String account = "", passWord = "", secondPassWord = "", 
			name = "", gender = "", phone = "", address = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		init();
	}
	private void init() {
		scale = this.getResources().getDisplayMetrics().density;
		PhoneList.add(et_phone);
		AddressList.add(et_address);
		addPhone();
		addAddress();
		setGender();
		Register();
	}
	private void Register() {
		bt_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(judge()){
					SendForRegister();
				}else{
					return;
				}
			}
		});
		
		
	}
	protected void SendForRegister() {
		RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
		String URL = getResources().getString(R.string.url) + "registerUser?username=" + account 
				+ "&password=" + passWord + "&gender="+ gender +"&name=" + name 
				+ "&phone=" + phone + "&address=" + address;
		Request<JSONObject> req_register = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			private View result;
			String code,desc;
			@Override
			public void onResponse(JSONObject response) {
				try {
					code = response.getString("code");
					desc = response.getString("desc");
						result = AlertDialogUtils.alertTextDialog(RegisterActivity.this, R.layout.common_dialog, "注册结果�?", "确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								if(code != null && !"-1".equals(code)){
									Intent resultIntent = new Intent();
									Bundle bundle =new Bundle();
//									bundle .putString("code", code);
//									bundle.putString("desc", desc);
									bundle.putString("username", account);
									bundle.putString("password", passWord);
									resultIntent.putExtras(bundle);
									RegisterActivity.this.setResult(RESULT_OK,resultIntent);
									RegisterActivity.this.finish();
								}
							}
						});
						TextView tv = (TextView) result.findViewById(R.id.tv_content);
						if("this username has exsisted".equals(desc)){
							tv.setText("该用户已存在");
						}if("register success".equals(desc)){
							tv.setText("恭喜您：注册成功�?");
						}else tv.setText(desc);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		rq.add(req_register);
	}
	private boolean judge() {
		account = et_account.getText().toString().trim();
		if("".equals(account)){
			Toast.makeText(RegisterActivity.this, "账号不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}
		passWord = et_password.getText().toString().trim();
		if("".equals(passWord)){
			Toast.makeText(RegisterActivity.this, "密码不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}
		secondPassWord = et_secondPassword.getText().toString().trim();
		if("".equals(secondPassWord)){
			Toast.makeText(RegisterActivity.this, "确认密码不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}else if(!passWord.equals(secondPassWord)){
			Toast.makeText(RegisterActivity.this, "确认密码与密码不统一", Toast.LENGTH_LONG).show();
			return false;
		}
		name = et_name.getText().toString().trim();
		if("".equals(name)){
			Toast.makeText(RegisterActivity.this, "姓名不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}
		gender = et_gender.getText().toString().trim();
		if("".equals(gender)){
			Toast.makeText(RegisterActivity.this, "性别不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}else if("�?".equals(gender)){
			gender = "true";
		}else if("�?".equals(gender)){
			gender = "false";
		}
		
		System.out.println("未变前phone�?"+phone+"<    >"+PhoneList.size());
		phone = "";
		System.out.println(phone);
		for (int i = 0; i < PhoneList.size(); i++) {
			String currentPhone = PhoneList.get(i).getText().toString().trim();
			System.out.println("currentPhone:"+currentPhone);
			if(currentPhone != null && !"".equals(currentPhone)){
				phone = phone+currentPhone+";";
			}
			System.out.println(i+": "+phone);
		}
		if(phone.contains(";")){
			phone = phone.substring(0, phone.lastIndexOf(";"));
		}
		System.out.println("变后phone�?"+phone);
		if("".equals(phone)){
			Toast.makeText(RegisterActivity.this, "电话不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}
		
		System.out.println("未变前address�?"+address+"<    >"+AddressList.size());
		address = "";
		for (int i = 0; i < AddressList.size(); i++) {
			String currentAddress = AddressList.get(i).getText().toString().trim();
			if(currentAddress != null && !"".equals(currentAddress)){
				address = address+currentAddress+";";
			}
		}
		if(address.contains(";")){
			address = address.substring(0, address.lastIndexOf(";"));
		}
		System.out.println("变后address�?"+address);
		if("".equals(address)){
			Toast.makeText(RegisterActivity.this, "地址不可以为�?", Toast.LENGTH_LONG).show();
			return false;
		}	
		return true;
	}

	private void setGender() {
		et_gender.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] gender = {"�?","�?"};
				AlertDialog.Builder selectGender = new AlertDialog.Builder(RegisterActivity.this);
				selectGender.setTitle("请�?�择性别");
				selectGender.setSingleChoiceItems(gender, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						et_gender.setText(gender[which]);
						dialog.dismiss();
					}
				});
				selectGender.show();
			}
		});
	}

	private void addAddress() {
		iv_add$address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText newAddress = new EditText(RegisterActivity.this);
				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, (int)(40*scale+0.5f));
				newAddress.setLayoutParams(lp);
				newAddress.setHint("请输入地�?");
				ll_addAddress.addView(newAddress);
				AddressList.add(newAddress);
			}
		});
	}

	private void addPhone() {
		iv_add$phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText newPhone = new EditText(RegisterActivity.this);
				LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, (int)(40*scale+0.5f));
				newPhone.setLayoutParams(lp);
				newPhone.setHint("请输入电话号�?");
				newPhone.setInputType(InputType.TYPE_CLASS_PHONE);
				ll_addPhone.addView(newPhone);
				PhoneList.add(newPhone);
			}
		});
		
	}

	private void initView() {
		ll_addPhone = (LinearLayout) findViewById(R.id.ll_register$add_phone);
		ll_addAddress = (LinearLayout) findViewById(R.id.ll_register$add_address);
		et_account = (EditText) findViewById(R.id.et_register$username);
		et_password = (EditText) findViewById(R.id.et_register$password);
		et_secondPassword = (EditText) findViewById(R.id.et_register$second_password);
		et_name = (EditText) findViewById(R.id.et_register$name);
		et_gender = (EditText) findViewById(R.id.et_register$gender);
		et_phone = (EditText) findViewById(R.id.et_register$phone1);
		et_address = (EditText) findViewById(R.id.et_register$address);
		iv_add$phone = (ImageView) findViewById(R.id.iv_register$add_phone);
		iv_add$address = (ImageView) findViewById(R.id.iv_register$add_address);
		bt_register = (Button) findViewById(R.id.bt_register);
	}
}
