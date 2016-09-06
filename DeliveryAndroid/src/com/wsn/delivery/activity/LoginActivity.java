package com.wsn.delivery.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.wsn.delivery.R.color;
import com.wsn.delivery.Session;
import com.wsn.delivery.courier.CourierVo;
import com.wsn.delivery.db.DBHelper;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText et_loginName = null;
	private EditText et_password = null;
	private TextView tv_addNewAccount;
	
	private ImageButton selectBnt = null;
	private PopupWindow popView = null;
	private CheckBox cb_isSaved = null;
	private Button btn_login = null;
	private DBHelper dbHelper;
	private UsernamePopAdapter usernameAdapter;
	private List<CourierVo> userList;
	ProgressDialog mLoding = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		setListener();
		initLoginUserName();
	}

	private void setListener() {
		btn_login.setOnClickListener(this);
		selectBnt.setOnClickListener(this);
		tv_addNewAccount.setOnClickListener(this);
	}

	private void initView() {
		btn_login = (Button) findViewById(R.id.login$btn_submit);
	    selectBnt = (ImageButton) findViewById(R.id.login$imgbtn_selectUser);
	    et_loginName = (EditText) findViewById(R.id.login$et_loginName);
	    et_password = (EditText) findViewById(R.id.login$et_passWord);
	    cb_isSaved = (CheckBox)findViewById(R.id.isSaved);
	    tv_addNewAccount = (TextView) findViewById(R.id.tv_register);
	}

	private void initLoginUserName(){
		dbHelper = new DBHelper(this);
		userList = dbHelper.queryAllUser();
		if(userList != null && userList.size() > 0){
			CourierVo tempVo = userList.get(userList.size()-1);
			String tempName = tempVo.getLoginName();
			int checkFlag = tempVo.getIssaved();
			String tempPwd = tempVo.getPassword();
			et_loginName.setText(tempName);
			if (checkFlag == 0) {
				cb_isSaved.setChecked(false);
			} else if (checkFlag == 1) {
				cb_isSaved.setChecked(true);
			}
			et_password.setText(tempPwd);
		}
		

		et_loginName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				et_password.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.login$btn_submit:
				mLoding = new ProgressDialog(LoginActivity.this);
//				mLoding.setProgressDrawable(getResources().getDrawable(R.drawable.dialog_loading));
				mLoding.setMessage("正在登陆中...");
				mLoding.show();
				String username = et_loginName.getText().toString();
				String password = et_password.getText().toString();
				login(username,password);
				break;
			case R.id.tv_register:
				Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivityForResult(i, 0);
				break;
			case R.id.login$imgbtn_selectUser:
		      if ((LoginActivity.this.userList == null) || 
		    		  (LoginActivity.this.userList.isEmpty())) {
		    	  Toast.makeText(this, "无记录", Toast.LENGTH_LONG).show();
		    	  return;
		      }
		      if (popView != null) {
		    	  System.out.println("不是第一次点击");
		    	  //不是第一次点击，popView对象已经存在
		    	  if (!popView.isShowing()) {
		    		  popView.showAsDropDown(et_loginName);
		    	  } else {
						popView.dismiss();
				  }
				} else {
					// 第一次点击
					System.out.println("是第一次点击");
					initPopView();
					if (!popView.isShowing()) {
						popView.showAsDropDown(et_loginName);
					} else {
						popView.dismiss();
					}
				}
			break;
		}		
	}

	private void login(final String username, final String password) {
		
		RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
		String URL = getResources().getString(R.string.url)+"loginCourier?username="+username+"&password="+password;
		System.out.println(URL);
		Request<JSONObject> req_login = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					String desc = response.getString("desc");
					System.out.println("desc: " + desc);
					if (desc.equals("login success")) {
						CourierVo vo = new CourierVo();
						vo.setLoginName(username);
						if (cb_isSaved.isChecked()) {
							vo.setPassword(password);
							vo.setIssaved(1);
						} else {
							vo.setPassword("");
							vo.setIssaved(0);
						}
						dbHelper.insertOrUpdate(vo);
						gotoMainActivity(username);
					}else{
						Toast.makeText(LoginActivity.this, "账号密码有误", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					if(mLoding != null) mLoding.dismiss();
					e.printStackTrace();
				}
			}

		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if(mLoding != null){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("mLoding: "+error);
					mLoding.dismiss();
					System.out.println("ss");
				}
				System.out.println(error);
				Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		});
		rq.add(req_login);
	}

	protected void gotoMainActivity(String username) {
		RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
		String URL = getResources().getString(R.string.url)+"getCourier?username="+username;
		Request<JSONObject> req_loginCourier = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				String id;
				try {
					id = response.getString("id");
					System.out.println("获取快递员");
					if(id != null && !"-1".equals(id)){
						CourierVo currentCourierVo  = new CourierVo();
						currentCourierVo.setUserId(id);
						currentCourierVo.setPassword(response.getString("password"));
						currentCourierVo.setName(response.getString("name"));
						currentCourierVo.setLoginName(response.getString("username"));
						currentCourierVo.setPhone(response.getString("phone"));
						Session.setCurrCourierVO(currentCourierVo);
						Intent intent = new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
						if(mLoding != null) mLoding.dismiss();
					}else{
						if(mLoding != null) mLoding.dismiss();
						Toast.makeText(LoginActivity.this, "登录失败"+response.getString("name"), Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if(mLoding != null) mLoding.dismiss();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(mLoding != null) mLoding.dismiss();
			}
		});
		rq.add(req_loginCourier);
	}

	private void initPopView() {
		usernameAdapter = new UsernamePopAdapter(LoginActivity.this, userList);
		ListView listView = new ListView(this);
		listView.setAdapter(usernameAdapter);
		popView = new PopupWindow(listView, et_loginName.getWidth(),
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		popView.setFocusable(true);
		popView.setOutsideTouchable(true);
		popView.setBackgroundDrawable(getResources().getDrawable(R.color.white));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == Activity.RESULT_OK){
				Bundle b = data.getExtras();
//				String code = b.getString("code");
//				String desc = b.getString("desc");
				et_loginName.setText(b.getString("username"));
				et_password.setText(b.getString("password"));
			}
		}
	}

	class UsernamePopAdapter extends BaseAdapter implements OnClickListener{

		private List<CourierVo> data;
		private LayoutInflater Inflater = null;

		public UsernamePopAdapter(Context context, List<CourierVo> data) {
			this.Inflater = LayoutInflater.from(context);
			this.data = data;
			for (int i = 0; i < data.size(); i++) {
				System.out.println("uservo:"+data.get(i).getLoginName());
			}
		}
		

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public  CourierVo getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = Inflater.inflate(R.layout.login_popview_item, null);
				holder.textview = (TextView) convertView.findViewById(R.id.username_pop_textview);
				holder.button = (ImageButton) convertView.findViewById(R.id.username_pop_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			CourierVo userinfo = data.get(position);
			System.out.println("item:"+userinfo.getLoginName());
			holder.textview.setText(userinfo.getLoginName());
			holder.textview.setTextColor(color.blue);
			holder.button.setBackgroundResource(R.drawable.xicon);
			holder.button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					System.out.println("删除");
					if(data != null && data.size() > 0){
						dbHelper.delete(data.get(position));
						userList.remove(data.get(position));
						notifyDataSetChanged();
					}
				}
			});
			holder.textview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CourierVo user = data.get(position);
					et_loginName.setText(user.getLoginName());
					if(user.getIssaved() == 1){
						cb_isSaved.setChecked(true);
						et_password.setText(user.getPassword());
					}else{
						cb_isSaved.setChecked(false);
						et_password.setText("");
					}
					popView.dismiss();
				}
			});
			return convertView;
		}
		
		class ViewHolder {
			private TextView textview;
			private ImageButton button;
		}

		@Override
		public void onClick(View v) {
			
		}
	}

}

