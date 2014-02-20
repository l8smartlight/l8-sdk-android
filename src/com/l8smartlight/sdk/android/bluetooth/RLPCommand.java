package com.l8smartlight.sdk.android.bluetooth;

import com.l8smartlight.sdk.android.Util;
import com.l8smartlight.sdk.core.Color;

public class RLPCommand
{
	
	public static final int HEADER_MSB  = 0xAA;
	public static final int HEADER_LSB  = 0x55;
	
	public static final byte CMD_LED_SET 				= 0x43;
	public static final byte CMD_MATRIX_SET		  		= 0x44;
	public static final byte CMD_MATRIX_OFF		  		= 0x45;
	public static final byte CMD_READ_BATTERY	  		= 0x46;
	public static final byte READ_BATTERY_RESULT		= 0x47;
	public static final byte CMD_READ_TEMPERATURE	  	= 0x48;
	public static final byte READ_TEMPERATURE_RESULT	= 0x49;
	public static final byte CMD_BACKLED_SET	  		= 0x4b;
	public static final byte CMD_READ_ACCELERATION	  	= 0x4c;
	public static final byte READ_ACCELERATION_RESULT	= 0x4d;
	public static final byte CMD_READ_AMBIENTLIGHT	  	= 0x50;
	public static final byte READ_AMBIENTLIGHT_RESULT	= 0x51;
	public static final byte CMD_READ_PROXIMITY	  		= 0x52;
	public static final byte READ_PROXIMITY_RESULT		= 0x53;
	public static final byte CMD_READ_NOISE		  		= 0x64;
	public static final byte READ_NOISE_RESULT			= 0x65;
	
	public static final byte CMD_RUN_APP			= (byte) 0x81;
	public static final byte CMD_STOP_APP			= (byte) 0x82;
	
	public static final byte CMD_SHUT_DOWN_L8		= (byte) 0x9D;
	
	public static final byte APP_DICE			= (byte) 0x00;
	public static final byte APP_PARTY			= (byte) 0x01;
	public static final byte APP_LIGHT_MODE		= (byte) 0x02;
	public static final byte APP_LUMINOSITY_AND_PROXIMITY	= (byte) 0x03;
	
	public static final byte CMD_L8_SET_BRIGHTNESS	= (byte) 0x9A;
	public static final byte BRIGHTNESS_LOW	        = (byte) 0x01;
	public static final byte BRIGHTNESS_NORMAL	    = (byte) 0x00;
	
	public static final byte CMD_L8_SET_NOTIFICATION 	= (byte) 0x99;
	
	public static final byte CMD_L8_SET_TEXT 	= (byte) 0x83;

	
	
