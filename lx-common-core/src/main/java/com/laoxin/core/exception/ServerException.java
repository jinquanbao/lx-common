/*
 * Copyright (c) 2004-2015 by UCweb All rights reserved
 */
package com.laoxin.core.exception;




import com.laoxin.core.enums.ResultState;
import com.laoxin.core.enums.ResultStateEnum;

import java.text.MessageFormat;

public class ServerException extends IllegalArgumentException {

	/**
	 * 错误代码
	 */
	private String code;

	/**
	 * 错误信息具体内容
	 */
	private String msg;
	
	/**
	 * http 状态码
	 */
	private int httpCode = 200;


	public ServerException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ServerException(String msg, Throwable cause) {
		super(cause);
		this.code = ResultStateEnum.INTERNAL_ERROR.getCode();
		this.msg = msg;
	}

	public ServerException(String msg) {
		this(ResultStateEnum.INTERNAL_ERROR.getCode(), msg);
	}
	
	public ServerException(ResultState resultState) {
		this(resultState.getCode(), resultState.getMessage());
		this.httpCode = resultState.getHttpCode();
		formatMessageLanguage(resultState);
	}

	public ServerException(ResultState resultState, Object... args) {
		this(resultState);
		this.msg = MessageFormat.format(msg, args);
	}

	public ServerException(Throwable cause) {
		super(cause);
		this.code = ResultStateEnum.INTERNAL_ERROR.getCode();
		formatMessageLanguage(ResultStateEnum.INTERNAL_ERROR);
	}

	private void formatMessageLanguage(ResultState resultState){
		this.msg = resultState.getMessage();
	}


	public String getCode() {
		return code;
	}
	public int getIntCode() {
		return convertCode(code);
	}

	private int convertCode(String code){
		if(code == null || code == ""){
			return 0;
		}
		String[] split = code.split("-");
		if(split.length >1){
			code = split[1];
		}
		return Integer.parseInt(code);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	@Override
	public String getMessage(){
		return msg;
	}
}
