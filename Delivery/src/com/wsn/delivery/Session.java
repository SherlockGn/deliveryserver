package com.wsn.delivery;


import java.util.HashMap;
import java.util.List;

import com.wsn.delivery.courier.CourierVo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;


public class Session extends Application{
	private static Session session;
	  private static CourierVo currCourierVO = null;
	  private HashMap<String, Object> attribute = new HashMap<String, Object>();
	  public static int localVersion;
	  public static int UID;

	  public void onCreate()
	  {
	    super.onCreate();
	    session = this;
	    try
	    {
	      localVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
	      UID = getApplicationInfo().uid;
	    }
	    catch (Exception e)
	    {
	      localVersion = 1;
	    }
	    System.out.println("application创建,其ID是 ：" + UID + "---");
	  }

	  public static Session getSession()
	  {
	    return session;
	  }

	  public Object getAttribute(String key)
	  {
	    return this.attribute.get(key);
	  }

	  public void setAttribute(String key, Object value)
	  {
	    this.attribute.put(key, value);
	  }

	  public void removeAttribute(String key)
	  {
	    this.attribute.remove(key);
	  }

	  public void clear()
	  {
	    this.attribute.clear();
	    setCurrCourierVO(null);
	  }

	  public void onLowMemory()
	  {
	    super.onLowMemory();
	    System.out.println("error:内存低");
	  }

	  public boolean isAppOnForeground(Context context)
	  {
	    ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
	    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
	    if (appProcesses == null)
	      return false;
	    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses)
	    {
	      if ((appProcess.processName.equals(getSession().getPackageName())) && 
	        (appProcess.importance == 100))
	        return true;
	    }
	    return false;
	  }

	public static CourierVo getCurrCourierVO() {
		return currCourierVO;
	}

	public static void setCurrCourierVO(CourierVo currCourierVO) {
		Session.currCourierVO = currCourierVO;
	}
}
