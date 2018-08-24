package com.yuy.architecture1.storemgr.dao;

import org.springframework.stereotype.Repository;
import com.yuy.architecture1.common.dao.BaseDAO;

import com.yuy.architecture1.storemgr.vo.StoreModel;
import com.yuy.architecture1.storemgr.vo.StoreQueryModel;

@Repository
public interface StoreDAO extends BaseDAO<StoreModel,StoreQueryModel>{
	public StoreModel getByGoodsUuid(int goodsUuid);
}
