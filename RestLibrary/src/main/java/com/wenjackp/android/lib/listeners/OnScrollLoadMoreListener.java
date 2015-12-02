package com.wenjackp.android.lib.listeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 加载更多监听类
 *   <b>完成数据加载后必须设置onLoadMoreCompelete(),否则只会执行一次.</b>
 * @author Single
 * @version 1.0
 * @category Listener
 */
public class OnScrollLoadMoreListener implements OnScrollListener {
	
	private OnLoadMoreListener mItemVisiableListener;
	private boolean mLastItemVisible = false;
	private boolean isLoading = false;
	
	public OnScrollLoadMoreListener(OnLoadMoreListener mItemVisiableListener)
	{
		this.mItemVisiableListener = mItemVisiableListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		if(scrollState==OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible==true && isLoading==false)
		{
			isLoading = true;
			mItemVisiableListener.onLastItemVisiable();
		}
	}

	/**
	 * 完成数据加载后需要调用此方法重置
	 */
	public void onLoadMoreCompelete()
	{
		isLoading = false;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) 
	{	
		mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);	
	}

	public static interface OnLoadMoreListener
	{
		public void onLastItemVisiable();
	}
}
