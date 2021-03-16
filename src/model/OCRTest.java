package model;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class OCRTest {

	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		File file = new File("image/test.jpg");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getName());
		System.out.println(file.length());
		System.out.println("exist:"+file.exists());
		System.out.println("file size:"+file.length());
		
		Tesseract instance=new Tesseract();
		
		instance.setDatapath("‪‪D:\\Tesseract-OCR\\tessdata");
		

		try {
			String result = instance.doOCR(file);
			 System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		

	}

}
