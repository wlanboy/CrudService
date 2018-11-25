package com.wlanboy.demo.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {
	
	@Test
	public void BCryptPasswordTest() {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String pwd = encode.encode("user");
		System.out.println(pwd);
		
		Assert.assertNotNull(pwd);
	}
}
