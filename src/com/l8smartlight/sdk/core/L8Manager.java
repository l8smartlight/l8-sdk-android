package com.l8smartlight.sdk.core;

import java.util.List;

public interface L8Manager {

	public L8 reconnectDevice(String deviceId) throws L8Exception;
	
	public List<L8> discoverL8s() throws L8Exception;
	
}
