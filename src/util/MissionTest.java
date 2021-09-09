package util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MissionTest {

	@Test
	public void test2() {
		int index = 0;

//		Thread t1=new Thread(new Mission(0,3333));
//		
//		Thread t2=new Thread(new Mission(0,3333));
		String pwdString = "a^";

		Thread t1 = new Thread(new Mission(33, 126, pwdString.length(), pwdString,"t1"));
		Thread t2 = new Thread(new Mission(33, 126, pwdString.length(), pwdString,"t2"));
		Thread t3 = new Thread(new Mission(33, 126, pwdString.length(), pwdString,"t3"));

		List<Thread> threads = new ArrayList<>();
		threads.add(t1);
		threads.add(t2);
		threads.add(t3);

		for (Thread t : threads) {
			t.run();

			

		}
		
		try {
			for (Thread t : threads) {
				t.run();}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
