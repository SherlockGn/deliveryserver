package com.wsn.delivery.fragment;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wsn.delivery.R;
import com.wsn.delivery.Session;
import com.wsn.delivery.activity.CaptureActivity;
import com.wsn.delivery.activity.CurUserDetail;

public class mineFragment extends Fragment {
	View Scan_result_Dialog;
	LinearLayout ll_mine,ll_scan,ll_quit,ll_sms;
	View mineView;
	TextView tv_mine;
	public static String Pref_SmsMessageInfo = "SmsMessageInfo.pref";
	
	public static String msgContent = "尊敬的客户，收到短信请尽快收取您的快递。";
	SharedPreferences SmsSp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mineView = inflater.inflate(R.layout.fragment_mine, container,false);
		initView();
		init();
		return mineView;
	}
	
	private void initView() {
		tv_mine = (TextView) mineView.findViewById(R.id.tv_mine);
		ll_mine = (LinearLayout) mineView.findViewById(R.id.ll_mine);
		ll_scan = (LinearLayout) mineView.findViewById(R.id.ll_scan);
		ll_sms = (LinearLayout)mineView.findViewById(R.id.ll_sms);
		ll_quit = (LinearLayout) mineView.findViewById(R.id.ll_quit);
	}
	
	private void init() {
		tv_mine.setText(Session.getCurrCourierVO().getName());
		ll_mine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show_mine();
			}
		});
		ll_scan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scan();
			}
		});
		ll_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SmsSp = getActivity().getSharedPreferences(Pref_SmsMessageInfo, 0);
				String msg = SmsSp.getString("SmsMessage", "");
				System.out.println("msg: "+msg);
				final EditText input = new EditText(getActivity()); // 定义一个EditText
				input.setText(msg);
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("请输入短信内容");
				builder.setView(input); // 将EditText添加到builder中
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mineFragment.msgContent = input.getText().toString();
						Editor editor = SmsSp.edit();
						editor.putString("SmsMessage", input.getText().toString());
						editor.commit();
					}
				});
				builder.show();
			}
		});
		ll_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quit();
			}
		});
	}
	
	private void show_mine() {
		Intent i = new Intent(getActivity(), CurUserDetail.class);
		startActivity(i);
	}
	
	private void scan() {
		Intent i = new Intent(getActivity(), CaptureActivity.class);
		startActivity(i);
//		startActivityForResult(i, 0);
	}
	
	private void quit() {
		ActivityManager manager = (ActivityManager)getActivity().
				getSystemService(Activity.ACTIVITY_SERVICE);
		System.out.println(getActivity().getPackageName());
		manager.restartPackage(getActivity().getPackageName());
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode == 0){
//			if(resultCode == Activity.RESULT_OK){
//				Bundle b = data.getExtras();
//				String code = b.getString("code");
//				String desc = b.getString("desc");
//				System.out.println("code: "+code);
//				System.out.println("desc: "+desc);
//				Scan_result_Dialog = AlertDialogUtils.alertTextDialog(getActivity(), R.layout.common_dialog, "扫描结果：", 
//						"确定", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.dismiss();
//							}
//						});
//				TextView scanDialog = (TextView) Scan_result_Dialog.findViewById(R.id.tv_content);
//				scanDialog.setText(code+"\n"+desc);
//			}
//		}
//	}
}
