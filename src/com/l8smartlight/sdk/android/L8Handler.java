package com.l8smartlight.sdk.android;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.os.AsyncTask;

import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8.Animation;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class L8Handler {
	
	public static interface L8Operation {
		
		public void execute() throws L8Exception;
		
	}
	
	public static interface L8OperationListener {
		
		public void onExecuted(Object result);
		
	}
	
	protected L8 l8;
	
	public L8Handler(L8 l8) {
		this.l8 = l8;
	}
		
	public void executeVoidOperation(final L8Operation operation) {
		new AsyncTask<Void, Void, Object>() {
			protected Object doInBackground(Void... params) {
				try {
					operation.execute();
				} catch (L8Exception e) {
					Util.log("Error: executeVoidOperation");
				}
				return null;
			}
			
			protected void onPostExecute(Object result) {
				
			}
		}.execute();
	}
		
	public void setMatrix(String colorMatrix) {
		setMatrix(Color.matrixFromString(colorMatrix));
	}
	
	public void setMatrix(final Color[][] colorMatrix) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.setMatrix(colorMatrix);
			}
		});
	}
	
	public void clearMatrix() {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.clearMatrix();
			}
		});
	}
	
	public void setSuperLED(final Color color) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.setSuperLED(color);
			}
		});		
	}
	
	public void clearSuperLED() {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.clearSuperLED();
			}
		});		
	}
	
	public void setLED(final int x, final int y, final Color color) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.setLED(x, y, color);
			}
		});
	}
	
	public void clearLED(final int x, final int y) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.clearLED(x, y);
			}
		});
	}
	
	public void enableSensor(final Sensor sensor) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.enableSensor(sensor);
			}
		});
	}
	
	public void disableSensor(final Sensor sensor) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.disableSensor(sensor);
			}
		});		
	}
	
	public void setAnimation(JSONArray jsonFrames) {
		List<L8.Frame> frames = new ArrayList<L8.Frame>();
		for (int i = 0; i < jsonFrames.size(); i++) {
			JSONObject jsonFrame = (JSONObject)jsonFrames.get(i);
			String strDuration = (String)jsonFrame.get("duration");
			Integer duration = Integer.valueOf(strDuration);
			String image = (String)jsonFrame.get("image");
			L8.Frame frame = new L8.Frame(Color.matrixFromString(image), duration);
			frames.add(frame);
		}
		L8.Animation animation = new L8.Animation(frames);
		setAnimation(animation);
	}
	
	public void setAnimation(final Animation animation) {
		executeVoidOperation(new L8Operation() {
			@Override
			public void execute() throws L8Exception {
				l8.setAnimation(animation);
			}
		});		
	}
	
	public void readSensor(final Sensor sensor, final L8OperationListener listener) {
		new AsyncTask<Void, Void, Sensor.Status>() {
			protected Sensor.Status doInBackground(Void... params) {
				Sensor.Status status = null;
				try {
					status = l8.readSensor(sensor);
				} catch (L8Exception e) {
					Util.log("Error: readSensor");
				}
				return status;
			}
			
			protected void onPostExecute(Sensor.Status result) {
				if (listener != null) {
					listener.onExecuted(result);					
				}
			}
		}.execute();
	}
	
	public void readSensors(final L8OperationListener listener) {
		new AsyncTask<Void, Void, List<Sensor.Status>>() {
			protected List<Sensor.Status> doInBackground(Void... params) {
				List<Sensor.Status> status = null;
				try {
					status = l8.readSensors();
				} catch (L8Exception e) {
					Util.log("Error: readSensors");
				}
				return status;
			}
			
			protected void onPostExecute(List<Sensor.Status> result) {
				if (listener != null) {
					listener.onExecuted(result);					
				}
			}
		}.execute();
	}
	
	public void isSensorEnabled(final Sensor sensor, final L8OperationListener listener) {
		new AsyncTask<Void, Void, Boolean>() {
			protected Boolean doInBackground(Void... params) {
				Boolean enabled = false;
				try {
					enabled = l8.isSensorEnabled(sensor);
				} catch (L8Exception e) {
					Util.log("Error: isSensorEnabled");
				}
				return enabled;
			}
			
			protected void onPostExecute(Boolean result) {
				if (listener != null) {
					listener.onExecuted(result);					
				}
			}
		}.execute();
	}
	
	/*
	public boolean isBluetoothEnabled() {
		return false;
	}
	*/
	public void getBatteryStatus(final L8OperationListener listener) {
		new AsyncTask<Void, Void, Integer>() {
			protected Integer doInBackground(Void... params) {
				Integer status = 0;
				try {
					status = l8.getBatteryStatus();
				} catch (L8Exception e) {
					Util.log("Error: getBatteryStatus");
				}
				return status;
			}
			
			protected void onPostExecute(Integer result) {
				if (listener != null) {
					listener.onExecuted(result);					
				}
			}
		}.execute();
	}
/*
	public int getButton() {
		return 0;
	}
	
	public int getMemorySize() {
		return 0;
	}

	public int getFreeMemory() {
		return 0;
	}
	*/

}
