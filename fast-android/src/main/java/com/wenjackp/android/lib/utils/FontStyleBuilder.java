package com.wenjackp.android.lib.utils;

import java.util.regex.Pattern;

/**
 * Created by Single on 15-8-10.
 */
public class FontStyleBuilder {

    private int textColor;

    private int textSize;

    private boolean showUnderLine;

    private boolean clickable;

    private String text;

    private Pattern pattern;

    private int backgroundColor;

    private int start;

    private int end;

    private int styles;

    private String links;

    private String family;

    private OnMatcherListener mMatcherListener;

    public FontStyleBuilder witch(String text){
        this.text = text;
        return this;
    }

    public FontStyleBuilder pattern(Pattern pattern){
        this.pattern = pattern;
        return this;
    }

    public FontStyleBuilder pattern(Pattern pattern,OnMatcherListener mMatcherListener){
        this.pattern = pattern;
        this.mMatcherListener = mMatcherListener;
        return this;
    }

    public FontStyleBuilder textColor(int textColor){
        this.textColor = textColor;
        return this;
    }

    public FontStyleBuilder showUnderLine(boolean showUnderLine){
        this.showUnderLine = showUnderLine;
        return this;
    }

    public FontStyleBuilder textSize(int textSize){
        this.textSize = textSize;
        return this;
    }

    public FontStyleBuilder startPoint(int start){
        this.start = start;
        return this;
    }

    public FontStyleBuilder endPoint(int end){
        this.end = end;
        return this;
    }

    public FontStyleBuilder textStyle(int styles){
        this.styles = styles;
        return this;
    }

    public FontStyleBuilder link(String links){
        this.links = links;
        return this;
    }

    public FontStyleBuilder typeFace(String family){
        this.family = family;
        return this;
    }

    public FontStyle build(){
        FontStyle mStyle = new FontStyle();
        mStyle.text = text;
        mStyle.textSize = textSize;
        mStyle.textColor = textColor;
        mStyle.showUnderLine = showUnderLine;
        mStyle.start = start;
        mStyle.end = end;
        mStyle.styles = styles;
        mStyle.links = links;
        mStyle.family = family;
        return mStyle;
    }

    public interface OnMatcherListener{
        TextStyle onMatcher(String matcherContent);
    }
}

