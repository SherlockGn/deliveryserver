package com.wsn.delivery.activity;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.wsn.delivery.Session;
import com.wsn.delivery.util.AlertDialogUtils;

public class ChangePhoneActivity extends Activity {
	
	LinearLayout ll_PhoneList;
	Button btn_addPhone;
	TextView tv_save;
	private float scale;
	
	String[] phones;
	Map<Integer, LinearLayout> itemMap = new TreeMap<Integer, LinearLayout>();
//	List<LinearLayout> itemList = new ArrayList<LinearLayout>();
	int item_count = 0;
	int cur_delete_id = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone);
		phones = getIntent().getStringExtra("phone").split(";");
		initView();
		init();
	}
	private void init() {
		scale = this.getResources().getDisplayMetrics().density;
		for (int i = 0; i < phones.length; i++) {
			item_count++;
			cur_delete_id++;
			LinearLayout ll = createPhoneItem(cur_delete_id,phones[i]);
			ll_PhoneList.addView(ll);
			itemMap.put(cur_delete_id, ll);//i+1:删除图片的id
		}
		btn_addPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				item_count++;
				cur_delete_id++;
				LinearLayout ll = createPhoneItem(cur_delete_id,"");
				ll_PhoneList.addView(ll);
				itemMap.put(cur_delete_id, ll);//i+1:删除图片的id
			}
		});
		tv_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String sendphone = "";
				for(Map.Entry<Integer, LinearLayout> map: itemMap.entrySet()){
					LinearLayout ll_content =(LinearLayout) map.getValue().getChildAt(0);
					EditText editText = (EditText) ll_content.getChildAt(1);
					if(!"".equals(editText.getText().toString())){
						sendphone = sendphone+editText.getText().toString()+";";
					}
					System.out.println(sendphone);
				}
				sendChangePhone(sendphone);
			}
		});
		
	}
	private void sendChangePhone(final String sendphone) {
		System.out.println("sendphone:"+sendphone);
		RequestQueue rq = Volley.newRequestQueue(ChangePhoneActivity.this);
//		setCourierid=xxx&username=xxx&[password=xxx]&[name=xxx]&[phone=xxx] 
		String URL = getResources().getString(R.string.url) + "setCourier?id=" + 
				Session.getCurrCourierVO().getUserId() + "&phone=" + sendphone;
		System.out.println(URL);
		Request<JSONObject> req_changePhone = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			private View result;
			String code,desc;
			@Override
			public void onResponse(JSONObject response) {
				try {
					code = response.getString("code");
					desc = response.getString("desc");
						result = AlertDialogUtils.alertTextDialog(ChangePhoneActivity.this, R.layout.common_dialog, "电话修改结果：", "确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								if(code != null && !"-1".equals(code)){
									Session.getCurrCourierVO().setPhone(sendphone);
									Intent resultIntent = new Intent();
									Bundle bundle =new Bundle();
									bundle.putString("phone", sendphone);
									resultIntent.putExtras(bundle);
									ChangePhoneActivity.this.setResult(RESULT_OK,resultIntent);
									ChangePhoneActivity.this.finish();
								}
							}
						});
						TextView tv = (TextView) result.findViewById(R.id.tv_content);
						if("the user doesn't exist".equals(desc)){
							tv.setText("该用户不存在");
						}if("reset success".equals(desc)){
							tv.setText("恭喜您：修改成功！");
						}else tv.setText(desc);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(ChangePhoneActivity.this, error.toString(), Toast.LENGTH_LONG).show();
			}
		});
		rq.add(req_changePhone);
		
		
	}
	private void initView() {
		btn_addPhone = (Button) findViewById(R.id.btn_add_newPhone);
		ll_PhoneList = (LinearLayout) findViewById(R.id.ll_PhoneList);
		tv_save = (TextView) findViewById(R.id.tv_savePhone);
	}
	
	public LinearLayout createPhoneItem(final int delete_id,String phone){
		System.out.println(delete_id+"=================================");
		final int i =delete_id;
		LinearLayout ll_item = new LinearLayout(ChangePhoneActivity.this);
		LayoutParams item_params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ll_item.setLayoutParams(item_params);
		ll_item.setBackgroundColor(Color.WHITE);
		ll_item.setOrientation(LinearLayout.VERTICAL);
		//电话LinearLayout
		LinearLayout ll_content = new LinearLayout(ChangePhoneActivity.this);
		LayoutParams content_params = new LayoutParams(LayoutParams.FILL_PARENT, (int)(50*scale+0.5f));
		ll_content.setLayoutParams(content_params);
		ll_content.setOrientation(LinearLayout.HORIZONTAL);
		//电话LinearLayout中的子控件
		TextView tv = new TextView(ChangePhoneActivity.this);
		LayoutParams tv_params = new LayoutParams(LayoutParams.WRAP_CONTENT, (int)(50*scale+0.5f));
		tv_params.setMargins((int)(5*scale+0.5f), 0, 0, 0);
		tv.setLayoutParams(tv_params);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setText("Phone"+delete_id+":");
		tv.setTextColor(getResources().getColor(R.color.color_black));
		tv.setTextSize(18);
		EditText et = new EditText(ChangePhoneActivity.this);
		LayoutParams et_params = new LayoutParams(LayoutParams.FILL_PARENT, (int)(50*scale+0.5f));
		et.setLayoutParams(et_params);
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setText(phone);
		ll_content.addView(tv);
		ll_content.addView(et);
		//分割线
		View v = new View(ChangePhoneActivity.this);
		LayoutParams v_params = new LayoutParams(LayoutParams.FILL_PARENT, (int)(1*scale+0.5f));
		v.setLayoutParams(v_params);
		v.setBackgroundColor(getResources().getColor(R.color.light_gray));
		//删除RelativeLayout
		RelativeLayout re_delete = new RelativeLayout(ChangePhoneActivity.this);
		LayoutParams delete_params = new LayoutParams(LayoutParams.FILL_PARENT, (int)(30*scale+0.5f));
		re_delete.setLayoutParams(delete_params);
		//删除RelativeLayout中的子控件
		TextView tv_delete = new TextView(ChangePhoneActivity.this);
		RelativeLayout.LayoutParams tv_delete_params = new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.WRAP_CONTENT, (int)(30*scale+0.5f));
		tv_delete_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv_delete_params.setMargins(0, 0, (int)(5*scale+0.5f), 0);
		tv_delete.setGravity(Gravity.CENTER_VERTICAL);
		tv_delete.setText("删除");
		tv_delete.setId(delete_id);
		re_delete.addView(tv_delete, tv_delete_params);
		
		ImageView iv_delete = new ImageView(ChangePhoneActivity.this);
		RelativeLayout.LayoutParams iv_delete_params = new RelativeLayout.LayoutParams
				((int)(20*scale+0.5f), (int)(20*scale+0.5f));
		iv_delete_params.addRule(RelativeLayout.LEFT_OF, tv_delete.getId());
		iv_delete_params.setMargins(0, (int)(5*scale+0.5f), 0, 0);
		iv_delete.setScaleType(ScaleType.FIT_XY);
		iv_delete.setBackgroundResource(R.drawable.sys_delete);
		iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("绑定监听器"+i);
				LinearLayout linearLayout = itemMap.get(i);
				ll_PhoneList.removeView(linearLayout);
				itemMap.remove(delete_id);
				int index = 1;
				for(Map.Entry<Integer, LinearLayout> map: itemMap.entrySet()){
					LinearLayout ll_content =(LinearLayout) map.getValue().getChildAt(0);
					TextView textView = (TextView) ll_content.getChildAt(0);
					textView.setText("Phone"+index+++":");
				}
			}
		});
		re_delete.addView(iv_delete, iv_delete_params);
		
		
		View v1 = new View(ChangePhoneActivity.this);
		LayoutParams v1_params = new LayoutParams(LayoutParams.FILL_PARENT, (int)(10*scale+0.5f));
		v1.setLayoutParams(v1_params);
		v1.setBackgroundColor(getResources().getColor(R.color.light_gray));
		
		ll_item.addView(ll_content);
		ll_item.addView(v);
		ll_item.addView(re_delete);
		ll_item.addView(v1);
		return ll_item;
	}
}
