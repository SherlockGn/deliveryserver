package com.wsn.courier.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wsn.courier.R;
import com.wsn.courier.Session;
import com.wsn.courier.activity.createIndentActivity;
import com.wsn.courier.user.userVo;
import com.wsn.courier.util.AlertDialogUtils;

public class friendFragment extends Fragment {
	List<userVo> friends;
//	private String url = "http://10.128.19.233:8080/delivery/";//服务器地址
//	RequestQueue rq = Volley.newRequestQueue(this.getActivity());
	friendAdapter fa;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View friendView = inflater.inflate(R.layout.fragment_friend, container,false);
		ListView list = (ListView) friendView.findViewById(R.id.lv_friend);
		getFriendData();
		fa = new friendAdapter(getActivity(),friends);
		list.setAdapter(fa);
		
		ImageView iv = (ImageView)friendView.findViewById(R.id.add_friend_iv);
		iv.setOnClickListener(new OnClickListener() {
			
			private EditText friend_et;
			private View find;

			@Override
			public void onClick(View v) {
				find = AlertDialogUtils.alertTextDialog(getActivity(), R.layout.find_friend, 
						"查找好友", "添加", new DialogInterface.OnClickListener() {
					private String friend_username;

					@Override
					public void onClick(DialogInterface dialog, int which) {
						friend_username = friend_et.getText().toString();
						if(friend_username == null ||"".equals(friend_username)){
							Toast.makeText(getActivity(), "账号不能为空！", Toast.LENGTH_SHORT).show();
						}else{
							//调用接口进行添加
							Dialog currentDialog = (Dialog) find.getTag();
//						addFriend?id1=xxx&username1=xxx&id2=xxx&username2=xxx 
//							RequestQueue req = Volley.newRequestQueue(currentDialog.getOwnerActivity());
							RequestQueue rq = Volley.newRequestQueue(getActivity());
							String URL = getResources().getString(R.string.url)+"addFriend?username1="+Session.getCurrUserVO().getLoginName()
									+"&username2="+friend_username;
							Request<JSONObject> request_addFriend = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									try {
										int code = response.getInt("code");
										String desc = response.getString("desc");
										if(code == 1){//成功
//											friends.clear();
											getFriendData();
//											fa.data = friends;
//											System.out.println("friends: "+friends.size());
//											fa.notifyDataSetChanged();
											Toast.makeText(getActivity(), desc, Toast.LENGTH_SHORT).show();
										}else if(code == -1){
											if(desc.contains("user1")){
												desc.replace("user1", Session.getCurrUserVO().getLoginName());
											}
											if(desc.contains("user2")){
												desc.replace("user2", friend_username);
											}
											Toast.makeText(getActivity(), desc, Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										Toast.makeText(getActivity(), "操作出错，请检查网络等！", Toast.LENGTH_SHORT).show();
									}
								}
							}, new ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error) {
									
								}
							});
							rq.add(request_addFriend);
							currentDialog.dismiss();
						}
					}
				});
				friend_et = (EditText) find.findViewById(R.id.friend_tv);
			}
		});
		return friendView;
	}
	
	private void getFriendData() {
		//网络获取
		RequestQueue rq = Volley.newRequestQueue(this.getActivity());
		String URL = getResources().getString(R.string.url)+"getFriend?username="+Session.getCurrUserVO().getLoginName();
		System.out.println("获取所有好友");
		Request<JSONArray> request_friends = new JsonArrayRequest(URL, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				friends.clear();
				for (int i = 0; i < response.length(); i++) {
					try {
						JSONObject jsonObject = response.getJSONObject(i);
						String s = jsonObject.getString("id");
						if(s != "-1"){
							userVo friend  = new userVo();
							friend.setUserId(jsonObject.getString("id"));
							friend.setGender(jsonObject.getString("gender"));
							friend.setName(jsonObject.getString("name"));
							friend.setLoginName(jsonObject.getString("username"));
							friend.setPhone(jsonObject.getString("phone"));
							friend.setAddress(jsonObject.getString("address"));
							friends.add(friend);
//							System.out.println(friend.getUserId());
//							System.out.println(friend.getGender());
//							System.out.println(friend.getName());
//							System.out.println(friend.getLoginName());
//							System.out.println(friend.getPhone());
//							System.out.println(friend.getAddress());
						}else{
							System.out.println(jsonObject.getString("name"));
							Toast.makeText(getActivity(), "error:"+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if(fa != null){
					fa.data = friends;
					fa.notifyDataSetChanged();
				}
				System.out.println("获取所有好友，总共: "+friends.size());
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(), "error:"+error, Toast.LENGTH_SHORT).show();
			}
		});
		rq.add(request_friends);
//===================================================================		
		if(friends == null) {
			friends = new ArrayList<userVo>();
			userVo v1 = new userVo();
			v1.setLoginName("小玉鸡");
			v1.setName("秦宇翔");
			friends.add(v1);
			userVo v2 = new userVo();
			v2.setLoginName("rose");
			v2.setName("张素蓉");
			friends.add(v2);
		}
//===================================================================		
	}
	
	class friendAdapter extends BaseAdapter{
		private Context context;
		private List<userVo> data;
		private LayoutInflater Inflater = null;
		public friendAdapter(Context context, List<userVo> data) {
			this.context = context;
			Inflater = Inflater.from(context);
			this.data = data;
		}
		
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = Inflater.inflate(R.layout.friend_list_item, null);
				holder.tv = (TextView) convertView.findViewById(R.id.friend_list_item_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final userVo userinfo = data.get(position);
			holder.tv.setText(userinfo.getName()+"("+userinfo.getLoginName()+")");
			holder.tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					View makeCourierDialog = AlertDialogUtils.alertTextDialog(context, 
							R.layout.common_dialog, "提示", "确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent intent = new Intent(context, createIndentActivity.class);
									Bundle bundle = new Bundle();
									bundle.putSerializable("friend", userinfo);
									intent.putExtras(bundle);
									dialog.dismiss();
									startActivity(intent);
								}
							});
					TextView tv_content = (TextView)makeCourierDialog.findViewById(R.id.tv_content);
					tv_content.setText("确定向“"+userinfo.getName()+"”寄送快递?");
				}
			});
			return convertView;
		}
		
	}
	
	class ViewHolder{
		TextView tv;
	}
}
