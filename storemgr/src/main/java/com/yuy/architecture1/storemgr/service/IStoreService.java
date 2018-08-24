package com.yuy.architecture1.storemgr.service;


import com.yuy.architecture1.common.service.IBaseService;

import com.yuy.architecture1.storemgr.vo.StoreModel;
import com.yuy.architecture1.storemgr.vo.StoreQueryModel;

public interface IStoreService extends IBaseService<StoreModel,StoreQueryModel>{
	public StoreModel getByGoodsUuid(int goodsUuid);

}

