package com.l8smartlight.sdk.usb;

import org.json.simple.JSONArray;

import com.l8smartlight.sdk.base.NonBlockingL8;
import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class USBL8 extends NonBlockingL8 implements L8 
{

	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.USB;
	}	
	
	@Override
	public void setMatrix(String image) throws L8Exception 
	{
		System.out.println("usb::setMatrix");
	}
	
	@Override
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		System.out.println("usb::setMatrix");
	}

	@Override
	public void clearMatrix() throws L8Exception 
	{
		System.out.println("usb::clearMatrix");
	}
	
	@Override
	public void getMatrix(OnColorMatrixResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getMatrix");		
	}
	
	@Override
	public void setLED(int x, int y, Color color) 
	{
		System.out.println("usb::setLED");
	}
	
	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		System.out.println("usb::clearLED");
	}

	@Override
	public void getLED(int x, int y, OnColorResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getLED");
	}

	@Override
	public void setSuperLED(String image) throws L8Exception 
	{
		System.out.println("usb::setSuperLED");
	}

	@Override
	public void setSuperLED(Color color) throws L8Exception 
	{
		System.out.println("usb::setSuperLED");
	}

	@Override
	public void clearSuperLED() throws L8Exception 
	{
		System.out.println("usb::setSuperLED");
	}

	@Override
	public void getSuperLED(OnColorResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getSuperLED");
	}

	@Override
	public void enableSensor(Sensor sensor) throws L8Exception 
	{
		System.out.println("usb::enableSensor");
	}

	@Override
	public void getSensor(Sensor sensor, OnSensorStatusResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getSensor");
	}

	@Override
	public void getSensors(OnSensorStatusListResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getSensors");
	}

	@Override
	public void disableSensor(Sensor sensor) throws L8Exception 
	{
		System.out.println("usb::disableSensor");
	}

	@Override
	public void getSensorEnabled(Sensor sensor, OnBooleanResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getSensorEnabled");
	}

	@Override
	public void getBluetoothEnabled(OnBooleanResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getBluetoothEnabled");
	}

	@Override
	public float getBatteryStatus() throws L8Exception 
	{
		System.out.println("usb::getBatteryStatus");
		return 0;
	}
	
	@Override
	public void getBatteryStatus(OnFloatResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getBatteryStatus");
	}

	@Override
	public void getButton(OnIntegerResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getButton");
	}

	@Override
	public void getMemorySize(OnIntegerResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getButton");
	}

	@Override
	public void getFreeMemory(OnIntegerResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getFreeMemory");
	}

	@Override
	public void getID(OnStringResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getFreeMemory");
	}

	@Override
	public void getVersion(OnVersionResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getVersion");
	}

	@Override
	public void setAnimation(JSONArray jsonFrames) throws L8Exception 
	{
		System.out.println("usb::setAnimation");
	}
	
	@Override
	public void setAnimation(Animation animation) throws L8Exception 
	{
		System.out.println("usb::getVersion");
	}

	@Override
	public void getConnectionURL(OnStringResultListener listener) throws L8Exception 
	{
		System.out.println("usb::getConnectionURL");
	}

	@Override
	public void stopCurrentL8app() throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runL8AppDice(Color color) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setL8Brightness(int Brightlevel) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotification(String bundle, int eventNotificationID,
			int categoryNotificationID) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setText(String text, int loop, Color color, int speed)
			throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runL8AppLuminosityAndProximity(int sensor, Color colorMatrix, Color colorBackLed, byte threshold)
			throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runL8AppLights(int lightColorMode, int speed, int backLedInverted) throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runL8AppPartyMode() throws L8Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutDown() throws L8Exception {
		// TODO Auto-generated method stub
		
	}



}
