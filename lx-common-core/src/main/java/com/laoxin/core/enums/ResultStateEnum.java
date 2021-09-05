/*
 * Copyright (c) 2004-2014 by UCweb All rights reserved
 */
package com.laoxin.core.enums;

/**
 * 错误码格式：字母+五位数字
 * 前面字母服务名简写
 * 前2位数字为服务编码
 * 前2-4位数字为模块序号
 * 4-6位数字为模块下的错误码序号
 * @author jinquanbao
 */
public enum ResultStateEnum implements ResultState{

	// 操作成功
	SUCCESS("0", "success", 200),

	//系统错误码
	INTERNAL_ERROR("common-100000", "请求处理失败，未知错误", 500),
	AUTH_FAILED("common-100001", "鉴权失败", 401),
	AUTH_FORBIDDEN("common-100003", "授权失败,禁止访问", 403),
	OPERATION_FORBIDDEN("common-100004", "操作越权", 403),
	METHOD_NOT_ALLOWED("common-100005", "method not allowed: {0}", 405),
	OPERATION_DB_FAILED("common-100010", "数据库操作失败", 500),
	OPERATION_BUSY_FAILED("common-100011", "其他用户正在操作，请稍后重试...", 200),
	TIME_FORMAT_ERROR("common-100012", "日期{0}与格式{1}不符", 200),

	//参数错误码
	PARAMETER_EMPTY("common-100100", "请求参数{0}为空", 200),
	INVALID_PARAMETER("common-100102", "请求参数错误，错误信息：{0}", 200),
	RESOURCE_NOT_EXIST("common-100103", "资源不存在,id={0}", 200),
	RESOURCE_ALREADY_EXIST("common-100104", "此{0}已存在", 200),

	//rpc error
	RPC_SERVER_ERROR("common-100200","调用{0}服务api:{1}发生异常,异常原因:{2}",200),


	;
	private String code;

	private String message;

	private int httpCode;

	ResultStateEnum(String code, String message, int httpCode) {
		this.code = code;
		this.message = message;
		this.httpCode = httpCode;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getHttpCode() {
		return httpCode;
	}

	@Override
	public String toString() {
		return this.code + "_" + this.message ;
	}

}
