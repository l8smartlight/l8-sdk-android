package es.develappers.rest;

public class Response 
{
	
	protected int code;
	protected Object JSON;
	
	public int getCode() 
	{
		return code;
	}
	
	public void setCode(int code) 
	{
		this.code = code;
	}
	
	public Object getJSON() 
	{
		return JSON;
	}
	
	public void setJSON(Object JSON) 
	{
		this.JSON = JSON;
	}
	
}
