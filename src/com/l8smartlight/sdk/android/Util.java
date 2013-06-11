package com.l8smartlight.sdk.android;

import android.util.Log;

public class Util {

	public static void log(String message) {
		if (Constants.LOG_DEBUG) {
			Log.e(Constants.LOG_TAG, message);
		}
	}
	
	public static void error(String message) {
		Log.e(Constants.LOG_TAG, message);
	}
	
}