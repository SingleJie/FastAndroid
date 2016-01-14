package com.wenjackp.android.lib.listener;

import android.view.View;
import android.view.ViewParent;

/**
 * Created by Single on 15-8-12.
 */
public interface OnItemClickListener {

    void onItemClick(ViewParent parent, View v, int position);
}
