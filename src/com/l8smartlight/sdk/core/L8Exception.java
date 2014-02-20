package com.l8smartlight.sdk.core;

public class L8Exception extends Exception 
{

	private static final long serialVersionUID = 2985140094956828434L;
	
    public L8Exception(String message) 
    {
        super(message);
    }	
    
    public L8Exception(String message, Throwable cause) 
    {

        super(message, cause);
    }

    public L8Exception(Throwable cause) 
    {
        super(cause);
    }

    protected L8Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
    {
        //super(message, cause, enableSuppression, writableStackTrace);
        super(message, cause);
    }
	
}
