package com.wenjackp.android.lib.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyHolder<T> extends RecyclerView.ViewHolder {
	
	public T mDatas;

	public MyHolder(View itemView) 
	{
		super(itemView);
		mDatas = (T) itemView;
	}

}
