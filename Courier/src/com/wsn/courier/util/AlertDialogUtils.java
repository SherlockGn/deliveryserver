package com.wsn.courier.util;


import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

public class AlertDialogUtils {

	
	
	public static View alertTextDialog(Context context, int resource, String title, String pBtnText,
	         OnClickListener pBtnListener) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View mEntryView = factory.inflate(resource, null);
		CustomDialog mDialog = new CustomDialog.Builder(context).setTitle(title).setContentView(mEntryView)
	            .setPositiveButton(pBtnText, pBtnListener).setNegativeButton("取消", null).create();
		mDialog.show();
		mEntryView.setTag(mDialog);
		return mEntryView;
	}
}
