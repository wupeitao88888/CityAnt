package com.iloomo.utils;



import android.util.Log;

public class SMSL {

	private SMSL() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebugSMS = true;
	private static final String TAG = "ledoing";

	public static void i(String msg) {
		if (isDebugSMS)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebugSMS)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebugSMS)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebugSMS)
			Log.v(TAG, msg);
	}

	public static void i(String tag, String msg) {
		if (isDebugSMS)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebugSMS)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebugSMS)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebugSMS)
			Log.i(tag, msg);
	}
}