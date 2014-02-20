package com.l8smartlight.sdk.base;

import java.util.List;

import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.L8MethodNotSupportedException;
import com.l8smartlight.sdk.core.Sensor;
import com.l8smartlight.sdk.core.Sensor.Status;

public abstract class NonBlockingL8 extends BaseL8 implements L8 {

	@Override
	public Color[][] getMatrix() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public Color getLED(int x, int y) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public Color getSuperLED() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public Status getSensor(Sensor sensor) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public List<Status> getSensors() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public boolean getSensorEnabled(Sensor sensor) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public boolean getBluetoothEnabled() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public float getBatteryStatus() throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public int getButton() throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public int getMemorySize() throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public int getFreeMemory() throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public String getID() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public Version getVersion() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public String getConnectionURL() throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

}
