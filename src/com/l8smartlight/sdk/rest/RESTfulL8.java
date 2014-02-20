package com.l8smartlight.sdk.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.l8smartlight.sdk.base.BlockingL8;
import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

import es.develappers.rest.RESTfulClient;
import es.develappers.rest.Response;

public class RESTfulL8 extends BlockingL8 implements L8 
{
	// DES:
	//private final String SIMUL8TOR_BASE_URL = "http://192.168.1.33:8888/l8-server-simulator";
	// PRE:
	//private final String SIMUL8TOR_BASE_URL = "http://54.228.218.122/l8-server-simulator";
	public static final String SIMUL8TOR_BASE_URL = "http://l8pre.develappers.es/l8-server-simulator";
	
	public RESTfulClient client = null;
	public String simul8torToken = null;
	
	protected String printColor(Color color) 
	{
		String alphaRedGreenBlue = Integer.toHexString(color.getRGB());
		String redGreenBlue = alphaRedGreenBlue.substring(2);
		return "#" + redGreenBlue; 
	}
	
	protected Color parseColor(String color) 
	{
		Integer red = Integer.valueOf(color.substring(1, 2), 16);
		Integer green = Integer.valueOf(color.substring(3, 5), 16);
		Integer blue = Integer.valueOf(color.substring(5), 16);
		return new Color(red, green, blue);
	}
	
	protected Color readColor(JSONObject json, String name)
	{
		String stringValue = (String)json.get(name);
		return parseColor(stringValue);
	}
	
	protected float readFloat(JSONObject json, String name)
	{
		String stringValue = (String)json.get(name);
		float floatValue = Double.valueOf(stringValue).floatValue();
		return floatValue;
	}
	
	protected int readInt(JSONObject json, String name)
	{
		return (int)readFloat(json, name);
	}
	
	protected boolean readBool(JSONObject json, String name)
	{
		String value = (String)json.get(name);
		return value.equals("1");		
	}
	
	protected String join(String[] input, String delimiter)
	{
	    StringBuilder sb = new StringBuilder();
	    for (String value : input) {
	        sb.append(value);
	        sb.append(delimiter);
	    }
	    int length = sb.length();
	    if (length > 0) {
	        sb.setLength(length - delimiter.length());
	    }
	    return sb.toString();
	}	
	
