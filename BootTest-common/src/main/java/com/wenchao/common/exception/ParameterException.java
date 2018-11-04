package com.wenchao.common.exception;

public class ParameterException extends RuntimeException{

	 private final String errormsg;

    public ParameterException(String errormsg) {
        super("");
        this.errormsg = errormsg;
    }
    

    @Override
    public String getMessage() {
    	 return " parameterErrorMsg: "  + errormsg;
    }

 

	public String getParameterErrorMsg() {
		return errormsg;
	}

 
}
