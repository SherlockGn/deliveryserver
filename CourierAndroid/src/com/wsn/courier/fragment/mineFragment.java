package com.wsn.courier.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wsn.courier.R;
import com.wsn.courier.Session;
import com.wsn.courier.activity.CaptureActivity;
import com.wsn.courier.activity.CurUserDetail;
import com.wsn.courier.util.AlertDialogUtils;

public class mineFragment extends Fragment {
	View Scan_result_Dialog;
	LinearLayout ll_mine,ll_scan,ll_quit;
	View mineView;
	TextView tv_mine;
	
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
		ll_quit = (LinearLayout) mineView.findViewById(R.id.ll_quit);
	}
	
	private void init() {
		tv_mine.setText(Session.getCurrUserVO().getName());
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
		startActivityForResult(i, 0);
	}
	
	private void quit() {
		ActivityManager manager = (ActivityManager)getActivity().
				getSystemService(Activity.ACTIVITY_SERVICE);
		System.out.println(getActivity().getPackageName());
		manager.restartPackage(getActivity().getPackageName());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			if(resultCode == Activity.RESULT_OK){
				Bundle b = data.getExtras();
				String code = b.getString("code");
				String desc = b.getString("desc");
				System.out.println("code: "+code);
				System.out.println("desc: "+desc);
				Scan_result_Dialog = AlertDialogUtils.alertTextDialog(getActivity(), R.layout.common_dialog, "扫描结果：", 
						"确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				TextView scanDialog = (TextView) Scan_result_Dialog.findViewById(R.id.tv_content);
				scanDialog.setText(code+"\n"+desc);
			}
		}
	}
}
