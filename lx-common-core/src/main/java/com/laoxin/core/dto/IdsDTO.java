package com.laoxin.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class IdsDTO {

    @ApiModelProperty(value = "ids")
    @NotEmpty(message = "ids不能为空")
    private List<Long> ids;
}
