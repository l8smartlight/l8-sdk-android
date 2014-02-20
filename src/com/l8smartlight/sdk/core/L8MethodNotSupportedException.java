package com.l8smartlight.sdk.core;

public class L8MethodNotSupportedException extends L8Exception {

	private static final long serialVersionUID = -5645937191663519946L;

	public L8MethodNotSupportedException() 
    {
        super("");
    }	
    
    public L8MethodNotSupportedException(Throwable cause) 
    {

        super("", cause);
    }

    protected L8MethodNotSupportedException(Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
    {
        super("", cause, enableSuppression, writableStackTrace);
    }
	
}
