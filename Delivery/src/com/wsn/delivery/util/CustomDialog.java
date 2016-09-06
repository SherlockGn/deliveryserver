package com.wsn.delivery.util;


import com.wsn.delivery.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * <p>标题:自定义对话框</p> 
 * <p>描述:自定义对话框,只支持显示提示信息，或者，自定义布局。
 *    尚未支持，弹出单选列表、多选列表。往后，可以根据需要进行扩展</p> 
 */
public class CustomDialog extends Dialog
{

   public CustomDialog(Context context, int theme)
   {
      super(context, theme);
   }

   public CustomDialog(Context context)
   {
      super(context);
   }

   static public int layoutId;
   
   /***
    * 为了使自定义的对话框跟AlertDialog的用法一样，以及不用修改以写过的代码，这里也自定义一个Builder类
    * 并且，添加了原来AlertDialog的Builder类相同名称的几个方法。 以后，需扩展这个自定义类的功能，可在个Builder类添加的方法
    * *****/
   public static class Builder
   {
      private Context context;
      private String title;// 标题
      private String message;// 提示信息
      private String positiveButtonText;// "确定"按钮显示的文字
      private String negativeButtonText;// "取消"按钮显示的文字
      private View contentView;// 需显示的View
      
      
      /***
       * "确定"按钮以及"取消"按钮的监听器
       * *******/
      private DialogInterface.OnClickListener positiveButtonClickListener , negativeButtonClickListener;

      public Builder(Context context)
      {
         this.context = context;
         layoutId = R.layout.com_customdialog;
      }
      
      public Builder(Context context,int loyoutId)
      {
         this.context = context;
         layoutId = loyoutId;
      }

      public Builder setMessage(String message)
      {
         this.message = message;
         return this;
      }

      public Builder setMessage(int message)
      {
         this.message = (String) context.getText(message);
         return this;
      }

      public Builder setTitle(int title)
      {
         this.title = (String) context.getText(title);
         return this;
      }

      public Builder setTitle(String title)
      {
         this.title = title;
         return this;
      }

      public Builder setContentView(View v)
      {
         this.contentView = v;
         return this;
      }

      // 设置"确定"按钮显示的文字，以及监听器
      // positiveButtonText：资源ID
      public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener)
      {
         this.positiveButtonText = (String) context.getText(positiveButtonText);
         this.positiveButtonClickListener = listener;
         return this;
      }

      // 设置"确定"按钮显示的文字，以及监听器
      // positiveButtonText：需显示的字符串
      public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener)
      {
         this.positiveButtonText = positiveButtonText;
         this.positiveButtonClickListener = listener;
         return this;
      }

      // 设置"取消"按钮显示的文字，以及监听器
      // negativeButtonText：资源ID
      public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener)
      {
         this.negativeButtonText = (String) context.getText(negativeButtonText);
         this.negativeButtonClickListener = listener;
         return this;
      }

      // 设置"取消"按钮显示的文字，以及监听器
      // negativeButtonText：需显示的字符串
      public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener)
      {
         this.negativeButtonText = negativeButtonText;
         this.negativeButtonClickListener = listener;
         return this;
      }

      // 创建对话框
      public CustomDialog create()
      {
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         /** 创建对话框，并设置对话框的样式：R.style.confirm_dialog */
         final CustomDialog dialog = new CustomDialog(context, R.style.confirm_dialog);
         View layout = inflater.inflate(layoutId, null);
         dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
         // 设置标题
         ((TextView) layout.findViewById(R.id.title)).setText(title);

         if (positiveButtonText != null)
         {
            ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
            if (positiveButtonClickListener != null)
            {// 设置"确定"按钮的监听器
               ((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener()
               {
                  public void onClick(View v)
                  {
                     positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                  }
               });
            }
            else
            {// 如果，监听器为空，则默认隐藏对话框
               ((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener()
               {
                  public void onClick(View v)
                  {
                     dialog.dismiss();
                  }
               });
            }
         }
         else
         {// 如果,"确认"按钮的显示文字为空，这把按钮隐藏
            layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
         }
         // 以下逻辑同上
         if (negativeButtonText != null)
         {
            ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
            if (negativeButtonClickListener != null)
            {
               ((Button) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener()
               {
                  public void onClick(View v)
                  {
                     negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                  }
               });
            }
            else
            {
               ((Button) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener()
               {
                  public void onClick(View v)
                  {
                     dialog.dismiss();
                  }
               });
            }
         }
         else
         {
            layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
         }
         // 如果message不为空，这设置显示信息
         if (message != null)
         {
            ((TextView) layout.findViewById(R.id.message)).setText(message);
         }
         else if (contentView != null)
         {
            // 如果contentView不为null，则是使用自定义布局的View，则将自定义布局的View添加到容器中
            ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
            ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(
                  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
         }
         dialog.setContentView(layout);
         return dialog;
      }
   }
}