package com.laoxin.core.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.laoxin.core.enums.CommonEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class AbstractOffsetPageDTO implements OffsetPageDTO {

    @ApiModelProperty(value = "分页偏移量,默认0")
    private int offset;

    @JsonAlias(value={"pageSize","size"})
    @ApiModelProperty(value = "每页条数,默认10",example = "10")
    private int pageSize;

    @ApiModelProperty(value = "是否统计总数 1-统计总数，其他不统计;默认0",example = "0")
    private int count = CommonEnum.NO.getValue();


    @Override
    public boolean isCount() {
        return count == CommonEnum.YES.getValue()?true:false;
    }
}
