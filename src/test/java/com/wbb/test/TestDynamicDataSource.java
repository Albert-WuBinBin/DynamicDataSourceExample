package com.wbb.test;

import com.wbb.annotation.DynamicRoutingDataSource;
import com.wbb.bean.Cost;
import com.wbb.dataSource.MultiDataSource;
import com.wbb.mapper.DataSourceMapper;
import com.wbb.service.CostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 动态切换数据源方法一
 *
 * @author wbb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml",
        "classpath:spring-mybatis.xml"})
public class TestDynamicDataSource {

    @Autowired
    CostService costService;
    @Autowired
    DataSourceMapper dataSourceMapper;

    /**
     * 使用工具类切换数据源
     */
    @Test
    public void test() {
        Cost cost = new Cost(100);
        costService.insert(cost);
        MultiDataSource.setDataSourceKey("dataSource2");
        costService.insert(cost);
        MultiDataSource.toDefault();
        costService.insert(cost);
    }

    /**
     * 注解切换数据源
     */
    @Test
    @DynamicRoutingDataSource("dataSource2")
    public void test2() {
        costService.insert2(new Cost(1000));
    }
}
