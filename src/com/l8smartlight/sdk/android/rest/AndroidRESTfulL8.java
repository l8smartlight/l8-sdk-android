package com.l8smartlight.sdk.android.rest;

import java.util.List;

import android.os.AsyncTask;

import com.l8smartlight.sdk.android.Util;
import com.l8smartlight.sdk.base.NonBlockingL8;
import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.L8MethodNotSupportedException;
import com.l8smartlight.sdk.core.Sensor;
import com.l8smartlight.sdk.rest.RESTfulL8;

import es.develappers.rest.Response;

public class AndroidRESTfulL8 extends NonBlockingL8 implements L8 {

	protected final RESTfulL8 l8;
	
	public AndroidRESTfulL8() throws L8Exception {
		super();
		l8 = new RESTfulL8();
	}
	
	public L8 reconnectSimulator(String simul8torToken) {
		l8.simul8torToken = simul8torToken;
		try {
			Response response = l8.client.get("/l8s/" + l8.simul8torToken + "/ping");
			if (response.getCode() == 200) {
				return this;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void createSimulator() throws L8Exception {
		l8.createSimulator();
	}
	
	@Override
	public void getBatteryStatus(final OnFloatResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Float>() {
			
			protected Float doInBackground(Void... params) {
				try {
					return l8.getBatteryStatus();
				} catch (L8Exception e) {
					Util.error("Error: getBatteryStatus");
				}
				return null;
			}
			
			protected void onPostExecute(Float result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void clearLED(final int x, final int y) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.clearLED(x, y);
				} catch (L8Exception e) {
					Util.error("Error: clearLED");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void clearMatrix() throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.clearMatrix();
				} catch (L8Exception e) {
					Util.error("Error: clearMatrix");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void clearSuperLED() throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.clearSuperLED();
				} catch (L8Exception e) {
					Util.error("Error: clearSuperLED");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void disableSensor(final Sensor sensor) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.disableSensor(sensor);
				} catch (L8Exception e) {
					Util.error("Error: disableSensor");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void enableSensor(final Sensor sensor) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.enableSensor(sensor);
				} catch (L8Exception e) {
					Util.error("Error: enableSensor");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void getBluetoothEnabled(final OnBooleanResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Boolean>() {
			
			protected Boolean doInBackground(Void... params) {
				try {
					return l8.getBluetoothEnabled();
				} catch (L8Exception e) {
					Util.error("Error: getBluetoothEnabled");
				}
				return null;
			}
			
			protected void onPostExecute(Boolean result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getButton(final OnIntegerResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Integer>() {
			
			protected Integer doInBackground(Void... params) {
				try {
					return l8.getButton();
				} catch (L8Exception e) {
					Util.error("Error: getButton");
				}
				return null;
			}
			
			protected void onPostExecute(Integer result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.RESTful;		
	}
	
	@Override
	public String getConnectionURL() throws L8Exception {
		return l8.getConnectionURL();
	}
	
	@Override
	public void getConnectionURL(OnStringResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getFreeMemory(final OnIntegerResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Integer>() {
			
			protected Integer doInBackground(Void... params) {
				try {
					return l8.getFreeMemory();
				} catch (L8Exception e) {
					Util.error("Error: getFreeMemory");
				}
				return null;
			}
			
			protected void onPostExecute(Integer result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public String getID() throws L8Exception {
		return l8.getID();
	}	
	
	@Override
	public void getID(final OnStringResultListener listener) throws L8Exception {
		throw new L8MethodNotSupportedException();
	}

	@Override
	public void getLED(final int x, final int y, final OnColorResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Color>() {
			
			protected Color doInBackground(Void... params) {
				try {
					return l8.getLED(x, y);
				} catch (L8Exception e) {
					Util.error("Error: getLED");
				}
				return null;
			}
			
			protected void onPostExecute(Color result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getMatrix(final OnColorMatrixResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Color[][]>() {
			
			protected Color[][] doInBackground(Void... params) {
				try {
					return l8.getMatrix();
				} catch (L8Exception e) {
					Util.error("Error: getMatrix");
				}
				return null;
			}
			
			protected void onPostExecute(Color[][] result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getMemorySize(final OnIntegerResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Integer>() {
			
			protected Integer doInBackground(Void... params) {
				try {
					return l8.getMemorySize();
				} catch (L8Exception e) {
					Util.error("Error: getMemorySize");
				}
				return null;
			}
			
			protected void onPostExecute(Integer result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getSensor(final Sensor sensor, final OnSensorStatusResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Sensor.Status>() {
			
			protected Sensor.Status doInBackground(Void... params) {
				try {
					return l8.getSensor(sensor);
				} catch (L8Exception e) {
					Util.error("Error: getSensor");
				}
				return null;
			}
			
			protected void onPostExecute(Sensor.Status result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getSensorEnabled(final Sensor sensor, final OnBooleanResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Boolean>() {
			
			protected Boolean doInBackground(Void... params) {
				try {
					return l8.getSensorEnabled(sensor);
				} catch (L8Exception e) {
					Util.error("Error: getSensorEnabled");
				}
				return null;
			}
			
			protected void onPostExecute(Boolean result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getSensors(final OnSensorStatusListResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, List<Sensor.Status>>() {
			
			protected List<Sensor.Status> doInBackground(Void... params) {
				try {
					return l8.getSensors();
				} catch (L8Exception e) {
					Util.error("Error: getSensors");
				}
				return null;
			}
			
			protected void onPostExecute(List<Sensor.Status> result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getSuperLED(final OnColorResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, Color>() {
			
			protected Color doInBackground(Void... params) {
				try {
					return l8.getSuperLED();
				} catch (L8Exception e) {
					Util.error("Error: getSuperLED");
				}
				return null;
			}
			
			protected void onPostExecute(Color result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();
	}

	@Override
	public void getVersion(final OnVersionResultListener listener) throws L8Exception {
		new AsyncTask<Void, Void, L8.Version>() {
			
			protected L8.Version doInBackground(Void... params) {
				try {
					return l8.getVersion();
				} catch (L8Exception e) {
					Util.error("Error: getVersion");
				}
				return null;
			}
			
			protected void onPostExecute(L8.Version result) {
				if (listener != null) {
					listener.onResult(result);					
				}
			}
			
		}.execute();

	}
	
	@Override
	public void setAnimation(final Animation animation) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.setAnimation(animation);
				} catch (L8Exception e) {
					Util.error("Error: setAnimation");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void setLED(final int x, final int y, final Color color) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.setLED(x, y, color);
				} catch (L8Exception e) {
					Util.error("Error: setLED");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void setMatrix(final Color[][] matrix) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.setMatrix(matrix);
				} catch (L8Exception e) {
					Util.error("Error: setMatrix");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}
	
	@Override
	public void setSuperLED(final Color color) throws L8Exception {
		new AsyncTask<Void, Void, Void>() {
			
			protected Void doInBackground(Void... params) {
				try {
					l8.setSuperLED(color);
				} catch (L8Exception e) {
					Util.error("Error: setSuperLed");
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				
			}
			
		}.execute();
	}

	@Override
	public void stopCurrentL8app() throws L8Exception {
		
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
