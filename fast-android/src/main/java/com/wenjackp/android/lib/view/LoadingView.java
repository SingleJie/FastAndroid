package com.wenjackp.android.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wenjackp.android.lib.R;
import com.wenjackp.android.lib.util.EmptyUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * loading控件
 *
 * @author Single
 * @version 2.0
 */
public class LoadingView extends FrameLayout implements View.OnClickListener {

    /**
     * 没有数据
     */
    private View emptyView;

    /**
     * 加载等待
     */
    private View loadingView;

    /**
     * 加载失败
     */
    private View failureView;

    /**
     * 正文内容
     */
    private View targetView;

    /**
     * 重新加载
     */
    private OnReLoadCallBack mCallBack;

    /**
     * 默认字体大小
     */
    private int defaultTextSize = 16;

    /**
     * 找不到VIEW_ID的时候返回默认值
     */
    public static final int NO_ID = -1;

    /**
     * 默认存在的视图个数
     */
    private final int VALUE_COUNT = 3;

    /**
     * 空内容的位置
     */
    private final int EMPTY_VIEW_POSITION = 2;

    /**
     * 加载的位置
     */
    private final int LOADING_VIEW_POSITION = 1;

    /**
     * 加载失败的位置
     */
    private final int FAILURE_VIEW_POSITION = 0;

    /**
     * 目的内容的位置
     */
    private final int TARGET_VIEW_POSITION = 3;


