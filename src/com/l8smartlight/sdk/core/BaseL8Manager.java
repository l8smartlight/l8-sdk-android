package com.l8smartlight.sdk.core;

import java.util.ArrayList;
import java.util.List;

import com.l8smartlight.sdk.rest.RESTfulL8;

public class BaseL8Manager implements L8Manager {

	public L8 reconnectDevice(String deviceId) throws L8Exception
	{
		RESTfulL8 l8 = new RESTfulL8();
		return l8.reconnectSimulator(deviceId);
	}
	
	public List<L8> discoverL8s() throws L8Exception 
	{
		List<L8> foundL8s = new ArrayList<L8>();
		if (foundL8s.size() == 0) {
			foundL8s.add(createEmulatedL8());
		}
		return foundL8s;
	}
	
	public L8 createEmulatedL8() throws L8Exception 
	{
		RESTfulL8 l8 = new RESTfulL8();
		l8.createSimulator();
		return l8;
	}
	
	/*
	public static void main(String[] args) 
	{
		try {
			
			BaseL8Manager l8Manager = new BaseL8Manager();
			
			L8 l8 = l8Manager.createEmulatedL8();
 			
			//L8 l8 = l8Manager.reconnectDevice("4c9b26176af0768e562837eeebdc227d");
			
			l8.clearMatrix();
			l8.setLED(0, 3, Color.CYAN);
			
			l8.getLED(0, 3, null);
			
			l8.clearLED(0, 3);
			
			Color[][] matrix = new Color[8][8];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					matrix[i][j] = Color.RED;
				}
			}
			l8.setMatrix(matrix);
			
			l8.getMatrix(null);
			
			l8.setSuperLED(Color.BLUE);
			
			l8.getSuperLED(null);
			
			l8.clearSuperLED();
			
			l8.enableSensor(Sensor.AMBIENTLIGHT);

			l8.disableSensor(Sensor.PROXIMITY);

			l8.getSensors(new L8.OnSensorStatusListResultListener() {
				@Override
				public void onResult(List<Status> sensors) {
					System.out.println(sensors);
				}
			});
			
			l8.getSensor(Sensor.TEMPERATURE, new L8.OnSensorStatusResultListener() {
				@Override
				public void onResult(Status temperature) {
					System.out.println("read temperature sensor: " + temperature);
				}
			});
			
			l8.getBluetoothEnabled(new L8.OnBooleanResultListener() {
				@Override
				public void onResult(boolean bluetoothEnabled) {
					System.out.println("bluetooth enabled? " + bluetoothEnabled);
				}
			});
			
			l8.setLED(4, 5, Color.LIGHT_GRAY);
			l8.setLED(7, 6, Color.BLUE);
			l8.setLED(1, 3, Color.GREEN);
			
			l8.getButton(null);
			
			l8.getMemorySize(null);
			
			l8.getFreeMemory(null);
			
			l8.getVersion(null);

			l8.getID(new L8.OnStringResultListener() {
				@Override
				public void onResult(String ID) {
					System.out.println("id: " + ID);
				}
			});
			
			Color[][] matrix0 = new Color[8][8];
			for (int i = 0; i < matrix0.length; i++) {
				for (int j = 0; j < matrix0[i].length; j++) {
					matrix0[i][j] = Color.RED;
				}
			}
			L8.Frame frame0 = new L8.Frame(matrix0, 100);

			Color[][] matrix1 = new Color[8][8];
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix1[i].length; j++) {
					matrix1[i][j] = Color.YELLOW;
				}
			}			
			L8.Frame frame1 = new L8.Frame(matrix1, 200);
			
			Color[][] matrix2 = new Color[8][8];
			for (int i = 0; i < matrix2.length; i++) {
				for (int j = 0; j < matrix2[i].length; j++) {
					matrix2[i][j] = Color.GREEN;
				}
			}			
			L8.Frame frame2 = new L8.Frame(matrix2, 400);
			
			
			List<L8.Frame> frames = new ArrayList<L8.Frame>();
			frames.add(frame0);
			frames.add(frame1);
			frames.add(frame2);
			L8.Animation animation = new L8.Animation(frames);
			l8.setAnimation(animation);
			
		} catch (L8Exception e) {
			e.printStackTrace();
		}
	}
	//*/

}
