package com.laoxin.core.vo;

import com.laoxin.core.enums.ResultState;
import com.laoxin.core.enums.ResultStateEnum;
import com.laoxin.core.exception.ServerException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

@Data
public class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty("返回编码，0表示成功，其他表示失败")
    private String code;

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    public ResponseVO(){
        this.code = ResultStateEnum.SUCCESS.getCode();
        this.msg = ResultStateEnum.SUCCESS.getMessage();
    }

    public ResponseVO(T data){
        this.code = ResultStateEnum.SUCCESS.getCode();
        this.msg = ResultStateEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public ResponseVO(ResultState resultState){
        this.code = resultState.getCode();
        this.msg = resultState.getMessage();
    }

    public ResponseVO(String code,String message){
        this.code = code;
        this.msg = message;
    }

    public ResponseVO(Exception e){
        this(ResultStateEnum.INTERNAL_ERROR);
        if(e instanceof ServerException){
            this.code = ((ServerException) e).getCode();
            this.msg = ((ServerException) e).getMsg();
        }
    }


    public static ResponseVO ok(){
        return new ResponseVO();
    }

    public static <T> ResponseVO ok(T data){
        return new ResponseVO(data);
    }

    public static ResponseVO error(ResultState resultState){
        return new ResponseVO(resultState);
    }

    public static ResponseVO error(String code,String message){
        return new ResponseVO(code,message);
    }

    public static ResponseVO error(Exception e){
        ResponseVO vo = new ResponseVO(e);
        if( !(e instanceof ServerException)){
            //vo.setData(stackTrace2String(e));
        }
        return vo;
    }

    private static String stackTrace2String(Throwable e) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(10);
        PrintStream ps = new PrintStream(os);
        e.printStackTrace(ps);
        return os.toString();
    }
}
