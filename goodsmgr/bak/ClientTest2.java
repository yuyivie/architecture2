package com.yuy.architecture1.goodsmgr.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.yuy.architecture1.goodsmgr.vo.GoodsModel;
import com.yuy.architecture1.goodsmgr.vo.GoodsQueryModel;

@Service
public class ClientTest2 {
	
	@Autowired
	private GoodsDAO dao;
	

	public static void main(String[] args) {

		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClientTest2 ct = (ClientTest2) ctx.getBean("clientTest2");
		
//		GoodsModel gm =  ct.dao.getByUuid(3);
		
		GoodsQueryModel gqm = new GoodsQueryModel();
		gqm.getPage().setPageShow(100);
		
		 List<GoodsModel> list =ct.dao.getByConditionPage(gqm);
		System.out.println("list==" + list);
	}
}
