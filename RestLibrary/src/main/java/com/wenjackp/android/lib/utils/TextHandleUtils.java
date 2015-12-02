package com.wenjackp.android.lib.utils;//package edu.single.library.utils;
//
//import java.lang.ref.SoftReference;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import edu.single.library.R;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.BackgroundColorSpan;
//import android.text.style.ForegroundColorSpan;
//import android.text.style.ImageSpan;
//import android.text.style.TypefaceSpan;
//import android.text.style.URLSpan;
//import android.text.style.UnderlineSpan;
//import android.util.Log;
//
///**
// * 文字表情类
// * @author 柿蕉炒柚 | Single
// * @category 文字表情处理工具类
// * @version 1.2
// * @alertTime 2013/12/08 16:05
// */
//public class TextHandleUtils{
//	
//	/**输出标识*/
//	private static final String TAG = TextHandleUtils.class.getSimpleName();	
//	private static HashMap<Integer,SoftReference<Drawable>> drawables = new HashMap<Integer, SoftReference<Drawable>>();
//	private static int resourceId = 0;
//	private static Drawable drawable = null;
//	private static SoftReference<Drawable> softDrawable = null;
//	private static Field field = null;
//	
//	
//    public static void subStringAndCallBackImageUrl(final Context mContext,String regularExpression,String content,final IparseCallBack parseCallBack)
//    {
//    	List<String> list_url = new ArrayList<String>();
//    	String text = null;
//    	SpannableString spannableString = new SpannableString(content);
//    	Pattern pattern = Pattern.compile(regularExpression);
//    	Matcher matcher  = pattern.matcher(spannableString);
//    	
//    	while(matcher.find())
//    	{	
//    		if(list_url.size() == 0)
//    		{
//    			text = content.substring(0,matcher.start())+"...";
//    		}
//  
//    		list_url.add(matcher.group());
//    	}
//    	
//    	//CallBack
//    	parseCallBack.parseCallBack(list_url,text);
//    	
//    	text = null;
//    	list_url = null;
//    	spannableString = null;
//    	pattern = null;
//    	matcher = null;
//    }
//    
//    /**
//     * 资源回收
//     */
//    public static void recycleDrawableCache()
//    {
//    	drawables.clear();
//    	drawables = null;
//    }
//    
//    /**
//     * 设置文字表情
//     * @param regularExpression
//     * @param input
//     * @return
//     */
//    public static SpannableString setEmojiToText(final Context mContext,String regularExpression,SpannableString input)
//    {  	
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//		
//				try
//				{
//					field = R.drawable.class.getDeclaredField(strMatcher);	
//     				resourceId = Integer.parseInt(field.get(null).toString());
//					softDrawable = drawables.get(resourceId);
//					
//					if(softDrawable == null)
//					{
//						drawable = mContext.getResources().getDrawable(resourceId);
//						drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());		
//						drawables.put(resourceId,new SoftReference<Drawable>(drawable));
//					}
//					else
//					{
//						drawable = softDrawable.get();
//					}
//				
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//					printlnMsg("setEmojiToText Error!");
//				}
//			
//				return new ImageSpan(drawable);
//			}
//		},regularExpression,input);
//    }
//    
//    /**
//     * 替换字符串
//     * @param regularExpression
//     * @param input
//     * @param ireplaceText
//     * @return
//     */
//    public static String replaceText(String regularExpression,String input,final IreplaceText ireplaceText)
//    {
//    	Pattern pattern = Pattern.compile(regularExpression);
//    	Matcher matcher = pattern.matcher(input);
//    	
//    	while(matcher.find())
//    	{
//    		input = input.replace(matcher.group(),ireplaceText.getNewString(matcher.group()));
//    	}
//    	
//    	pattern = null;
//    	matcher = null;
//    	
//    	return input;
//    }
//    
//    /**
//     * 设置文本字体样式
//     * @param regularExpression
//     * @param input
//     * @param typeface
//     * @return
//     */
//    public static SpannableString setTextTypeFace(String regularExpression,SpannableString input,final String typeface)
//    {
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//				
//				return new TypefaceSpan(typeface);
//			}
//		}, regularExpression, input);
//    }
//    
//    /**
//     * 设置文本超链接
//     * @param regularExpression
//     * @param input
//     * @param url
//     * @return
//     */
//    public static SpannableString setTextUrlLinks(String regularExpression,SpannableString input,final String url)
//    {
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//				
//				return new URLSpan(url);
//			}
//		}, regularExpression,input);
//    }
//    
//    /**
//     * 设置文本下划线
//     * @param regularExpression
//     * @param input
//     * @param txtColor
//     * @return
//     */
//    public static SpannableString setTextUnderLines(String regularExpression,SpannableString input)
//    {
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//				
//				return new UnderlineSpan();
//			}
//		}, regularExpression,input);
//    }
//    
//    /**
//     * 设置文本前景色
//     * @param regularExpression
//     * @param input
//     * @param txtColor
//     * @return
//     */
//    public static SpannableString setTextColorByForeground(String regularExpression,SpannableString input,final int txtColor)
//    {
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//				
//				return new ForegroundColorSpan(txtColor);
//			}
//		},regularExpression,input);
//    }
//    
//    /**
//     * 设置文本背景色
//     * @param regularExpression
//     * @param input
//     * @param bgColor
//     * @return
//     */
//    public static SpannableString setTextColorByBackground(String regularExpression,SpannableString input,final int bgColor)
//    {
//    	return handleTextBySpanableString(new IgetNewSpan() {
//			
//			@Override
//			public Object createNewSpan(String strMatcher) {
//				
//				return new BackgroundColorSpan(bgColor);
//			}
//		},regularExpression,input);
//    }
//    
//    /**
//     * 处理文字
//     * @param span
//     * @param regularExpression
//     * @param input
//     * @return
//     */
//    private static SpannableString handleTextBySpanableString(IgetNewSpan getSpan,String regularExpression,SpannableString input)
//    {
//    	Pattern pattern = Pattern.compile(regularExpression);
//    	Matcher matcher = pattern.matcher(input);
//   	
//    	while(matcher.find())
//    	{
//    		input.setSpan(getSpan.createNewSpan(matcher.group()),matcher.start(),matcher.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//    	}
//    	
//    	pattern = null;
//    	matcher = null;
//    	
//    	return input;
//    }
//   
//    /**
//     * 日志打印
//     * @param msg
//     */
//    private static void logMsg(String msg)
//    {
//    	Log.i(TAG,msg);
//    }
//    
//    /**
//     * 控制台打印
//     * @param msg
//     */
//    private static void printlnMsg(String msg)
//    {
//    	System.out.println(msg);
//    }
//    
//    public interface IparseCallBack
//    {
//    	public void parseCallBack(List<String> imgUrls,String text);
//    }
//    
//    /**构造一个新的Span类型*/
//    private interface IgetNewSpan
//    {
//    	public Object createNewSpan(String strMatcher);
//    }
//    
//    /**替换新的字体*/
//    public interface IreplaceText
//    {
//    	public String getNewString(String callBack);
//    }
//
//
//}