    public LoadingView(Context context, View targetView) {
        super(context);
        this.targetView = targetView;
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init() {

        loadingView = createLoadingView();
        emptyView = createEmptyView();
        failureView = createFailureView();

        LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mParams.gravity = Gravity.CENTER;

        this.addView(failureView, FAILURE_VIEW_POSITION, mParams);
        this.addView(loadingView, LOADING_VIEW_POSITION, mParams);
        this.addView(emptyView, EMPTY_VIEW_POSITION, mParams);
        this.addView(targetView, TARGET_VIEW_POSITION);
        hiddenAllViews();
    }

    private void init(AttributeSet attrs) {

        handleAttribute(attrs);

        if (loadingView == null) {
            loadingView = createLoadingView();
        }

        if (emptyView == null) {
            emptyView = createEmptyView();
        }

        if (failureView == null) {
            failureView = createFailureView();
        }

        LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mParams.gravity = Gravity.CENTER;

        this.addView(failureView, mParams);
        this.addView(loadingView, mParams);
        this.addView(emptyView, mParams);

        if (targetView != null) {
            this.addView(targetView);
        }
    }

    private void handleAttribute(AttributeSet attrs) {

        TypedArray mArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0);
        int tempResID = NO_ID;
        final int indexCount = mArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int attr = mArray.getIndex(i);

            if (attr == R.styleable.LoadingView_loadingView) {
                tempResID = mArray.getResourceId(attr, NO_ID);
                if (tempResID != NO_ID) {
                    loadingView = inflaterView(tempResID);
                }

            } else if (attr == R.styleable.LoadingView_emptyView) {
                tempResID = mArray.getResourceId(attr, NO_ID);
                if (tempResID != NO_ID) {
                    emptyView = inflaterView(tempResID);
                }

            } else if (attr == R.styleable.LoadingView_failureView) {
                tempResID = mArray.getResourceId(attr, NO_ID);
                if (tempResID != NO_ID) {
                    failureView = inflaterView(tempResID);
                }

            } else if (attr == R.styleable.LoadingView_targetView) {
                tempResID = mArray.getResourceId(attr, NO_ID);
                if (tempResID != NO_ID) {
                    targetView = inflaterView(tempResID);
                }

            } else if (attr == R.styleable.LoadingView_failureReLoad) {
                if (getContext().isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot "
                            + "be used within a restricted context");
                }

                final String handlerName = mArray.getString(attr);
                if (handlerName != null) {

                    if (failureView == null) {
                        failureView = createFailureView();
                    }

                    failureView.setTag(handlerName);
                    failureView.setOnClickListener(this);
                }

            }
        }
        mArray.recycle();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView != null) {
            this.emptyView = emptyView;
            removeViewAt(EMPTY_VIEW_POSITION);
            addView(emptyView, EMPTY_VIEW_POSITION);
        }
    }

    public void setLoadingView(View loadingView) {

        if (loadingView != null) {
            this.loadingView = loadingView;
            removeViewAt(LOADING_VIEW_POSITION);
            LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mParams.gravity = Gravity.CENTER;
            addView(loadingView, LOADING_VIEW_POSITION, mParams);
        }
    }

    public void setFailureView(View failureView) {

        if (failureView != null) {
            this.failureView = failureView;
            this.failureView.setOnClickListener(this);
            removeViewAt(FAILURE_VIEW_POSITION);
            addView(failureView, FAILURE_VIEW_POSITION);
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        if (emptyView instanceof TextView) {
            TextView mEmptyView = (TextView) emptyView;
            mEmptyView.setText(emptyText);
        }
    }

    public void setEmptyText(int resId) {
        if (emptyView instanceof TextView) {
            TextView mEmptyView = (TextView) emptyView;
            mEmptyView.setText(resId);
        }
    }

    public void setEmptyTextColor(int color) {
        if (emptyView instanceof TextView) {
            TextView mEmptyView = (TextView) emptyView;
            mEmptyView.setTextColor(color);
        }
    }

    private void hiddenAllViews() {
        targetView.setVisibility(INVISIBLE);
        loadingView.setVisibility(INVISIBLE);
        emptyView.setVisibility(INVISIBLE);
        failureView.setVisibility(INVISIBLE);
    }

    public void loading() {

        if (loadingView != null) {
            loadingView.setVisibility(VISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(INVISIBLE);
        }

        if (failureView != null) {
            failureView.setVisibility(INVISIBLE);
        }

        if (targetView != null) {
            targetView.setVisibility(INVISIBLE);
        } else {
            showOrHideTargetView(INVISIBLE);
        }
    }

    public void success() {

        if (targetView != null) {
            targetView.setVisibility(VISIBLE);
        } else {
            showOrHideTargetView(VISIBLE);
        }

        if (loadingView != null) {
            loadingView.setVisibility(INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(INVISIBLE);
        }

        if (failureView != null) {
            failureView.setVisibility(INVISIBLE);
        }
    }

    public void failure() {
        if (targetView != null) {
            targetView.setVisibility(INVISIBLE);
        } else {
            showOrHideTargetView(GONE);
        }

        if (loadingView != null) {
            loadingView.setVisibility(INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(INVISIBLE);
        }

        if (failureView != null) {
            failureView.setVisibility(VISIBLE);
        }
    }

    public void empty() {

        if (targetView != null) {
            targetView.setVisibility(INVISIBLE);
        } else {
            showOrHideTargetView(GONE);
        }

        if (loadingView != null) {
            loadingView.setVisibility(INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(VISIBLE);
        }

        if (failureView != null) {
            failureView.setVisibility(INVISIBLE);
        }
    }

    private void showOrHideTargetView(int visiable) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            if (i >= VALUE_COUNT) {
                getChildAt(i).setVisibility(visiable);
            }
        }
    }

    private void clearCache() {
        this.removeView(loadingView);
        this.removeView(emptyView);
        this.removeView(failureView);
        loadingView = null;
        emptyView = null;
        failureView = null;
    }

    private View inflaterView(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId, null);
    }

    public void setOnReloadCallBack(OnReLoadCallBack onReloadCallBack) {
        this.mCallBack = onReloadCallBack;
    }

    @Override
    public void onClick(View v) {

        loading();

        if (mCallBack != null) {
            mCallBack.reloadMethod();
        } else {

            String handlerName = v.getTag() == null ? null : v.getTag().toString();

            if (!EmptyUtil.emptyOfString(handlerName)) {

                Method mHandler = null;

                if (mHandler == null) {
                    try {
                        mHandler = getContext().getClass().getMethod(handlerName,
                                View.class);
                    } catch (NoSuchMethodException e) {
                        int id = getId();
                        String idText = id == NO_ID ? "" : " with id '"
                                + getContext().getResources().getResourceEntryName(
                                id) + "'";
                        throw new IllegalStateException("Could not find a method " +
                                handlerName + "(View) in the activity "
                                + getContext().getClass() + " for onClick handler"
                                + " on view " + LoadingView.this.getClass() + idText, e);
                    }
                }

                try {
                    mHandler.invoke(getContext(), LoadingView.this);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Could not execute non "
                            + "public method of the activity", e);
                } catch (InvocationTargetException e) {
                    throw new IllegalStateException("Could not execute "
                            + "method of the activity", e);
                }
            }
        }
    }

    private void toastMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public interface OnReLoadCallBack {
        public void reloadMethod();
    }

    private TextView createEmptyView() {
        TextView mTextView = new TextView(getContext());
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, defaultTextSize);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setText("没有任何数据");
        return mTextView;
    }

    private ProgressBar createLoadingView() {
        ProgressBar mProgressBar = new ProgressBar(getContext());
        return mProgressBar;
    }

    private AppCompatButton createFailureView() {
        AppCompatButton mButton = new AppCompatButton(getContext());
        mButton.setBackgroundColor(Color.TRANSPARENT);
        mButton.setGravity(Gravity.CENTER);
        mButton.setTextColor(Color.BLACK);
        mButton.setText("重新加载");
        mButton.setTextSize(defaultTextSize);
        mButton.setOnClickListener(this);
        return mButton;
    }

    public void emptyDataFullScreen() {
        if (targetView != null) {
            targetView.setVisibility(INVISIBLE);
        }

        if (loadingView != null) {
            loadingView.setVisibility(INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(VISIBLE);
        }

        if (failureView != null) {
            failureView.setVisibility(INVISIBLE);
        }
    }
}
