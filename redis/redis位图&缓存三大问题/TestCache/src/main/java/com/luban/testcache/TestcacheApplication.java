package com.luban.testcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,pattern="com.luban.testcache.datainit.*")})
@SpringBootApplication
public class TestcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcacheApplication.class, args);
	}

}
