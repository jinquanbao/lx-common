package com.laoxin.dao.sql.dao;

import com.laoxin.core.dto.PageDTO;
import com.laoxin.core.vo.PageVO;
import com.laoxin.dao.sql.entity.BaseSqlEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/***
 * 基于mybatis-plus之上的 自定义公共操作基类
 * @author jinquanbao
 */
public interface BasePlusDao<T extends BaseSqlEntity> extends IService<T> {

    boolean insert(T entity);

    List<T> insertList(List<T> entityList, int rows);

    /***
     * 分页列表
     */
    PageVO<T> listEntityByPage(Wrapper<T> queryWrapper, PageDTO pageDTO);

    /***
     * 批量逻辑删除
     * @author jinquanbao
     */
    boolean updateDeleteByIds(Collection<? extends Serializable> ids);


    /***
     * 批量更新
     * @author jinquanbao
     */
    boolean updateByIds(T entity,Collection<? extends Serializable> ids);


    /***
     * 根据ids查询，组装成Map key->id,value->valueMapper
     * @author jinquanbao
     */
    <U> Map<?,U> listByIds(Collection<? extends Serializable> ids, Function<? super T, ? extends U> valueMapper);
}
