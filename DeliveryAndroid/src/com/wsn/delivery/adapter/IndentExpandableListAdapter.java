package com.wsn.delivery.adapter;

import java.util.List;

import com.wsn.delivery.R;
import com.wsn.delivery.indent.indentVo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

//import com.wsn.courier.R;
//import com.wsn.courier.indent.indentVo;

public class IndentExpandableListAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	private LayoutInflater mInflater = null;
	private String[] mGropStrings = null;
	private List<List<indentVo>> mData = null;
	
	
	
	public IndentExpandableListAdapter(Context context,List<List<indentVo>> list){
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGropStrings = mContext.getResources().getStringArray(R.array.indent_groups);
		mData = list;
	}

	public void setData(List<List<indentVo>> list){
		mData = list;
	}
	
	@Override
	public int getGroupCount() {
		return mData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mData.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.indent_group_item_layout, null);
			holder = new GroupViewHolder();
			holder.mGropName = (TextView) convertView.findViewById(R.id.group_name);
			holder.mGroupCount = (TextView) convertView.findViewById(R.id.group_count);
			convertView.setTag(holder);
		}else{
			holder = (GroupViewHolder) convertView.getTag();
		}
		holder.mGropName.setText(mGropStrings[groupPosition]);
		holder.mGroupCount.setText("["+mData.get(groupPosition).size()+"]");
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.indent_child_item_layout, null);
			holder = new ChildViewHolder();
			holder.mChildName = (TextView) convertView.findViewById(R.id.tv_indent_id);
			holder.mChildTime = (TextView) convertView.findViewById(R.id.tv_indent_time);
			convertView.setTag(holder);
		}else{
			holder =(ChildViewHolder) convertView.getTag();
		}
		holder.mChildName.setText(mData.get(groupPosition).get(childPosition).getId());
		holder.mChildTime.setText(mData.get(groupPosition).get(childPosition).getTime());
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		//实现点击事件
		return true;
	}
	private class GroupViewHolder{
		TextView mGropName;
		TextView mGroupCount;
	}
	private class ChildViewHolder{
		TextView mChildName;
		TextView mChildTime;
//		ImageView mGotoDetailIcon;
	}
}

