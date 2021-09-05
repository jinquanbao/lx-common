package com.laoxin.core.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeVO implements Tree<TreeVO> {

    private long id;

    private long parentId;

    private int sort;

    private String name;

    private List<TreeVO> childs;
}
