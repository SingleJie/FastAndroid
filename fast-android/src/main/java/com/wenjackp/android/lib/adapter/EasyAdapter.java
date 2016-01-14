package com.wenjackp.android.lib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wenjackp.android.lib.entity.ViewHolder;
import com.wenjackp.android.lib.listener.OnItemClickListener;
import com.wenjackp.android.lib.util.EmptyUtil;
import com.wenjackp.android.lib.util.ViewHolderUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * EasyAdapter
 * 简介: 实现简单的列表形式,可以帮助你更快捷的开发,无须再重复实现内容
 * Created by Single on 15-7-18.
 *
 * @version 1.0
 */
public abstract class EasyAdapter<E, T extends ViewHolder> extends BaseAdapter {

    private int layout;
    private Class<T> mHolderClass;
    private List<E> mList;
    private OnItemClickListener mListener;

    public EasyAdapter(List<E> mList, int layout, Class<T> mHolderClass) {
        this.layout = layout;
        this.mList = mList;
        this.mHolderClass = mHolderClass;
    }

    public EasyAdapter(E[] mArray, int layout, Class<T> mHolderClass) {
        this.layout = layout;
        this.mList = arrayToList(mArray);
        this.mHolderClass = mHolderClass;
    }

    private List<E> arrayToList(E[] mArray){
        int length = mArray.length;
        List<E> mList = new ArrayList<>();
        for(int i=0;i<length;i++){
            mList.add(mArray[i]);
        }
        return mList;
    }

    private E[] listToArray(List<E> mList){
        int length = mList.size();
        E[] mNewArray = (E[])Array.newInstance(mList.getClass().getComponentType(),length);
        return mNewArray;
    }

    private EasyAdapter() {
    }

    public void updateDataSet(List<E> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    public void updateDataSet(E[] mArray) {
        this.mList = arrayToList(mArray);
        this.notifyDataSetChanged();
    }

    public void removeData(E mItem){
        if(!EmptyUtil.emptyOfList(mList)){
            this.mList.remove(mItem);
        }
        this.notifyDataSetChanged();
    }

    public void removeData(int position){

        if(!EmptyUtil.emptyOfList(mList)){
            mList.remove(position);
        }
        this.notifyDataSetChanged();
    }

    public void addData(List<E> mList) {
        this.mList.addAll(mList);
        this.notifyDataSetChanged();
    }

    public void addData(E[] mArray) {
        int length = mArray.length;
        for(int i=0;i<length;i++){
            this.mList.add(mArray[i]);
        }
        this.notifyDataSetChanged();
    }

    public void addData(E mItem) {

        if (!EmptyUtil.emptyOfList(this.mList)) {
            this.mList.add(mItem);
        }
        this.notifyDataSetChanged();
    }

    public E[] getArrayDataSet() {
        return listToArray(mList);
    }

    public List<E> getListDataSet() {
        return mList;
    }

    @Override
    public int getCount() {

        if (!EmptyUtil.emptyOfList(this.mList)) {
            return this.mList.size();
        }else {
            return 0;
        }
    }

    @Override
    public E getItem(int position) {

        if (!EmptyUtil.emptyOfList(this.mList)) {
            return this.mList.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ViewHolderUtil.loadingConvertView(parent.getContext(), convertView, layout, mHolderClass);
        final T mHolder = (T) convertView.getTag();
        mHolder.itemView = convertView;
        mHolder.currentPosition = position;
        onBindData(position, mHolder, getItem(position));
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onItemClick(v.getParent(),v,mHolder.currentPosition);
                }
            }
        });
        return convertView;
    }

    public abstract void onBindData(int position, T mViewHolder, E mItem);

    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }
}
