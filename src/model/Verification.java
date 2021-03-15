package model;

public class Verification {

	private static final int[] checkCode = { 1, 2, 1, 2, 1, 2, 4, 1 };

	

	public static boolean checkUniform(String code) {

		if (code.trim().length() != 8) {

			System.err.println("長度錯誤\n");
			return false;
		}

		int sum = 0;
		boolean isSeven = false;

		for (int i = 0; i < code.length(); i++) {
//			System.out.println("檢查第" + (i + 1) + "個字:");
			char c = code.charAt(i);
			if (!Character.isDigit(c)) {
				System.err.println("含有非數字");
				return false;
			}
			int current = Character.getNumericValue(c);

			if (i == 6 && current == 7) { // 特殊狀況:第7個數字=7時

				isSeven = true;

			} else {

				int temp = current * checkCode[i];

				if (temp >= 10) {

					temp = (temp / 10) + (temp % 10);
//					System.out.println("temp="+temp);								
				}

				sum += temp;

			}

		}
//		System.out.println("sum=" + sum);

		if (isSeven) {
			if (sum % 10 == 0 || (sum + 1) % 10 == 0) {
				return true;
			} else {
				return false;
			}
		} else {

			return (sum % 10 == 0);

		}

	}

}
