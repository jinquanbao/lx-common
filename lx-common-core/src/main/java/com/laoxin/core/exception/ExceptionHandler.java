package com.laoxin.core.exception;

import com.laoxin.core.enums.ResultStateEnum;
import com.laoxin.core.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 异常处理器
 * @author jinquanbao
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(ServerException.class)
	public ResponseVO handleException(HttpServletRequest request, HttpServletResponse response, ServerException e){
		log.warn("[请求URI{}->]运行时发生自定义已知异常code:{},msg:{}",request.getRequestURI() ,e.getCode(), e.getMsg());
		response.setStatus(e.getHttpCode());
		return ResponseVO.error(e);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseVO handleException(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e){
		log.error("[请求URI{}->]method not allowed",request.getRequestURI() ,e);
		response.setStatus(405);
		return ResponseVO.error(new ServerException(ResultStateEnum.METHOD_NOT_ALLOWED, e.getMessage()));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseVO<?> handleException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
		List<String> errorMessages = e.getBindingResult().getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		String message = String.join(",", errorMessages);
		return handleException(request,response, new ServerException(ResultStateEnum.INVALID_PARAMETER,message));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseVO handleException(HttpServletRequest request, HttpServletResponse response,Exception e){
		log.error("[请求URI{}->]运行时发生未知异常",request.getRequestURI() ,e);
		response.setStatus(ResultStateEnum.INTERNAL_ERROR.getHttpCode());
		return ResponseVO.error(e);
	}

}
