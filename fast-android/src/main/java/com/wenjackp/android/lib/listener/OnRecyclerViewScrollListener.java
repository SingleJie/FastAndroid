package com.wenjackp.android.lib.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wenjackp.android.lib.utils.LogUtils;

/**
 * 加载更多监听类
 * 完成数据加载后必须设置onLoadMoreCompelete(),否则只会执行一次.
 * 暂时支持LinearLayoutManager、GridLayoutManager
 *
 * @author Single
 * @version 1.0
 */
public class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private boolean mLastItemVisiable = false;
    private boolean isLoading = false;
    private OnLastItemVisiableListener mListener;

    public OnRecyclerViewScrollListener(OnLastItemVisiableListener mItemVisiableListener) {
        this.mListener = mItemVisiableListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (mListener != null && newState == RecyclerView.SCROLL_STATE_IDLE && mLastItemVisiable && isLoading == false) {
            isLoading = true;
            mListener.onLastItemVisiable();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int lastVisiable = -1;
        int totalVisiable = -1;
        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();

        if (mLayoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mLayoutManager;
            lastVisiable = mLinearLayoutManager.findLastVisibleItemPosition();
            totalVisiable = mLinearLayoutManager.getItemCount();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            lastVisiable = mGridLayoutManager.findLastVisibleItemPosition();
            totalVisiable = mGridLayoutManager.getItemCount();
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) mLayoutManager;
            int[] temp = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);

            if (temp == null) {
                return;
            }

            if (temp.length > 1) {
                lastVisiable = temp[0] > temp[1] ? temp[0] : temp[1];
                totalVisiable = mStaggeredGridLayoutManager.getItemCount();
            }
        }

        if (lastVisiable != -1 && totalVisiable != -1 && lastVisiable + 1 == totalVisiable && dy > 0) {
            LogUtils.logEMsg(" LastVisiable : " + lastVisiable + "     total  " + totalVisiable);
            mLastItemVisiable = true;
        }
        super.onScrolled(recyclerView, dx, dy);
    }

    public void onLoadMoreCompelete() {
        isLoading = false;
        mLastItemVisiable = false;
    }

    public static interface OnLastItemVisiableListener {
        void onLastItemVisiable();
    }
}
