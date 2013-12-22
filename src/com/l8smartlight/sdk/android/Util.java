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
	
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(int count, byte[] bytes) 
    {
        char[] hexChars = new char[count * 2];
        int v;
        for ( int j = 0; j < count; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }        

}