package com.wsn.delivery.activity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.wsn.delivery.R;
import com.wsn.delivery.Session;
import com.wsn.delivery.fragment.mineFragment;
import com.wsn.delivery.scan.camera.CameraManager;
import com.wsn.delivery.scan.decode.DecodeThread;
import com.wsn.delivery.scan.utils.BeepManager;
import com.wsn.delivery.scan.utils.CaptureActivityHandler;
import com.wsn.delivery.scan.utils.InactivityTimer;
import com.wsn.delivery.util.AlertDialogUtils;

public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;

    private Rect mCropRect = null;
    private boolean isHasSurface = false;

    public static String Pref_SmsMessageInfo = "SmsMessageInfo.pref";
    
    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan);

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation
                .RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, final Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        //==================================================
//        courierScan?courierid=xxx&courierusername=xxx&indentid=xxx 
        String url = getResources().getString(R.string.url);
        String URL = url+"courierScan?courierid="+Session.getCurrCourierVO().getUserId()
        		+"&indentid="+rawResult.getText();
        System.out.println(URL);
        RequestQueue rq = Volley.newRequestQueue(CaptureActivity.this);
        Request<JSONObject> request_scan = new JsonObjectRequest(URL, null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					String code = response.getString("code");
					String desc = response.getString("desc");
					System.out.println("code"+code);
					System.out.println("desc"+desc);
					//发送短信=========================================================
					if(desc.startsWith("courier scan success")){
						String tophone = desc.substring(desc.lastIndexOf(":")+1).trim();
						System.out.println(tophone);
						SharedPreferences SmsSp = getSharedPreferences(Pref_SmsMessageInfo, 0);
						String msg = SmsSp.getString("SmsMessage", "");
						System.out.println(msg);
						SmsManager smsManager = SmsManager.getDefault();
						ArrayList<String> smss = smsManager.divideMessage(msg);
						for (String sms : smss) {
							System.out.println(sms+" "+smss.size());
							smsManager.sendTextMessage(tophone, null, sms, null, null);
						}
					}
					//=====================================================================
					View result = AlertDialogUtils.alertTextDialog
							(CaptureActivity.this, 
									R.layout.common_dialog, "扫描结果：", 
									"确定", new OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent intent = getIntent();
											finish();
											startActivity(intent);
											dialog.dismiss();
										}
									});
					TextView tv = (TextView) result.findViewById(R.id.tv_content);
					if(desc.startsWith("courier scan success")){
						tv.setText("扫描成功");
					}else tv.setText(desc);
					
//			        Intent resultIntent = new Intent();
//			        bundle.putString("code", code);
//			        bundle.putString("desc", desc);
//			        resultIntent.putExtras(bundle);
//			        CaptureActivity.this.setResult(RESULT_OK, resultIntent);
//			        CaptureActivity.this.finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error);
			}
		});
        rq.add(request_scan);
    }

    protected void sendSMS() {
//    	String url = getResources().getString(R.string.url);
//        String URL = url+"courierScan?courierid="+Session.getCurrCourierVO().getUserId()
//        		+"&indentid="+rawResult.getText();
//        System.out.println(URL);
//        RequestQueue rq = Volley.newRequestQueue(CaptureActivity.this);
//        Request<JSONObject> request_queryUser = new JsonObject
    	
//		SmsManager smsManager = SmsManager.getDefault();
//		ArrayList<String> smss = smsManager.divideMessage("尊敬的客户，收到短信请尽快收取您的快递");
//		for (String sms : smss) {
//			smsManager.sendTextMessage("客户电话号码", "18705192253或null", sms, null, null);
//		}
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Camera error");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 鍒濆鍖栨埅鍙栫殑鐭╁舰鍖哄煙
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 鑾峰彇甯冨眬涓壂鎻忔鐨勪綅缃俊鎭� */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 鑾峰彇甯冨眬瀹瑰櫒鐨勫楂� */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 璁＄畻鏈�缁堟埅鍙栫殑鐭╁舰鐨勫乏涓婅椤剁偣x鍧愭爣 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 璁＄畻鏈�缁堟埅鍙栫殑鐭╁舰鐨勫乏涓婅椤剁偣y鍧愭爣 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 璁＄畻鏈�缁堟埅鍙栫殑鐭╁舰鐨勫搴� */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 璁＄畻鏈�缁堟埅鍙栫殑鐭╁舰鐨勯珮搴� */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 鐢熸垚鏈�缁堢殑鎴彇鐨勭煩褰� */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}