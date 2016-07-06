package com.iyiyo.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 代码自定义选择器，可以随机生成
 * 
 * @author AdministrorOnline
 * 
 */
public class DrawableUtil {
	/**
	 * 图片选择器�
	 * @param color
	 * @param radius
	 * @return
	 */
	public static GradientDrawable getGradientDrawable(int color, int radius) {
		// 形状shape对应的类
		GradientDrawable drawable = new GradientDrawable();
		// 设置类型为矩形
		drawable.setGradientType(GradientDrawable.RECTANGLE);
		// 设置矩形的圆角半径
		drawable.setCornerRadius(radius);
		// 设置颜色
		drawable.setColor(color);
		return drawable;
	}
	/**
	 * 生成状态选择器
	 */
	public static StateListDrawable getSelector(Drawable press,Drawable normal){
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_pressed}, press);
		drawable.addState(new int[]{}, normal);
		return drawable;
	}
	//重载状态选择器
	public static StateListDrawable getSelector(int normalColor,int pressColor,int radius){
		Drawable press = getGradientDrawable(pressColor, radius);
		Drawable normal = getGradientDrawable(normalColor, radius);
		return getSelector(press, normal);
	}
}
