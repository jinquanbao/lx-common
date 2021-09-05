package com.laoxin.core.enums;


public enum CommonEnum {
    YES(1),
    NO(0),

    /**
     * 排序
     */
    DESC(-1),
    ASC(1),

    ;
    private final int value;

    CommonEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
