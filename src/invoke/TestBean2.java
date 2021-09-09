package invoke;



import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

//adasdas
/**
 * asd
 * 
 * @author Ren.Yu
 *
 */

public class TestBean2 implements TestService {

	@Override
	@TestAnn(prgid = "execute")
	public void execute() {
		System.out.println("this is execute");
	}

	@TestAnn(prgid = "AA")
	public String AA() {
			return "挖搭西挖AA荒法";
	}
	
	@TestAnn(prgid = "BB")
	public String BB() {
		return "挖搭西挖BB荒法";
	}
	
	
	@TestAnn(prgid = "BB")
	public String BB(HashMap map) {
		return "挖搭西挖低能";
	}
	
	@TestAnn(prgid="TEST01")
	public int test01(int index,String s,boolean b) {//
		return 999;
	}
}
