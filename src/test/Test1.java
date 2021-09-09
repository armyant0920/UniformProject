package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

class Test1 {
	
	
	public static String[] toStringArray(String s,String type) {
		if(s.length()>0) {
			String temp = type;
			String stemp = s;
			Vector vr = new Vector();
			if(temp.length()==0) temp = ",";
			boolean flag = true;
			int len = type.length();
			/*StringTokenizer st = new StringTokenizer(s,temp);
			String[] result = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()) {
				result[i] = st.nextToken();
				i++;
			}*/
			int si = 0,ei = 0;
			while (flag == true) {
				si = stemp.indexOf(temp);
				//ei = s.indexOf(temp,(si + 1));
				//當第一個字元就屬type時，要第一個元素放空字串
				if(si == 0) {
					//ei = si + 1;
					vr.add(null);
					stemp = stemp.substring((si+len),stemp.length());
					//si = si +1;
					//ei = si + 1;
				} else if(si > -1 ){
					vr.add(stemp.substring(0,si));
					stemp = stemp.substring((si+len),stemp.length());

				} else if(si==-1) {
					if(stemp.length()>0) {
						vr.add(stemp);

					}
					break;
				}
				if(stemp.length() == 0 && s.lastIndexOf(temp)+len == s.length()) {
					vr.add(null);
				}

				//si = si+1;
				/*if(si > -1) {
					if(ei == -1) {
						ei = s.length();
						si = si + 1;
						flag = false;
					}
					if(si == (ei-1)) {
						vr.add(null);
					} else
						vr.add(s.substring(si,ei));

				}
				if(ei < s.length()) {
					stemp = s.substring(ei,s.length());
					si = ei ;
				}*/
			}
			String[] result = new String[vr.size()];
			for(int i=0;i<vr.size();i++) {
				result[i] = (String)vr.elementAt(i);
			}
			temp = null;
			stemp = null;
			//st = null;
			return result;
		} else {
			return new String[0];
		}
	}

	@Test
	void test() {
		System.out.println("test begin");
		String s="abcadgafaaasfqaacghsha";
		String[]array=toStringArray(s,null);
		for(String ss:array) {
			
			System.out.println(ss);
		}
		System.out.println("end");
	}

}
