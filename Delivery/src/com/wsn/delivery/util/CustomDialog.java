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
 * <p>����:�Զ���Ի���</p> 
 * <p>����:�Զ���Ի���,ֻ֧����ʾ��ʾ��Ϣ�����ߣ��Զ��岼�֡�
 *    ��δ֧�֣�������ѡ�б���ѡ�б����󣬿��Ը�����Ҫ������չ</p> 
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
    * Ϊ��ʹ�Զ���ĶԻ����AlertDialog���÷�һ�����Լ������޸���д���Ĵ��룬����Ҳ�Զ���һ��Builder��
    * ���ң������ԭ��AlertDialog��Builder����ͬ���Ƶļ��������� �Ժ�����չ����Զ�����Ĺ��ܣ����ڸ�Builder����ӵķ���
    * *****/
   public static class Builder
   {
      private Context context;
      private String title;// ����
      private String message;// ��ʾ��Ϣ
      private String positiveButtonText;// "ȷ��"��ť��ʾ������
      private String negativeButtonText;// "ȡ��"��ť��ʾ������
      private View contentView;// ����ʾ��View
      
      
      /***
       * "ȷ��"��ť�Լ�"ȡ��"��ť�ļ�����
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

      // ����"ȷ��"��ť��ʾ�����֣��Լ�������
      // positiveButtonText����ԴID
      public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener)
      {
         this.positiveButtonText = (String) context.getText(positiveButtonText);
         this.positiveButtonClickListener = listener;
         return this;
      }

      // ����"ȷ��"��ť��ʾ�����֣��Լ�������
      // positiveButtonText������ʾ���ַ���
      public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener)
      {
         this.positiveButtonText = positiveButtonText;
         this.positiveButtonClickListener = listener;
         return this;
      }

      // ����"ȡ��"��ť��ʾ�����֣��Լ�������
      // negativeButtonText����ԴID
      public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener)
      {
         this.negativeButtonText = (String) context.getText(negativeButtonText);
         this.negativeButtonClickListener = listener;
         return this;
      }

      // ����"ȡ��"��ť��ʾ�����֣��Լ�������
      // negativeButtonText������ʾ���ַ���
      public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener)
      {
         this.negativeButtonText = negativeButtonText;
         this.negativeButtonClickListener = listener;
         return this;
      }

      // �����Ի���
      public CustomDialog create()
      {
         LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         /** �����Ի��򣬲����öԻ������ʽ��R.style.confirm_dialog */
         final CustomDialog dialog = new CustomDialog(context, R.style.confirm_dialog);
         View layout = inflater.inflate(layoutId, null);
         dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
         // ���ñ���
         ((TextView) layout.findViewById(R.id.title)).setText(title);

         if (positiveButtonText != null)
         {
            ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
            if (positiveButtonClickListener != null)
            {// ����"ȷ��"��ť�ļ�����
               ((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener()
               {
                  public void onClick(View v)
                  {
                     positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                  }
               });
            }
            else
            {// �����������Ϊ�գ���Ĭ�����ضԻ���
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
         {// ���,"ȷ��"��ť����ʾ����Ϊ�գ���Ѱ�ť����
            layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
         }
         // �����߼�ͬ��
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
         // ���message��Ϊ�գ���������ʾ��Ϣ
         if (message != null)
         {
            ((TextView) layout.findViewById(R.id.message)).setText(message);
         }
         else if (contentView != null)
         {
            // ���contentView��Ϊnull������ʹ���Զ��岼�ֵ�View�����Զ��岼�ֵ�View��ӵ�������
            ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
            ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(
                  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
         }
         dialog.setContentView(layout);
         return dialog;
      }
   }
}