	public static byte[] BuildLedSet(byte x, byte y, Color color)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[10];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1 + 5;
		cmd[3] = CMD_LED_SET;
		cmd[4] = x;
		cmd[5] = y;
		cmd[6] = (byte)((color.getBlue()>>4)&0x0f);
		cmd[7] = (byte)((color.getGreen()>>4)&0x0f);
		cmd[8] = (byte)((color.getRed()>>4)&0x0f);
		cmd[9] = (byte)m_crc.calc(cmd,3,9);
		return cmd;
	}
	
	public static byte[] BuildLedSet(byte x, byte y, Color color, AndroidBluetoothL8.L8Mode mode)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[10];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1 + 5;
		cmd[3] = CMD_LED_SET;
		cmd[4] = x;
		cmd[5] = y;
		
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[6] = (byte)((color.getBlue()>>4)&0x0f);
			cmd[7] = (byte)((color.getGreen()>>4)&0x0f);
			cmd[8] = (byte)((color.getRed()>>4)&0x0f);
		}
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[6] = (byte)((color.getBlue())&0x0f);
			cmd[7] = (byte)((color.getGreen())&0x0f);
			cmd[8] = (byte)((color.getRed())&0x0f);
		}

		cmd[9] = (byte)m_crc.calc(cmd,3,9);
		return cmd;
	}
	
	public static byte[] BuildMatrixSet(Color[][] matrix, int rows, int columns)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[4 + rows*columns*2  + 1];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = (byte)(1 + rows*columns*2);
		cmd[3] = CMD_MATRIX_SET;
		
		int index = 4;
		
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				cmd[index++] = (byte)((matrix[i][j].getBlue()>>4)&0x0f);
				cmd[index++] = (byte) ((byte)((matrix[i][j].getGreen())&0xf0) |  (byte)((matrix[i][j].getRed()>>4)&0x0f));
			}
		
		cmd[index] = (byte)m_crc.calc(cmd,3,((4 + rows*columns*2  + 1)-1));
		return cmd;
	}
	
	public static byte[] BuildMatrixSet(Color[][] matrix, int rows, int columns, AndroidBluetoothL8.L8Mode mode)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[];
		int payload_length = 0;
		
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
			payload_length = rows*columns*2;
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
			payload_length = rows*columns*3;
		else
			return null;
			
		cmd = new byte[4 + payload_length  + 1];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = (byte)(1 + payload_length);
		cmd[3] = CMD_MATRIX_SET;
		
		int index = 4;
		
		for(int i=0;i<rows;i++)
			for(int j=0;j<columns;j++)
			{
				if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
				{
					cmd[index++] = (byte)matrix[i][j].getBlue();
					cmd[index++] = (byte)matrix[i][j].getGreen();
					cmd[index++] = (byte)matrix[i][j].getRed();
				}
				else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
				{
					cmd[index++] = (byte)((matrix[i][j].getBlue()>>4)&0x0f);
					cmd[index++] = (byte) ((byte)((matrix[i][j].getGreen())&0xf0) |  (byte)((matrix[i][j].getRed()>>4)&0x0f));
				}
			}
		
		cmd[index] = (byte)m_crc.calc(cmd,3,((4 + payload_length + 1)-1));
		return cmd;
	}
	
	public static byte[] BuildMatrixClear()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_MATRIX_OFF;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
	public static byte[] BuildBackledSet(Color color, AndroidBluetoothL8.L8Mode mode)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[8];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 4;
		cmd[3] = CMD_BACKLED_SET;
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[4] = (byte)((color.getBlue()>>4)&0x0f);
			cmd[5] = (byte)((color.getGreen()>>4)&0x0f);
			cmd[6] = (byte)((color.getRed()>>4)&0x0f);
		}
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[4] = (byte)((color.getBlue())&0x0f);
			cmd[5] = (byte)((color.getGreen())&0x0f);
			cmd[6] = (byte)((color.getRed())&0x0f);
		}
		cmd[7] = (byte)m_crc.calc(cmd,3,7);
		return cmd;
	}
	
	public static byte[] BuildReadBattery()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_BATTERY;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
	public static byte[] BuildReadTemperature()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_TEMPERATURE;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
	public static byte[] BuildReadAcceleration()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_ACCELERATION;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
	public static byte[] BuildReadAmbientLight()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_AMBIENTLIGHT;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	public static byte[] BuildReadProximity()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_PROXIMITY;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	public static byte[] BuildReadNoise()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_READ_NOISE;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
	
	//a–adiendo mas metodos para construir nuevos mensajes..
	
	public static byte[] BuildStopCurrentL8App ()
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_STOP_APP;
		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
		
		
	}
	
	
	public static byte[] BuildRunL8appDice(Color color,AndroidBluetoothL8.L8Mode mode )
	{
		//AA 55 05 81 00 00 0F 00
		//System.out.println("dentro de BuildRunL8appDice");
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[9];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 5;
		cmd[3] = CMD_RUN_APP;
		cmd[4] = APP_DICE;
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[5] = (byte)((color.getBlue()>>4)&0x0f);
			cmd[6] = (byte)((color.getGreen()>>4)&0x0f);
			cmd[7] = (byte)((color.getRed()>>4)&0x0f);
		}
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[5] = (byte)((color.getBlue())&0x0f);
			cmd[6] = (byte)((color.getGreen())&0x0f);
			cmd[7] = (byte)((color.getRed())&0x0f);
		}
		cmd[8] = (byte)m_crc.calc(cmd,3,8);
		return cmd;
	}
	
	public static byte[] BuildBrightLevel(byte brightLevel ){
		
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[6];
		
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 2;
		cmd[3] = CMD_L8_SET_BRIGHTNESS;
		cmd[4] = brightLevel;
		cmd[5] = (byte)m_crc.calc(cmd,3,5);
		return cmd;
		
	}

	public static byte[] BuildNotificationPosted(String bundle,
			byte eventNotificationID, byte categoryNotificationID) {
		
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[];
		
		if (bundle == null){ //incoming call there is no bundle  99-00-00-01
			cmd = new byte[8];
			
			cmd[0] = (byte)HEADER_MSB;
			cmd[1] = (byte)HEADER_LSB;
			cmd[2] = 4;	//lenght payload
			cmd[3] = CMD_L8_SET_NOTIFICATION;
			cmd[4] = 0;
			cmd[5] = eventNotificationID;
			cmd[6] = categoryNotificationID;
			cmd[7] = (byte)m_crc.calc(cmd,3,7);
		}
		else{
			int lenghtBundle=bundle.length();
			
			int payloadLength= lenghtBundle+1+2+1;
		    cmd = new byte[4+payloadLength+1];
			
			cmd[0] = (byte)HEADER_MSB;
			cmd[1] = (byte)HEADER_LSB;
			cmd[2] = (byte)payloadLength;
			cmd[3] = CMD_L8_SET_NOTIFICATION;
			cmd[4] = (byte)lenghtBundle;
			for (int i=0;i<lenghtBundle;i++){
				cmd[5+i] =bundle.getBytes()[i];	
			}
			
			cmd[5+lenghtBundle] = eventNotificationID;
			cmd[5+lenghtBundle+1] = categoryNotificationID;
			cmd[5+lenghtBundle+2] = (byte)m_crc.calc(cmd,3,5+lenghtBundle+2);
		}
		
		
		return cmd;
	}

	public static byte[] BuildTexttoL8(String text,byte loop, Color color, byte speed, AndroidBluetoothL8.L8Mode mode){

		
		CRC8Table m_crc = new CRC8Table(0x07);
		int lenghtText=text.length();
		
		int payloadLength= lenghtText+6;
		byte cmd[] = new byte[4+payloadLength+1];
		
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = (byte)payloadLength;
		cmd[3] = CMD_L8_SET_TEXT;
		cmd[4] = loop;
		cmd[5] = speed;
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[5] = (byte)((color.getBlue()>>4)&0x0f);
			cmd[6] = (byte)((color.getGreen()>>4)&0x0f);
			cmd[7] = (byte)((color.getRed()>>4)&0x0f);
		}
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[5] = (byte)((color.getBlue())&0x0f);
			cmd[6] = (byte)((color.getGreen())&0x0f);
			cmd[7] = (byte)((color.getRed())&0x0f);
		}
		for (int i=0;i<lenghtText;i++){
			cmd[8+i] =text.getBytes()[i];	
		}
		cmd[8+lenghtText] =(byte)m_crc.calc(cmd,3,8+lenghtText);
		
		return cmd;
	}
	
	public static byte[] BuildRunL8appPartyMode(){
		
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[6];
		
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 2;
		cmd[3] = CMD_RUN_APP;
		cmd[4] = APP_PARTY;
		cmd[5] = (byte)m_crc.calc(cmd,3,5);
		return cmd;
		
	}


	public static byte[] BuildRunL8AppLuminosityAndProximity(byte sensor,
			Color colorMatrix, Color colorBackLed, byte threshold,AndroidBluetoothL8.L8Mode mode) {

		//AA 55 05 81 08 0F 0F 0F 0F 0F 0F 40 00
			
		//idx=3 / Front color BGR / Back color BGR / Threshold (1B) 1/125 / Sensor=0,1
		
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[14];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 10;
		cmd[3] = CMD_RUN_APP;
		cmd[4] = APP_LUMINOSITY_AND_PROXIMITY;
		if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[5] = (byte)((colorMatrix.getBlue()>>4)&0x0f);
			cmd[6] = (byte)((colorMatrix.getGreen()>>4)&0x0f);
			cmd[7] = (byte)((colorMatrix.getRed()>>4)&0x0f);
			
			cmd[8] = (byte)((colorBackLed.getBlue()>>4)&0x0f);
			cmd[9] = (byte)((colorBackLed.getGreen()>>4)&0x0f);
			cmd[10] = (byte)((colorBackLed.getRed()>>4)&0x0f);
			
		}
		else if(mode == AndroidBluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[5] = (byte)((colorMatrix.getBlue())&0x0f);
			cmd[6] = (byte)((colorMatrix.getGreen())&0x0f);
			cmd[7] = (byte)((colorMatrix.getRed())&0x0f);
			
			cmd[8] = (byte)((colorBackLed.getBlue())&0x0f);
			cmd[9] = (byte)((colorBackLed.getGreen())&0x0f);
			cmd[10] = (byte)((colorBackLed.getRed())&0x0f);
		}
		cmd[11] = threshold;
		//cmd[11] = (byte)0x40;
		cmd[12] = sensor;
		cmd[13] = (byte)m_crc.calc(cmd,3,13);
	//	Util.error("BYTES WRITE >>>>>>: " + Util.bytesToHex(cmd.length, cmd));	
		return cmd;
	}
	
	public static byte[] BuildRunL8AppLight(byte colorMode,
			byte speed, byte backLedInverted) {

		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[9];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 5;
		cmd[3] = CMD_RUN_APP;
		cmd[4] = APP_LIGHT_MODE;
		cmd[5] = colorMode;
		cmd[6] = speed;
		cmd[7] = backLedInverted;

		cmd[8] = (byte)m_crc.calc(cmd,3,8);
		return cmd;
	}

	public static byte[] BuildShutDown() {
		
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[5];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1;
		cmd[3] = CMD_SHUT_DOWN_L8;


		cmd[4] = (byte)m_crc.calc(cmd,3,4);
		return cmd;
	}
	
}
