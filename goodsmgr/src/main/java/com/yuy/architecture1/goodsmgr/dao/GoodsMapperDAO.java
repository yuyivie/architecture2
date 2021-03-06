package com.yuy.architecture1.goodsmgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuy.architecture1.goodsmgr.vo.GoodsModel;
import com.yuy.architecture1.goodsmgr.vo.GoodsQueryModel;
@Repository
public interface GoodsMapperDAO extends GoodsDAO{
	public List<Integer>getIdsByConditionPage(GoodsQueryModel qm);
	public List<GoodsModel>getByIds(List ids);

}
