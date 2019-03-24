package com.wlanboy.demo.hashing;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.hash.Hashing;

public class HashTest {

	public static String teststring = "Test1";
	public static String hashstring = "8a863b145dc6e4ed7ac41c08f7536c476ebac7509e028ed2b49f8bd5a3562b9f";
	
	public static String teststring2 = "Test2";
	public static String hashstring2 = "32e6e1e134f9cc8f14b05925667c118d19244aebce442d6fecd2ac38cdc97649";
	
	@Test
	public void testHash() {
		String sha256hex = Hashing.sha256()
				  .hashString(teststring, StandardCharsets.UTF_8)
				  .toString();
		System.out.println("Test1: "+sha256hex);
		Assert.assertEquals(sha256hex, hashstring);
	}
	
	@Test
	public void testHash2() {

		String sha256hex = Hashing.sha256()
				  .hashString(teststring2, StandardCharsets.UTF_8)
				  .toString();
		System.out.println("Test2: "+sha256hex);
		Assert.assertEquals(sha256hex, hashstring2);
	}
}
