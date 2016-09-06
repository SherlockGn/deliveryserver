package com.wsn.courier.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.TextUtils;

public class DateUtils {
  private static SimpleDateFormat SDF_DEFAULT_HEADER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static SimpleDateFormat SDF_DEFAULT_TIME = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat SDF_DEFAULT_BOOKTIME = new SimpleDateFormat("MM-dd HH");
  private static SimpleDateFormat DATETIME_FORMAT_TWO = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
  private static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss");

  public static Date transString2Date(String date) {
    try {
      String form = null;
      if (TextUtils.isEmpty(date))
        form = "1900-01-01 00:00:00";
      else if (date.trim().length() == 10)
        form = date + " 00:00:00";
      else if (date.trim().length() == 13)
        form = date + ":00:00";
      else if (date.trim().length() == 16)
        form = date + ":00";
      else
        form = date;
      return SDF_DEFAULT_HEADER.parse(form);
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String getDateString_cn(Date date)
  {
    return DATETIME_FORMAT_TWO.format(date);
  }

  public static String getDateString(Date date)
  {
    return SDF_DEFAULT_TIME.format(date);
  }

  public static String getDateTimeString(Date date)
  {
    return SDF_DEFAULT_HEADER.format(date);
  }

  public static String transformShort(Date date)
  {
    return SDF_DEFAULT_BOOKTIME.format(date);
  }

  public static String getCurrentDateTimeStr()
  {
    return getDateTimeString(new Date());
  }

  public static String convertMillsecondsToDateTime(long millseconds)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millseconds);
    return DATETIME_FORMAT.format(calendar.getTime());
  }

  public static String convertMillsecondsToDateTime(long millseconds, SimpleDateFormat SDF_DEFAULT_HEADER)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millseconds);
    return SDF_DEFAULT_HEADER.format(calendar.getTime());
  }

  public static String formatDateString(String str, String format)
  {
    try
    {
      String form = null;
      if (TextUtils.isEmpty(str))
        form = "1900-01-01 00:00:00";
      else if (str.trim().length() == 10)
        form = str + " 00:00:00";
      else if (str.trim().length() == 13)
        form = str + ":00:00";
      else if (str.trim().length() == 16)
        form = str + ":00";
      else
        form = str;
      return new SimpleDateFormat(format).format(SDF_DEFAULT_HEADER.parse(form));
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String compareWithToday(String date)
    throws ParseException
  {
    String retrunStr = null;
    Date begin = transString2Date(date);
    Date end = new Date();
    long between;
    if (end.getTime() > begin.getTime())
    {
      between = (end.getTime() - begin.getTime()) / 1000L;
      retrunStr = "已超时：";
    }
    else
    {
      between = (begin.getTime() - end.getTime()) / 1000L;
      retrunStr = "剩余：";
    }

    long day1 = between / 86400L;
    long hour1 = between % 86400L / 3600L;
    long minute1 = between % 3600L / 60L;
    return retrunStr + day1 + "天" + hour1 + "小时" + minute1 + "分";
  }

  public static String getDateBefore(String dateStr, int day)
  {
    Date date = null;
    try
    {
      date = SDF_DEFAULT_TIME.parse(dateStr);
    }
    catch (ParseException e)
    {
      return null;
    }
    Calendar now = Calendar.getInstance();
    now.setTime(date);
    now.set(5, now.get(5) - day - 1);
    Date beforeDate = now.getTime();
    return getDateString(beforeDate);
  }

  public static int getDayInWeek(String dateStr)
  {
    int weekDay = 0;
    try
    {
      int month = Integer.parseInt(dateStr.substring(5, 7));
      int date = Integer.parseInt(dateStr.substring(8));
      GregorianCalendar dc = new GregorianCalendar();
      dc.set(2, month - 1);
      dc.set(5, date);
      weekDay = (dc.get(7) - 1 + 7) % 7;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return weekDay;
  }

  public static String calendar2String(Calendar calendar)
  {
    return SDF_DEFAULT_TIME.format(new Date(calendar.getTimeInMillis()));
  }
}