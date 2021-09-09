package util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
----------------------------------------------------------------------------------------
問題單號：Bug #2746 - 使用者維護shd1b
修改摘要：密碼欄位寫入DB時要加密
更新版本：平台轉換
修改人員：eason
修改日期：0970527
----------------------------------------------------------------------------------------
*/

/**
 * <pre>
 * jdk 1.4 版本以上才能運作，否則請自行去 sun 的網站下載 Java Cryptography Extension (JCE)
 * jdk 1.3 使用方法：
 * 1.將 jce1.2.2/lib 下4個jar檔
 *   jce1_2_2.jar
 *   local_policy.jar
 *   sunjce_provider.jar
 *   US_export_policy.jar
 *   放到使用的 jdk 目錄下的 jre/lib/ext
 * 
 * 2.程式一開始要先把 SunJCE provider 加進來才可使用
 *   Security.addProvider(new com.sun.crypto.provider.SunJCE());
 * </pre>
 * 
 */
public class CryptoUtil {
	
	private final static String algorithm = "DES";
	
	private final static String transformation = "DES/ECB/PKCS5Padding";
	
	private final static String keystring = "recA!123";
	
	private final static String charset = "UTF-8";
	
	private final static int bufferSize = 1024 * 4;
	
	/**
	 * dynamically register provider
	 * @throws Exception
	 */
	private static void addProvider() throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}
	
	/**
	 * MIGRATION_YN變數=Y時，將明碼轉成密碼
	 * @param cleartext
	 * @param migration
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String cleartext, String migration) throws Exception {
		if (migration != null && "Y".equals(migration.toUpperCase())) {
			return encrypt(cleartext);
		} else {
			return cleartext;
		}
	}

	/**
	 * 將明碼轉成密碼
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String cleartext) throws Exception {
		return encryptstr(cleartext, charset);
	}

	/**
	 * 將明碼轉成密碼
	 * @param cleartext
	 * @return
	 * @throws Exception
	 */
	public static String encryptstr(String cleartext, String charset) throws Exception {
		if ("".equals(cleartext)) {
			return "";
		}

		try {
			addProvider();
			
			SecretKey key = new SecretKeySpec(keystring.getBytes(charset), algorithm);

			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] ciphertext = cipher.doFinal(cleartext.getBytes(charset));
			
			return byte2hex(ciphertext);
		} catch (Exception e) {
			System.out.println("cleartext = " + cleartext);
			System.out.println("CryptoUtil.encrypt Error : " + e.toString());
			return cleartext;
		}
	}
	
	/**
	 * MIGRATION_YN變數=Y時，將密碼轉成明碼
	 * @param ciphertext
	 * @param migration
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext, String migration) throws Exception {
		if (migration != null && "Y".equals(migration.toUpperCase())) {
			return decrypt(ciphertext);
		} else {
			return ciphertext;
		}
	}
	
	/**
	 * 將密碼轉成明碼
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext) throws Exception {
		return decryptstr(ciphertext, charset);
	}
	
	/**
	 * 將密碼轉成明碼
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decryptstr(String ciphertext, String charset) throws Exception {
		if ("".equals(ciphertext)) {
			return "";
		}

		try {
			addProvider();

			SecretKey key = new SecretKeySpec(keystring.getBytes(charset), algorithm);

			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] cleartext = cipher.doFinal(hex2byte(ciphertext));
			
			return new String(cleartext, charset);
		} catch (IllegalArgumentException iae){
			return ciphertext;
		} catch (Exception e) {
			System.out.println("ciphertext = " + ciphertext);
			System.out.println("CryptoUtil.decrypt Error : " + e.toString());
			return ciphertext;
		}
	}
	
	/**
	 * Convert hex string to byte array
	 * @param strhex
	 * @return
	 * @throws Exception
	 */
	private static byte[] hex2byte(String strhex) throws Exception {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	/**
	 * Convert byte array to hex string
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private static String byte2hex(byte[] b) throws Exception {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) {
		try {
			String text = "ACER123";
			String migration = "Y";
			System.out.println("text = " + text);
			System.out.println("migration = " + migration);
			
			String encryptStr = encrypt(text, migration);
			String decryptStr = decrypt(encryptStr, migration);
			System.out.println("encryptStr = " + encryptStr);
			System.out.println("decryptStr = " + decryptStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void encryptFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		CipherOutputStream cos = null;

		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			fis = new FileInputStream(fileIn);
			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			cos.close();
		}
	}
	
	public static void decryptFile(String fileIn, String fileOut) throws Exception {
		FileInputStream fis = null;
		CipherOutputStream cos = null;
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);

			byte[] buffer = new byte[bufferSize];

			fis = new FileInputStream(fileIn);
			cos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)), cipher);

			printFileSize(fis.available());

			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				cos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			cos.close();
		}
	}
	
	private static Cipher createCipher(int mode) {
		Cipher cipher = null;
		try {
			addProvider();

			SecretKey key = new SecretKeySpec(keystring.getBytes(charset), algorithm);

			cipher = Cipher.getInstance(transformation);
			
			cipher.init(mode, key);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipher;
	}	
	
	private static void printFileSize(int bytes) {
		String sizeMB = new BigDecimal(bytes).divide(new BigDecimal(1024 * 1024), 2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
