package com.wsn.courier.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.wsn.courier.R;
import com.wsn.courier.Session;

public class LoadingDialog extends Dialog {

	private TextView dialog_loading;
	private Handler loadingHandler;
	  private String loadingName;
	public LoadingDialog(Context context) {
		super(context,Session.getSession().getResources().getIdentifier("dialog", "style", Session.getSession().getPackageName()));
//		super(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(Session.getSession().getResources().getIdentifier("sys_loading", "layout", Session.getSession().getPackageName()));
		setContentView(R.layout.sys_loading);
		this.dialog_loading = (TextView) findViewById(R.id.dialog_loading);
		this.loadingHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				LoadingDialog.this.dialog_loading.setText(LoadingDialog.this.loadingName);
			}
		};
	}

	private void onProgressChanged() {
		this.loadingHandler.sendEmptyMessage(0);
	}
	public void setLoadingName(String loadingName) {
		this.loadingName = loadingName;
		onProgressChanged();
	}
}
