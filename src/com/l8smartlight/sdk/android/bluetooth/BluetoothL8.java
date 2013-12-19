package com.l8smartlight.sdk.android.bluetooth;

import java.util.ArrayList;
import java.util.List;

import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class BluetoothL8 implements L8 {
	
	public static final int NUM_ROWS	= 8;
	public static final int NUM_COLUMNS	= 8;
	
	public enum L8Mode 
	{
		L8_MODE_4BIT,	//Default mode
		L8_MODE_8BIT
	}

	protected BluetoothClient bluetoothClient;
	protected L8Mode mode;
	
	public BluetoothL8(BluetoothClient bluetoothClient) 
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

	public byte[] receive(int max) {
		return null;
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
	public Color[][] readMatrix() throws L8Exception 
	{
		System.out.println("bluetooth::readMatrix");
		return null;
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
	public Color readLED(int x, int y) throws L8Exception 
	{
		System.out.println("bluetooth::readLED");
		return null;
	}
	
	@Override
	public void setSuperLED(Color color) throws L8Exception {
		stopCurrentAnimation(); // TODO: Esto yo creo que está mal porque el superled es parte de la animación.
		send(RLPCommand.BuildBackledSet(color, mode));
	}
	
	@Override
	public void clearSuperLED() throws L8Exception {
		stopCurrentAnimation();
		System.out.println("bluetooth::clearSuperLED");
	}	
	
	@Override
	public Color readSuperLED() throws L8Exception {
		System.out.println("bluetooth::readSuperLED");
		return null;
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
	public Sensor.Status readSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::readSensor");
		return new Sensor.TemperatureStatus(false, 0.0f, 0.0f);
	}
	
	@Override
	public List<Sensor.Status> readSensors() throws L8Exception {
		return new ArrayList<Sensor.Status>();
	}

	@Override
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::isSensorEnabled");
		return true;
	}
	
	@Override
	public boolean isBluetoothEnabled() throws L8Exception
	{
		System.out.println("bluetooth::isBluetoothEnabled");
		return false;
	}
	
	@Override
	public int getBatteryStatus() throws L8Exception
	{
		System.out.println("bluetooth::getBatteryStatus");
		return 0;
	}
	
	@Override
	public int getButton() throws L8Exception
	{
		System.out.println("bluetooth::getButton");
		return 0;		
	}
	
	@Override
	public int getMemorySize() throws L8Exception
	{
		System.out.println("bluetooth::getMemorySize");
		return 0;		
	}

	@Override
	public int getFreeMemory() throws L8Exception
	{
		System.out.println("bluetooth::getFreeMemory");
		return 0;		
	}
	
	@Override
	public String getId() throws L8Exception
	{
		System.out.println("bluetooth::getId");
		return null;
	}
	
	@Override
	public L8.Version getVersion() throws L8Exception
	{
		System.out.println("bluetooth::getVersion");
		return null;
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
	
}
