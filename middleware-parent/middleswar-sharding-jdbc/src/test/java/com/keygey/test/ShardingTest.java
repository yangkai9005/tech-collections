package com.keygey.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.keygey.chapter.BootApplication;
import com.keygey.chapter.entity.TOrder;
import com.keygey.chapter.service.TOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BootApplication.class)
public class ShardingTest {
	@Autowired
	private TOrderService service;
	@Test
	public void testAdd(){
		List<TOrder> orderList = new ArrayList<TOrder>();
		try {
			for (int i = 0; i < 100; i++) {
				TOrder order = new TOrder();
				order.setId(i+1);
				order.setUserId(i+1);
				order.setRemark("test");
				//service.insert(order);
				orderList.add(order);
			}
			service.insertBatch(orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testSelect(){
		TOrder order = service.selectById(2);
		System.out.println(order.getUserId());
	}
	
}
