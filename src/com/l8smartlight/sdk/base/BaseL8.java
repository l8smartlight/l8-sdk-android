package com.l8smartlight.sdk.base;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.l8smartlight.sdk.core.Color;
import com.l8smartlight.sdk.core.L8;
import com.l8smartlight.sdk.core.L8Exception;

public abstract class BaseL8 implements L8 {

	@Override
	public void setMatrix(String image) throws L8Exception 
	{
		setMatrix(Color.matrixFromString(image));
	}
	
	@Override
	public void setSuperLED(String image) throws L8Exception 
	{
		setSuperLED(Color.backLedFromString(image));
	}
	
	@Override
	public void setAnimation(JSONArray jsonFrames) throws L8Exception 
	{
		List<L8.Frame> frames = new ArrayList<L8.Frame>();
		for (int i = 0; i < jsonFrames.size(); i++) {
			JSONObject jsonFrame = (JSONObject)jsonFrames.get(i);
			String strDuration = (String)jsonFrame.get("duration");
			Integer duration = Integer.valueOf(strDuration);
			String image = (String)jsonFrame.get("image");
			L8.Frame frame = new L8.Frame(Color.matrixFromString(image), duration);
			frame.setBackLed(Color.backLedFromString(image));
			frames.add(frame);
		}
		L8.Animation animation = new L8.Animation(frames);
		setAnimation(animation);
	}
	
}
