package com.laoxin.core.dto;

public interface PageDTO {



    default int getPageNum(){
        return 1;
    }

    default int getPageSize() {
        return 10;
    }

    default String getOrderBy(){
        return "";
    }

    default boolean isCount(){ return true; }
}
