package com.rln.crossover;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.rln.acme.JavaCompilerApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JavaCompilerApplication.class)
@WebAppConfiguration
public class JavaCompilerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
