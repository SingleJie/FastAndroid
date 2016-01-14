package com.wenjackp.android.lib.util;

import android.graphics.drawable.Drawable;

import java.util.regex.Pattern;

/**
 * Created by Single on 15-8-10.
 */
class FontStyle {

    public int textColor;

    public int textSize;

    public boolean showUnderLine;

    public boolean clickable;

    public String text;

    public Pattern pattern;

    public int start;

    public int end;

    public int backgroundColor;

    public int styles;

    public String links;

    public String family;

    public Drawable imageDrawable;

    public FontStyleBuilder.OnMatcherListener matcherListener;

    public static FontStyle buildStyle(String text,int start,int end,TextStyle style){
        FontStyle mStyle = new FontStyle();
        mStyle.start = start;
        mStyle.end = end;
        mStyle.text = text;

        if(style!=null) {
            mStyle.backgroundColor = style.backgroundColor;
            mStyle.textColor = style.textColor;
            mStyle.textSize = style.textSize;
            mStyle.clickable = style.clickable;
            mStyle.family = style.family;
            mStyle.showUnderLine = style.showUnderLine;
            mStyle.imageDrawable = style.imageDrawable;
        }

        return mStyle;
    }
}
