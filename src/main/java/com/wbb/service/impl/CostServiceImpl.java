package com.wbb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wbb.annotation.DynamicRoutingDataSource;
import com.wbb.bean.Cost;
import com.wbb.mapper.CostMapper;
import com.wbb.service.CostService;

@Service
public class CostServiceImpl implements CostService {

	@Resource
	private CostMapper costMapper;

	@Override
	public void insert(Cost cost)  {
		System.out.println("insert:" + costMapper.insert(cost));
	}

	@Override
	@DynamicRoutingDataSource("dataSource2")
	public void insert2(Cost cost)  {
		System.out.println("insert2:" + costMapper.insert(cost));
	}

}