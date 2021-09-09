package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import util.CryptoUtil;
import util.DecryptTool;

class DecryptTest1 {

	@Test
	void test() throws Exception {
		String pwd="f";
		
		
		System.out.println("encrypt code="+CryptoUtil.encrypt(pwd));
		
		
		System.out.println("pwd = "+DecryptTool.getPWD(pwd));
		
		
	}

}
