package com.wenjackp.android.lib.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字体处理工具
 * @author WenJackp
 * @version 1.0
 */
public class TextTools {

    private StringBuilder mBuilder;
    private List<FontStyle> mFontStyles;
    private SpannableString mString;

    private HashMap<Pattern, FontStyleBuilder.OnMatcherListener> mPatterns;

    public TextTools(String text) {
        if (!EmptyUtils.emptyOfString(text)) {
            mBuilder = new StringBuilder(text);
        } else {
            mBuilder = new StringBuilder();
        }
        mPatterns = new HashMap<>();
    }

    public TextTools append(String text, int start, int end, TextStyle style) {
        checkStylesIsEmpty();
        mBuilder.append(text, start, end);
        mFontStyles.add(FontStyle.buildStyle(text, start, end, style));
        return this;
    }

    public TextTools append(String text) {
        checkStylesIsEmpty();
        mBuilder.append(text);
        mFontStyles.add(FontStyle.buildStyle(text, 0, 0, null));
        return this;
    }

    public TextTools append(String text, TextStyle style) {
        checkStylesIsEmpty();
        mBuilder.append(text);
        mFontStyles.add(FontStyle.buildStyle(text, mBuilder.length(), mBuilder.length() + text.length(), style));
        return this;
    }

    public TextTools replace(String text, int start, int end, TextStyle style) {
        checkStylesIsEmpty();
        mBuilder.replace(start, end, text);
        mFontStyles.add(FontStyle.buildStyle(text, start, end, style));
        return this;
    }

    public TextTools change(Pattern pattern, FontStyleBuilder.OnMatcherListener mListener) {
        checkPatternIsEmpty();
        mPatterns.put(pattern, mListener);
        return this;
    }

    public SpannableString build() {

        mString = new SpannableString(mBuilder.toString());

        if(!EmptyUtils.emptyOfList(mFontStyles)){
            int length = mFontStyles.size();
            for (int i = 0; i < length; i++) {
                FontStyle mBuilder = mFontStyles.get(i);
                int start;
                int end;

                if (mBuilder.end > 0) {
                    start = mBuilder.start;
                    end = mBuilder.end;
                    setSpan(start, end, mBuilder);
                }
            }
        }

        if (!EmptyUtils.emptyOfMap(mPatterns)) {
            int start = 0;
            int end = 0;

            for (Map.Entry<Pattern, FontStyleBuilder.OnMatcherListener> mTemp : mPatterns.entrySet()) {
                Pattern mPattern = mTemp.getKey();
                Matcher matcher = mPattern.matcher(mString);
                while (matcher.find()) {
                    String content = matcher.group();
                    start = matcher.start();
                    end = matcher.end();

                    if (mTemp.getValue() != null) {
                        TextStyle mBuilder = mTemp.getValue().onMatcher(content);
                        if (mBuilder != null) {
                            setSpan(start,end, FontStyle.buildStyle(content, matcher.start(), matcher.end(), mBuilder));
                        }
                    }
                }
            }
        }
        return mString;
    }

    private void setSpan(int start, int end, FontStyle mBuilder) {

        if(mBuilder==null){
            return;
        }

        if (mBuilder.textColor != 0) {
            ForegroundColorSpan mSpan = new ForegroundColorSpan(mBuilder.textColor);
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (mBuilder.showUnderLine) {
            UnderlineSpan mSpan = new UnderlineSpan();
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (mBuilder.backgroundColor != 0) {
            BackgroundColorSpan mSpan = new BackgroundColorSpan(mBuilder.backgroundColor);
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (mBuilder.styles != 0) {
            StyleSpan mSpan = new StyleSpan(mBuilder.styles);
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (!EmptyUtils.emptyOfString(mBuilder.links)) {
            URLSpan mSpan = new URLSpan(mBuilder.links);
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (!EmptyUtils.emptyOfString(mBuilder.family)) {
            TypefaceSpan mSpan = new TypefaceSpan(mBuilder.family);
            mString.setSpan(mSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if(mBuilder.imageDrawable != null){
            ImageSpan mSpan = new ImageSpan(mBuilder.imageDrawable);
            mString.setSpan(mSpan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void checkStylesIsEmpty() {
        if (EmptyUtils.emptyOfList(mFontStyles)) {
            mFontStyles = new ArrayList<>();
        }
    }

    private void checkPatternIsEmpty() {
        if (EmptyUtils.emptyOfMap(mPatterns)) {
            mPatterns = new HashMap<>();
        }
    }
}
