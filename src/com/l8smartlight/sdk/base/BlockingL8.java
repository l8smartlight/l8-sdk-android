package com.l8smartlight.sdk.base;

import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.L8MethodNotSupportedException;
import com.l8smartlight.sdk.core.Sensor;

public abstract class BlockingL8 extends BaseL8 implements L8 {

	@Override
	public void getMatrix(OnColorMatrixResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getLED(int x, int y, OnColorResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getSuperLED(OnColorResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public void getSensor(Sensor sensor, OnSensorStatusResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getSensors(OnSensorStatusListResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getSensorEnabled(Sensor sensor, OnBooleanResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getBluetoothEnabled(OnBooleanResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getBatteryStatus(OnFloatResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getButton(OnIntegerResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getMemorySize(OnIntegerResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getFreeMemory(OnIntegerResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getID(OnStringResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getVersion(OnVersionResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();		
	}

	@Override
	public void getConnectionURL(OnStringResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

}
