package com.l8smartlight.sdk.android.bluetooth;

import com.l8smartlight.sdk.core.Color;

public class RLPCommand
{
	
	public static final int HEADER_MSB  = 0xAA;
	public static final int HEADER_LSB  = 0x55;
	
	public static final byte CMD_LED_SET 			= 0x43;
	public static final byte CMD_MATRIX_SET		  	= 0x44;
	public static final byte CMD_MATRIX_OFF		  	= 0x45;
	
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
	
	public static byte[] BuildLedSet(byte x, byte y, Color color, BluetoothL8.L8Mode mode)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[] = new byte[10];
		cmd[0] = (byte)HEADER_MSB;
		cmd[1] = (byte)HEADER_LSB;
		cmd[2] = 1 + 5;
		cmd[3] = CMD_LED_SET;
		cmd[4] = x;
		cmd[5] = y;
		
		if(mode == BluetoothL8.L8Mode.L8_MODE_4BIT)
		{
			cmd[6] = (byte)((color.getBlue()>>4)&0x0f);
			cmd[7] = (byte)((color.getGreen()>>4)&0x0f);
			cmd[8] = (byte)((color.getRed()>>4)&0x0f);
		}
		else if(mode == BluetoothL8.L8Mode.L8_MODE_8BIT)
		{
			cmd[6] = (byte)((color.getBlue())&0x0f);
			cmd[7] = (byte)((color.getGreen())&0x0f);
			cmd[8] = (byte)((color.getRed())&0x0f);
		}

		cmd[9] = (byte)m_crc.calc(cmd,3,9);
		return cmd;
	}
	
	public static  byte[] BuildMatrixSet(Color[][] matrix, int rows, int columns)
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
	
	public static  byte[] BuildMatrixSet(Color[][] matrix, int rows, int columns, BluetoothL8.L8Mode mode)
	{
		CRC8Table m_crc = new CRC8Table(0x07);
		byte cmd[];
		int payload_length = 0;
		
		if(mode == BluetoothL8.L8Mode.L8_MODE_4BIT)
			payload_length = rows*columns*2;
		else if(mode == BluetoothL8.L8Mode.L8_MODE_8BIT)
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
				if(mode == BluetoothL8.L8Mode.L8_MODE_8BIT)
				{
					cmd[index++] = (byte)matrix[i][j].getBlue();
					cmd[index++] = (byte)matrix[i][j].getGreen();
					cmd[index++] = (byte)matrix[i][j].getRed();
				}
				else if(mode == BluetoothL8.L8Mode.L8_MODE_4BIT)
				{
					cmd[index++] = (byte)((matrix[i][j].getBlue()>>4)&0x0f);
					cmd[index++] = (byte) ((byte)((matrix[i][j].getGreen())&0xf0) |  (byte)((matrix[i][j].getRed()>>4)&0x0f));
				}
			}
		
		cmd[index] = (byte)m_crc.calc(cmd,3,((4 + payload_length + 1)-1));
		return cmd;
	}
	
	public static  byte[] BuildMatrixClear()
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
}
