package com.laoxin.core.dto;

public interface OffsetPageDTO extends PageDTO{

    default int getOffset(){
        return 0;
    }

}
