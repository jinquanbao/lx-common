package com.laoxin.dao.sql.entity;

import java.io.Serializable;
import java.time.LocalDateTime;


public interface BaseSqlEntity<T> extends Serializable {

	default T getId() {
		return null;
	}

	default void setId(T id) {
	}

	default LocalDateTime getCreateTime() {
		return null;
	}

	default void setCreateTime(LocalDateTime createTime) {

	}

	default LocalDateTime getUpdateTime() {
		return null;
	}

	default void setUpdateTime(LocalDateTime updateTime) {
	}

	default Integer getDeleted() {
		return null;
	}

	default void setDeleted(Integer deleted) {
	}
}
