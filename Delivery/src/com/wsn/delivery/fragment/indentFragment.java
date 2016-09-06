package com.wsn.delivery.fragment;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnChildClickListener;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.Response.Listener;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.wsn.courier.R;
//import com.wsn.courier.Session;
//import com.wsn.courier.activity.indentDetail;
//import com.wsn.courier.adapter.IndentExpandableListAdapter;
//import com.wsn.courier.indent.indentVo;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wsn.delivery.R;
import com.wsn.delivery.Session;
import com.wsn.delivery.activity.indentDetail;
import com.wsn.delivery.adapter.IndentExpandableListAdapter;
import com.wsn.delivery.indent.indentVo;

public class indentFragment extends Fragment {
//	String url = "http://10.128.19.233:8080/delivery/";
	List<List<indentVo>> data = new ArrayList<List<indentVo>>();
	List<indentVo> UnreceiveList = null;
//	List<indentVo> receivedList = null;
	boolean UnreceivedProgress = false;
	Handler handler;
	IndentExpandableListAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("ÔËÐÐindentFragmentµÄonCreateView+++++++++++++++++++++++++++++++++++");
		super.onCreateView(inflater, container, savedInstanceState);
		View indentView = inflater.inflate(R.layout.fragment_indent, container,false);
		getData();
		ExpandableListView ExList = (ExpandableListView) indentView.findViewById(R.id.exList_Indent);
		
		adapter = new IndentExpandableListAdapter(getActivity(), data);
		ExList.setAdapter(adapter);
		ExList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println(groupPosition+" <:> "+ childPosition);
				Intent i = new Intent(getActivity(), indentDetail.class);
				i.putExtra("id",data.get(groupPosition).get(childPosition).getId());
				Bundle bundle = new Bundle();
				bundle.putSerializable("indent", data.get(groupPosition).get(childPosition));
				i.putExtras(bundle);
				startActivity(i);
				
				return true;
			}
		});
		return indentView;
	}

	private void getData() {
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				System.out.println(msg.what);
				switch (msg.what) {
				case 1:
//					sendedProgress = true;
//					System.out.println("result:"+sendedProgress+":"+receivedProgress);
//					if(receivedProgress){
//						data.clear();
////						data.add(sendedList);
//						data.add(UnreceiveList);
//						adapter.setData(data);
//						adapter.notifyDataSetChanged();
//						System.out.println("========================");
//					}
					break;
				case 2:
					UnreceivedProgress = true;
					if(UnreceivedProgress){
						data.clear();
//						data.add(sendedList);
						data.add(UnreceiveList);
						adapter.setData(data);
						adapter.notifyDataSetChanged();
						System.out.println("========================");
					}
					break;
				default:
					break;
				}
			}
		};
		RequestQueue rq = Volley.newRequestQueue(getActivity());
//		String URL1 = getResources().getString(R.string.url)+"getSendedIndent?id="+Session.getCurrCourierVO().getUserId(); 
//		Request<JSONArray> request_sended = new JsonArrayRequest(URL1, new Listener<JSONArray>() {
//			@Override
//			public void onResponse(JSONArray response) {
//				addSendedData(response);
//				System.out.println("sendedData:"+sendedList.size()+">>>>");
//				Message msg = new Message();
//			    msg.what = 1;
//			    handler.sendMessage(msg);
//			}
//		}, new ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				
//			}
//		});
		String URL2 = getResources().getString(R.string.url)+"remainingIndent?id="+Session.getCurrCourierVO().getUserId(); 
		Request<JSONArray> request_Unreceived = new JsonArrayRequest(URL2, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				addUnreceivedData(response);
				System.out.println("UnreceivedData:"+UnreceiveList.size()+">>>>");
				Message msg = new Message();
			    msg.what = 2;
			    handler.sendMessage(msg);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		});
//		rq.add(request_sended);
		rq.add(request_Unreceived);
	}

	private void addUnreceivedData(JSONArray response) {
		UnreceiveList = new ArrayList<indentVo>();
		if(response.length() == 1 ){
			try {
				JSONObject jo = (JSONObject) response.get(0);
				if("-1".equals(jo.getString("id"))){
					System.out.println(jo.getString("address"));
				}else{
					indentVo vo = newIndentVo(jo);
					UnreceiveList.add(vo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			for (int i = 0; i < response.length(); i++) {
				try {
					JSONObject jo = (JSONObject) response.get(i);
					indentVo vo = newIndentVo(jo);
					UnreceiveList.add(vo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}		
	}

//	private void addSendedData(JSONArray response) {
//		sendedList = new ArrayList<indentVo>();
//		if(response.length() == 1 ){
//			try {
//				JSONObject jo = (JSONObject) response.get(0);
//				if("-1".equals(jo.getString("id"))){
//					System.out.println(jo.getString("address"));
//				}else{
//					indentVo vo = newIndentVo(jo);
//					sendedList.add(vo);
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}else{
//			for (int i = 0; i < response.length(); i++) {
//				try {
//					JSONObject jo = (JSONObject) response.get(i);
//					indentVo vo = newIndentVo(jo);
//					sendedList.add(vo);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

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

