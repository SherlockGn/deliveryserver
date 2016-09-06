package com.wsn.delivery.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wsn.delivery.R;
import com.wsn.delivery.adapter.FragmentAdapter;
import com.wsn.delivery.fragment.indentFragment;
import com.wsn.delivery.fragment.mineFragment;


public class MainActivity extends FragmentActivity implements OnClickListener{
	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;
	private ViewPager mViewPager;
	//Tab显示内容
	private TextView tv_indent,tv_mine;
	//Tab引导线
	private ImageView mTabLineIv;
	//Fragment
	private indentFragment mIndentFg;
//	private ScanFragment mScanFg;
	private mineFragment mMineFg;
	//ViewPager的当前选中页
	private int currentIndex;
	//屏幕的宽度
	private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        initTabLineWidth();
    }
    
	private void initView() {
		tv_indent = (TextView) findViewById(R.id.tv_chat);
//		tv_friend = (TextView) findViewById(R.id.tv_friend);
		tv_mine = (TextView) findViewById(R.id.tv_mine);
		mTabLineIv = (ImageView) findViewById(R.id.iv_tab_line);
		mViewPager = (ViewPager) findViewById(R.id.vp_main);
		tv_indent.setOnClickListener(this);
//		tv_friend.setOnClickListener(this);
//		tv_courier.setOnClickListener(this);
		tv_mine.setOnClickListener(this);
	}
	
	private void init() {
		mIndentFg = new indentFragment();
//		mScanFg = new ScanFragment();
		mMineFg = new mineFragment();
		mFragmentList.add(mIndentFg);
//		mFragmentList.add(mScanFg);
		mFragmentList.add(mMineFg);
		mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
		mViewPager.setAdapter(mFragmentAdapter);
		mViewPager.setCurrentItem(0);
		currentIndex = 0;
		tv_indent.setTextColor(getResources().getColor(R.color.text_color_category_brown));
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			//state滑动中的状态 有三种状态（0，1，2） 0：什么都没  1：正在滑动  2：滑动完毕 
			@Override
			public void onPageSelected(int position) {
				changeView(position);
			}
			//position :当前页面，及你点击滑动的页面; offset:当前页面偏移的百分比; offsetPixels:当前页面偏移的像素位置 
			@Override
			public void onPageScrolled(int position, float offset, int offsetPixels) {
				LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams)mTabLineIv.getLayoutParams();
				if (currentIndex == 0 && position == 0) {  // 0->1
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex  
                            * (screenWidth / 2));  
  
                } else if (currentIndex == 1 && position == 0) {  // 1->0
                    lp.leftMargin = (int) (-(1 - offset)  
                            * (screenWidth * 1.0 / 2) + currentIndex  
                            * (screenWidth / 2));  
  
                } 
//                else if (currentIndex == 1 && position == 1) {  // 1->2
//                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex  
//                            * (screenWidth / 2));  
//                } else if (currentIndex == 2 && position == 1) {  // 2->1 
//                    lp.leftMargin = (int) (-(1 - offset)  
//                            * (screenWidth * 1.0 / 3) + currentIndex  
//                            * (screenWidth / 3));  
//                }
				mTabLineIv.setLayoutParams(lp);
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();  
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);  
        screenWidth = dpMetrics.widthPixels;  
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();  
        lp.width = screenWidth / 2;  
        mTabLineIv.setLayoutParams(lp); 
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_chat:
			changeView(0);
			break;
//		case R.id.tv_friend:
//			changeView(1);
//			break;
		case R.id.tv_mine:
			changeView(2);
			break;
		default:
			break;
		}
	}

	private void changeView(int i) {
		tv_indent.setTextColor(getResources().getColor(R.color.color_dark_grey));
//		tv_friend.setTextColor(getResources().getColor(R.color.color_dark_grey));
		tv_mine.setTextColor(getResources().getColor(R.color.color_dark_grey));
		switch (i) {
		case 0:
			tv_indent.setTextColor(getResources().getColor(R.color.text_color_category_brown));
			break;
		case 1:
//			tv_friend.setTextColor(getResources().getColor(R.color.text_color_category_brown));
//			break;
//		case 2:
			tv_mine.setTextColor(getResources().getColor(R.color.text_color_category_brown));
			break;
		default:
			break;
		}
		currentIndex = i;
		mViewPager.setCurrentItem(i, true);
	}
}
