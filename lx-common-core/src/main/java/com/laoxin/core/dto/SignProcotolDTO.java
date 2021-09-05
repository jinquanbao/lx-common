package com.laoxin.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class SignProcotolDTO {

    @ApiModelProperty(value = "appid",required = true,example = "1")
    @NotEmpty(message = "appid不能为空")
    private String appid;

    @ApiModelProperty(value = "时间戳",required = true,example = "1606723009421")
    private long timestamp;

    @NotBlank(message = "noncestr 不能为空")
    @ApiModelProperty(value = "随机字符串",required = true,example = "vuzpYURSNS6rXKbI")
    private String noncestr;

    @NotBlank(message = "sign 不能为空")
    @ApiModelProperty(value = "签名",required = true,example = "eb3dc5d161ee50")
    private String sign;
}
