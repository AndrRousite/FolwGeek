package com.iyiyo.utils;

import android.util.Log;

/**
 * 打印日志帮助类
 * 
 * @author liu-feng
 */
public class L {

	private L() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = false;// 判断是否需要打印日志，可以在Application中初始化

	private static final String TAG = "MESSAGE";

	// 下面四个默人的Tag函数
	public static void i(String msg) {
		if (isDebug) {
			Log.i(TAG, msg);
		}
	}

	public static void d(String msg) {
		if (isDebug) {
			Log.d(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (isDebug) {
			Log.e(TAG, msg);
		}
	}

	public static void v(String msg) {
		if (isDebug) {
			Log.v(TAG, msg);
		}
	}

	// 下面为传入自定义的Tag函数
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, msg);
		}
	}
}
