package com.laoxin.dao.utils;


import com.laoxin.core.dto.OffsetPageDTO;
import com.laoxin.core.dto.PageDTO;
import com.laoxin.core.dto.PageQueryDTO;
import com.laoxin.core.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PageUtil {


    /**
     * 自定义sql分页查询 根据Offset分页
     * @author jinquanbao
     */
    public static <T> PageVO<T> listEntityByPage(Supplier<List<T>> supplier, OffsetPageDTO pageDTO) {
        com.github.pagehelper.Page<T> page = com.github.pagehelper.PageHelper.offsetPage(pageDTO.getOffset(), pageDTO.getPageSize(),pageDTO.isCount());
        if (!StringUtils.isEmpty(pageDTO.getOrderBy())) {
            com.github.pagehelper.PageHelper.orderBy(pageDTO.getOrderBy());
        }
        List<T> list = supplier.get();
        return new PageVO<>(page.getStartRow(),page.getPageSize(),page.getTotal(),list);
    }

    /***
     * 根据pageNum 分页
     * @author jinquanbao
     */
    public static <T> PageVO<T> listEntityByPage(Supplier<List<T>> supplier, PageDTO pageDTO) {
        com.github.pagehelper.Page<T> page = com.github.pagehelper.PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize(),pageDTO.isCount());
        if (!StringUtils.isEmpty(pageDTO.getOrderBy())) {
            com.github.pagehelper.PageHelper.orderBy(pageDTO.getOrderBy());
        }
        List<T> list = supplier.get();
        return new PageVO<>(page.getStartRow(),page.getPageSize(),page.getTotal(),list);
    }

    /**
     * 自定义sql分页查询
     * @author jinquanbao
     */
    public static <T> PageVO<T> listEntityByPage(Supplier<List<T>> supplier, int offset, int pageSize, String orderBy) {
        return listEntityByPage(supplier,new PageQueryDTO(offset,pageSize,orderBy));
    }

    /**
     * 获取分页结果，不查询count
     * @author jinquanbao
     */
    public static <T> List<T> listEntityByLimit(Supplier<List<T>> supplier,int offset, int limit) {
        com.github.pagehelper.Page<T> page = com.github.pagehelper.PageHelper.offsetPage(offset, limit,false);
        return  supplier.get();
    }

    /**
     * 查询单个结果
     * @author jinquanbao
     */
    public static <T> T selectOne(Supplier<List<T>> supplier) {
        List<T> ts = listEntityByLimit(supplier, 0, 1);
        if(CollectionUtils.isEmpty(ts)){
            return null;
        }
        return  ts.get(0);
    }

    /**
     * 分页参数属性转换
     * @author jinquanbao
     */
    public static <T, R> PageVO<R> convertPage(PageVO<T> srcPage, Class<R> clazz) {
        if(srcPage == null){
            return null;
        }
        List<R> rs = convertPage(srcPage.getList(), t -> {
            R r = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(t, r);
            return r;
        });
        return new PageVO<>(srcPage.getOffset(),srcPage.getPageSize(),srcPage.getTotal(),rs);
    }

    private static <T,R> List<R> convertPage(List<T> srcRows ,Function<? super T, ? extends R> mapper) {
        if(CollectionUtils.isEmpty(srcRows)){
            return new ArrayList<>();
        }
        return srcRows.stream().map(mapper).collect(Collectors.toList());
    }

}
