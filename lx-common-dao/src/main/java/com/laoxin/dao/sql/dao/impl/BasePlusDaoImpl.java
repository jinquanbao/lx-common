package com.laoxin.dao.sql.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laoxin.core.dto.OffsetPageDTO;
import com.laoxin.core.dto.PageDTO;
import com.laoxin.core.enums.CommonEnum;
import com.laoxin.core.enums.ResultStateEnum;
import com.laoxin.core.exception.ServerException;
import com.laoxin.core.vo.PageVO;
import com.laoxin.dao.sql.dao.BasePlusDao;
import com.laoxin.dao.sql.entity.BaseSqlEntity;
import com.laoxin.dao.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasePlusDaoImpl<T extends BaseSqlEntity> extends ServiceImpl<BaseMapper<T>, T> implements BasePlusDao<T> {


    protected void assembleEntity(BaseSqlEntity entity){
        if(entity != null){
            if (entity.getDeleted() == null) {
                entity.setDeleted(0);
            }
            if (entity.getCreateTime() == null) {
                entity.setCreateTime(LocalDateTime.now());
            }
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(entity.getCreateTime());
            }
        }
    }

    @Override
    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }

    @Override
    public boolean insert(T entity) {
        if(entity == null){
            throw new RuntimeException("entity为空");
        }
        assembleEntity(entity);
        return save((T)entity);
    }

    @Override
    public List<T> insertList(List<T> entitys, int rows) {
        if(CollectionUtils.isEmpty(entitys)){
            return entitys;
        }
        for (int i = 0, n = entitys.size(); i < n; i++) {
            T t = entitys.get(i);
            assembleEntity(t);

        }
        saveBatch(entitys,rows);
        return entitys;
    }

    @Override
    public PageVO<T> listEntityByPage(Wrapper<T> queryWrapper, PageDTO pageDTO) {
        if(pageDTO instanceof OffsetPageDTO){
            return PageUtil.listEntityByPage(()->getBaseMapper().selectList(queryWrapper),(OffsetPageDTO)pageDTO);
        }
        return PageUtil.listEntityByPage(()->getBaseMapper().selectList(queryWrapper),pageDTO);
    }

    @Override
    public boolean updateDeleteByIds(Collection<? extends Serializable> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("ids is empty");
            throw new ServerException(ResultStateEnum.INVALID_PARAMETER,"ids为空");
        }
        LambdaUpdateWrapper<T> wrapper = Wrappers.lambdaUpdate(getEntityClass());
        wrapper.in(T::getId,ids);

        T entity = BeanUtils.instantiateClass(getEntityClass());
        entity.setDeleted(CommonEnum.YES.getValue());
        entity.setUpdateTime(LocalDateTime.now());
        return super.update(entity,wrapper);
    }

    @Override
    public boolean updateByIds(T entity, Collection<? extends Serializable> ids) {
        if(CollectionUtils.isEmpty(ids) || entity == null){
            log.error("ids is empty or entity is null");
            throw new ServerException(ResultStateEnum.INVALID_PARAMETER,"ids 或者 实体为空");
        }
        LambdaUpdateWrapper<T> wrapper = Wrappers.lambdaUpdate(getEntityClass());
        wrapper.in(T::getId,ids);
        return super.update(entity,wrapper);
    }

    @Override
    public <U> Map<?, U> listByIds(Collection<? extends Serializable> ids, Function<? super T, ? extends U> valueMapper) {
        Map<?,U> result = new HashMap<>();
        List<T> ts = listByIds(ids);
        if(ts == null || ts.size() == 0){
            return result;
        }
        result = ts.stream().collect(Collectors.toMap(T::getId,valueMapper));
        return result;
    }
}
