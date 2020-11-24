package com.cdd.eshop;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdd.eshop.datasource.TestTable;
import com.cdd.eshop.datasource.TestTableMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.cdd.eshop.datasource")
class EshopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	TestTableMapper testMapper;

	@Test
	void dataSourceTest(){
		assert testMapper!=null;
		List<TestTable> testTableList = testMapper.selectList(new QueryWrapper<>());
		assert testTableList.size() > 0;
	}

}