	protected String printMatrix(Color[][] matrix)
	{
		String[] rowColors =  new String[matrix.length*matrix.length];
		int idx = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				rowColors[idx++] = printColor(matrix[i][j]);
			}
		}
		return join(rowColors, "-");	
	}
	
	public RESTfulL8() throws L8Exception 
	{
		try {
			this.client = new RESTfulClient(SIMUL8TOR_BASE_URL);
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	public void createSimulator() throws L8Exception 
	{
		try {
			Response response = this.client.post("/l8s");
			if (response.getCode() == 201) {
				JSONObject json = (JSONObject)response.getJSON();
				this.simul8torToken = (String)json.get("id");
			} else {
				throw new L8Exception("Error creating a new simul8tor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	public L8 reconnectSimulator(String simul8torToken)
	{
		this.simul8torToken = simul8torToken;
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/ping");
			if (response.getCode() == 200) {
				return this;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.RESTful;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			for (int i = 0; i< colorMatrix.length; i++) {
				for (int j = 0; j < colorMatrix.length; j++) {
					message.put("led" + i + j, printColor(colorMatrix[i][j]));
				}
			}
			for (int i = 0; i < 8; i++) {
				message.put("frame" + i, "");
				message.put("frame" + i + "_duration", 0);
			}
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simul8tor matrix");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void clearMatrix() throws L8Exception 
	{
		try {
			Color[][] matrix = new Color[8][8];
			JSONObject message = new JSONObject();
			for (int i = 0; i< matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					message.put("led" + i + j, printColor(Color.BLACK));
				}
			}
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simul8tor matrix");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public Color[][] getMatrix() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/led");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				Color[][] matrix = new Color[8][8];			
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						String ledName = "led" + i + "" + j;
						matrix[i][j] = readColor(json, ledName);
					}
				}
				return matrix;
			}		
			throw new L8Exception("Error reading simul8tor matrix");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}	

	@Override
	@SuppressWarnings("unchecked")
	public void setLED(int x, int y, Color color) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put("led" + x + y, printColor(color));
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simul8tor LED {" + x + "," + y + "}");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		setLED(x, y, Color.BLACK);
	}

	@Override
	public Color getLED(int x, int y) throws L8Exception  
	{
		try {
			String ledName = "led" + x + "" + y;
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/led/" + ledName);
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readColor(json, ledName);
			}		
			throw new L8Exception("Error reading simul8tor LED {" + x + "," + y + "}");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
		
	@Override
	@SuppressWarnings("unchecked")	
	public void setSuperLED(Color color) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put("superled", printColor(color));
			Response response = this.client.put("/l8s/" + this.simul8torToken + "/superled", message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simul8tor superLED");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	public void clearSuperLED() throws L8Exception 
	{
		setSuperLED(Color.BLACK);
	}
	
	@Override
	public Color getSuperLED() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/superled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readColor(json, "superled");
			}		
			throw new L8Exception("Error reading simul8tor superLED");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void enableSensor(Sensor sensor) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put(sensor.getName()+ "_sensor_enabled", "1");
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error enabling simul8tor sensor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void disableSensor(Sensor sensor) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put(sensor.getName()+ "_sensor_enabled", "0");
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error disabling simul8tor sensor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public Sensor.Status getSensor(Sensor sensor) throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/sensor/" + sensor.getName());
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				Sensor.Status result = new Sensor.Status(false);
				if (sensor.equals(Sensor.TEMPERATURE)) {
					float celsiusValue = readFloat(json, "temperature_sensor_data_celsius");
					float fahrenheitValue = readFloat(json, "temperature_sensor_data_fahrenheit");
					result = new Sensor.TemperatureStatus(readBool(json, "temperature_sensor_enabled"), celsiusValue, fahrenheitValue);
				} else if (sensor.equals(Sensor.ACCELERATION)) {
					int rawX = readInt(json, sensor.getName() + "_sensor_data_rawX");
					int rawY = readInt(json, sensor.getName() + "_sensor_data_rawY");
					int rawZ = readInt(json, sensor.getName() + "_sensor_data_rawZ");
					int shake = readInt(json, sensor.getName() + "_sensor_data_shake");
					int orientation = readInt(json, sensor.getName() + "_sensor_data_orientation");
					result = new Sensor.AccelerationStatus(readBool(json, "acceleration_sensor_enabled"), rawX, rawY, rawZ, shake, orientation, 0, 0);
				} else {
					int value = readInt(json, sensor.getName() + "_sensor_data");
					if (sensor.equals(Sensor.AMBIENTLIGHT)) {
						result = new Sensor.AmbientLightStatus(readBool(json, "ambientlight_sensor_enabled"), value);
					} else if (sensor.equals(Sensor.PROXIMITY)) {
						result = new Sensor.ProximityStatus(readBool(json, "proximity_sensor_enabled"), value);
					} else if (sensor.equals(Sensor.NOISE)) {
						result = new Sensor.NoiseStatus(readBool(json, "noise_sensor_enabled"), value);
					}
				}
				return result;
			}		
			throw new L8Exception("Error reading simul8tor sensor");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public List<Sensor.Status> getSensors() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/sensor");
			if (response.getCode() == 200) {
				List<Sensor.Status> sensors = new ArrayList<Sensor.Status>();
				
				JSONObject json = (JSONObject)response.getJSON();

				float celsiusValue = readFloat(json, "temperature_sensor_data_celsius");
				float fahrenheitValue = readFloat(json, "temperature_sensor_data_fahrenheit");
				sensors.add(new Sensor.TemperatureStatus(readBool(json, "temperature_sensor_enabled"), celsiusValue, fahrenheitValue));

				int rawX = readInt(json, "acceleration_sensor_data_rawX");
				int rawY = readInt(json, "acceleration_sensor_data_rawY");
				int rawZ = readInt(json, "acceleration_sensor_data_rawZ");
				int shake = readInt(json, "acceleration_sensor_data_shake");
				int orientation = readInt(json, "acceleration_sensor_data_orientation");
				sensors.add(new Sensor.AccelerationStatus(readBool(json, "acceleration_sensor_enabled"), rawX, rawY, rawZ, shake, orientation, 0, 0));

				sensors.add(new Sensor.AmbientLightStatus(readBool(json, "ambientlight_sensor_enabled"), readInt(json, "ambientlight_sensor_data")));
				
				sensors.add(new Sensor.ProximityStatus(readBool(json, "proximity_sensor_enabled"), readInt(json, "proximity_sensor_data")));
				
				sensors.add(new Sensor.NoiseStatus(readBool(json, "noise_sensor_enabled"), readInt(json, "noise_sensor_data")));

				return sensors;
			}		
			throw new L8Exception("Error reading simul8tor sensors");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override	
	public boolean getSensorEnabled(Sensor sensor) throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/sensor/" + sensor.getName() + "/enabled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readBool(json, sensor.getName() + "_enabled");
			}		
			throw new L8Exception("Error querying simul8tor sensor");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public boolean getBluetoothEnabled() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/bluetooth_enabled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readBool(json, "bluetooth_enabled");
			}		
			throw new L8Exception("Error querying simul8tor bluetooth");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public float getBatteryStatus() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/battery_status");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "battery_status");
			}		
			throw new L8Exception("Error reading simul8tor battery status");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public int getButton() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/button");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "button");
			}		
			throw new L8Exception("Error reading simul8tor button");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public int getMemorySize() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/memory_size");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "memory_size");
			}		
			throw new L8Exception("Error reading simul8tor memory size");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override
	public int getFreeMemory() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/free_memory");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "free_memory");
			}		
			throw new L8Exception("Error reading simul8tor free memory");
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	public String getID() throws L8Exception
	{
		return simul8torToken;
	}
	
	@Override
	public Version getVersion() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simul8torToken + "/version");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				int hardwareVersion = readInt(json, "hardware_version");
				int softwareVersion = readInt(json, "software_version");
				return new L8.Version(hardwareVersion, softwareVersion);
			}		
			throw new L8Exception("Error reading simul8tor version");
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setAnimation(L8.Animation animation) throws L8Exception
	{
		try {
			JSONObject message = new JSONObject();
			for (int i = 0; i < 8; i++) {
				if (i < animation.getFrames().size()) {
					L8.Frame frame = animation.getFrames().get(i);
					message.put("frame" + i, printMatrix(frame.getMatrix()));
					message.put("frame" + i + "_duration", frame.getDuration());
				} else {
					message.put("frame" + i, "");
					message.put("frame" + i + "_duration", 0);
				}
			}
			Response response = this.client.put("/l8s/" + this.simul8torToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error setting animation");
			}
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override
	public String getConnectionURL() throws L8Exception
	{
		return SIMUL8TOR_BASE_URL + "/l8s/" + getID();
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
