package com.laoxin.core.dto;

import io.swagger.annotations.ApiModelProperty;

public class PageQueryDTO implements OffsetPageDTO{

    @ApiModelProperty(value = "分页偏移量")
    private int offset;

    @ApiModelProperty(value = "每页条数")
    private int pageSize;

    @ApiModelProperty(value = "排序",hidden = true)
    private String orderBy;

    @ApiModelProperty(value = "是否统计总数")
    private boolean count;

    public PageQueryDTO() {
    }

    public PageQueryDTO(int offset, int pageSize, String orderBy) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }
}
