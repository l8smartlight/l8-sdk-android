package com.l8smartlight.sdk.android.bluetooth;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import com.l8smartlight.sdk.android.Util;
import com.l8smartlight.sdk.base.NonBlockingL8;
import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class AndroidBluetoothL8 extends NonBlockingL8 implements L8 {
	
	public static final int NUM_ROWS	= 8;
	public static final int NUM_COLUMNS	= 8;
	
	public enum L8Mode 
	{
		L8_MODE_4BIT,	//Default mode
		L8_MODE_8BIT
	}

	protected BluetoothClient bluetoothClient;
	protected L8Mode mode;
	
	public AndroidBluetoothL8(BluetoothClient bluetoothClient) 
	{
		this.bluetoothClient = bluetoothClient;
		this.mode = L8Mode.L8_MODE_4BIT;
	}	
	
	public boolean send(byte[] buffer) 
	{
		try {
			if (bluetoothClient != null) {
				// Check that we're actually connected before trying anything
	            if (bluetoothClient.getState() != BluetoothClient.STATE_CONNECTED) {
	                return false;
	            }
	            // Check that there's actually something to send
	            if (buffer != null && buffer.length > 0) {
	            	bluetoothClient.write(buffer);
	                return true;
	            }
	    	}
		} catch(Exception ignored) {}
		return false;
	}
	
	protected L8.OnFloatResultListener readBatteryListener;
	
	public void received(int bytes, byte[] buffer) 
	{
		// TODO: Comentar:
		Util.error("BYTES READ: " + bytes + ": " + Util.bytesToHex(bytes, buffer));
		
        if (bytes > 1) {
        	byte code = buffer[0];
        	if (code == RLPCommand.READ_BATTERY_RESULT && readBatteryListener != null) {
        		byte[] v = new byte[2];
            	v[0] = buffer[1];
            	v[1] = buffer[2];
        		ByteBuffer bb = ByteBuffer.wrap(v);
        		bb.order(ByteOrder.BIG_ENDIAN);
        		int result = bb.getShort() & 0xffff; // para interpretar como unsigned short.
            	float batteryVoltage = (float)result / 1000;
            	readBatteryListener.onResult(batteryVoltage);
        	}
        }
	}

	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.Bluetooth;
	}
	
	public void setMode(L8Mode mode)
	{
		this.mode = mode;
	}
	
	@Override
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		stopCurrentAnimation();
		send(RLPCommand.BuildMatrixSet(colorMatrix, NUM_ROWS, NUM_COLUMNS, mode));
	}

	@Override
	public void clearMatrix() throws L8Exception 
	{
		stopCurrentAnimation();
		send(RLPCommand.BuildMatrixClear());
	}
	
	@Override
	public void setLED(int x, int y, Color color) throws L8Exception 
	{
		stopCurrentAnimation();
		send(RLPCommand.BuildLedSet((byte)x, (byte)y, color, mode));
	}

	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		stopCurrentAnimation();
		System.out.println("bluetooth::clearLED");
	}

	@Override
	public void setSuperLED(Color color) throws L8Exception {
		stopCurrentAnimation();
		send(RLPCommand.BuildBackledSet(color, mode));
	}
	
	@Override
	public void clearSuperLED() throws L8Exception {
		stopCurrentAnimation();
		System.out.println("bluetooth::clearSuperLED");
	}	
	
	@Override
	public void enableSensor(Sensor sensor) throws L8Exception 
	{
		System.out.println("bluetooth::enableSensor");
	}
	
	@Override
	public void disableSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::disableSensor");
	}
	
	@Override
	public void getBatteryStatus(L8.OnFloatResultListener listener) throws L8Exception {
		readBatteryListener = listener;
		send(RLPCommand.BuildReadBattery());
	}
	
	protected L8.Animation currentAnimation;
    protected int currentAnimationIndex = 0;	
    protected boolean shouldStopAnimation = true;
	protected Thread currentAnimationThread;
    
    protected void stopCurrentAnimation() 
    {
    	if (shouldStopAnimation && currentAnimationThread != null && currentAnimationThread.isAlive()) {
    		currentAnimationThread.interrupt();
    	}
    }
    
    protected void startCurrentAnimation(L8.Animation animation) 
    {
    	stopCurrentAnimation();
    	currentAnimation = animation;
    	currentAnimationIndex = 0;
    	currentAnimationThread = new Thread()
	    {
	        @Override
	        public void run() {
	    		try {
		        	while (true) {
		        		List<L8.Frame> frames = currentAnimation.getFrames();
		        		if (currentAnimationIndex < frames.size()) {
		        			L8.Frame currentFrame = frames.get(currentAnimationIndex);
		        			shouldStopAnimation = false;
		        			setMatrix(currentFrame.getMatrix());
		        			setSuperLED(currentFrame.getBackLed());
		        			shouldStopAnimation = true;
		        			currentAnimationIndex++;
		        			if (currentAnimationIndex == frames.size() - 1) currentAnimationIndex = 0;
		        			sleep(currentFrame.getDuration());
		        		}
		        	}
	    		} catch (InterruptedException e) {
	    			return;
	    		} catch (L8Exception e) {
	    			return;
	    		}
	        }
	    };    	
	    currentAnimationThread.start();
    }
	
	@Override
	public void setAnimation(L8.Animation animation) throws L8Exception
	{
		startCurrentAnimation(animation);
	}
	
	@Override
	public String getConnectionURL() throws L8Exception 
	{
		if (bluetoothClient != null && bluetoothClient.getConnectedDevice() != null) {
			return bluetoothClient.getConnectedDevice().getAddress();
		} else {
			return "";
		}
	}

	@Override
	public void getBluetoothEnabled(OnBooleanResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getButton(OnIntegerResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getConnectionURL(OnStringResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getFreeMemory(OnIntegerResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getID(OnStringResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getLED(int x, int y, OnColorResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getMatrix(OnColorMatrixResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getMemorySize(OnIntegerResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getSensor(Sensor sensor, OnSensorStatusResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getSensorEnabled(Sensor sensor, OnBooleanResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getSensors(OnSensorStatusListResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getSuperLED(OnColorResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void getVersion(OnVersionResultListener listener) throws L8Exception {
		// TODO Auto-generated method stub
	}
	
}
