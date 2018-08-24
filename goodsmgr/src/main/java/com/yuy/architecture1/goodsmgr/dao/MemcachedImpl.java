package com.yuy.architecture1.goodsmgr.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
import com.yuy.architecture1.goodsmgr.vo.GoodsModel;
import com.yuy.architecture1.goodsmgr.vo.GoodsQueryModel;

@Service
@Primary//优先注入   如果不想使用缓存 则可以吧 @Primary 放到 GoodsMapperDAO类上
public class MemcachedImpl implements GoodsDAO{
	private final String MEM_PRE = "Goods";
	@Autowired
	private GoodsMapperDAO mapper = null;
	
	@Autowired
	private MemCachedClient mcc;
	
	@Override
	public void create(GoodsModel m) {
		mapper.create(m);
	}

	@Override
	public void update(GoodsModel m) {
		mapper.update(m);
		 //查询缓存里面是否有这条数据有的话  需要更新缓存
		volatile Object obj =  mcc.get(MEM_PRE + m.getUuid());
		if(obj != null ){
			mcc.set(MEM_PRE + m.getUuid(), m);
		}
	}

	@Override
	public void delete(int uuid) {
		mapper.delete(uuid);
		//查询缓存里面是否有这条数据，有的话 需从缓存中删除
		Object obj =  mcc.get(MEM_PRE + uuid);
		if(obj != null ){
			mcc.delete(MEM_PRE + uuid);
		}
	}

	@Override
	public GoodsModel getByUuid(int uuid) {
		GoodsModel gm = null;
		//1:查缓存，如果有就从缓存中取值并返回
		Object obj =  mcc.get(MEM_PRE + uuid);
		if(obj != null ){
			gm = (GoodsModel) obj;
			return gm;
		}
		//2:缓存没有，从db中取
		gm = mapper.getByUuid(uuid);
		//3：把数据设置到缓存中
		mcc.set(MEM_PRE + uuid,gm);
		
		return gm;
		
		
	}

	@Override
	public List<GoodsModel> getByConditionPage(GoodsQueryModel qm) {
		
		//1:根据条件查询出所有的ids
	    List<Integer> ids = mapper.getIdsByConditionPage(qm);
	   
	    //2:在内存中找这些id对应的对象
		List<GoodsModel> listGm1 = new ArrayList<GoodsModel>();
	    List<Integer> noIdsList = new ArrayList<Integer>();
	    for (Integer id : ids) {
	    	Object obj =  mcc.get(MEM_PRE + id);
	    	if(obj != null){
	    		GoodsModel gm = (GoodsModel) obj;
	    		listGm1.add(gm);
	    	}else{
	    		noIdsList.add(id);
	    	}
	    	
		}
	   
	    
		//3:内存中找不到对应的id,从数据库里面获取，并设置到缓存中
	    List<GoodsModel> listGm2 = null;
	    System.out.println("noIdsList===" + noIdsList.size());
	    if(!noIdsList.isEmpty()){
	    	listGm2 = mapper.getByIds(noIdsList);
	    	listGm1.addAll(listGm2);
	    	for (GoodsModel goodsModel : listGm2) {
	    		mcc.set(MEM_PRE + goodsModel.getUuid(),goodsModel);
			}
	    }
		
		
		return listGm1;
	}

}
