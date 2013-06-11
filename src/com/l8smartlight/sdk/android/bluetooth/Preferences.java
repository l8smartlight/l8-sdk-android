package com.l8smartlight.sdk.android.bluetooth;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
	
	private static final String LAST_DEVICE = "last_device";
	private static final String LAST_EMULATOR = "last_emulator";
	private SharedPreferences preferences;

	public Preferences(Context ctx) {
		preferences = ctx.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
	}
	
	public String getLastConnectedDevice() {
		return preferences.getString(LAST_DEVICE, null);
	}
	
	public void setLastConnectedDevice(String device) {
		preferences.edit().putString(LAST_DEVICE, device).commit();
	}

	public String getLastConnectedEmulator() {
		return preferences.getString(LAST_EMULATOR, null);
	}
	
	public void setLastConnectedEmulator(String emulator) {
		preferences.edit().putString(LAST_EMULATOR, emulator).commit();
	}
	
}
