package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.table.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		String userName = "WangJion";

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", userName);
		boolean s =  !userMapper.selectList(queryWrapper).isEmpty();
		return;
	}

}
