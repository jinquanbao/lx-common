package com.laoxin.core.vo;

import java.util.List;

public interface Tree<T> {

    long getId();

    long getParentId() ;

    int getSort() ;

    void setChilds(List<T> t);

}
