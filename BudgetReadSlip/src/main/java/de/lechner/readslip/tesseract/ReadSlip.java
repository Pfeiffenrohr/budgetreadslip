package de.lechner.readslip.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ReadSlip {
	
	public void readTesseract() {
	//System.setProperty("jna.library.path", "C:/MyProgramms/tesseract/Tess4J-3.4.8-src/Tess4J/lib/win32-x86-64");
    Tesseract tesseract = new Tesseract(); 
    try { 

        tesseract.setDatapath("C:/MyProgramms/tesseract/Tess4J-3.4.8-src/Tess4J/tessdata"); 
        tesseract.setLanguage("eng");
        //tesseract.setHocr(true);

        // the path of your tess data folder 
        // inside the extracted file 
        String text 
         //   = tesseract.doOCR(new File("C:\\temp\\tessdata\\20210105_115509.jpg")); 
        = tesseract.doOCR(new File("Z:\\tmp\\testbild.png"
        		+ "")); 

        // path of your image file 
        System.out.print(text); 
    } 
    catch (TesseractException e) { 
        e.printStackTrace(); 
    } 
  }
}