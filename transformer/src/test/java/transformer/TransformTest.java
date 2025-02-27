package transformer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import transformer.transform.*;


public class TransformTest {
    
    @Test
    public void transformTest() throws Exception {
    
        System.out.println("Start Test");
        
    /*    result.add("eins €");
        result.add(" zwei €");
        result.add(" drei €");
        List <String> str =  ps.scanner("eins €\n zwei €\n irgendwas \n drei €");
        
        assertEquals(result,str);*/

}
    private String readFile(String filename)
    {
        String data="";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              data = data+myReader.nextLine() +"\n";
             // System.out.println(data);
            }
            data=data.substring(0, data.length() - 1);
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        return data;
    }
    
    
    @Test
    public void TwinoTest() throws Exception {
        
        System.out.println("Start Twino");
        Transform transform = new Transform();
      
        String data = readFile("src/test/resources/TwinoMail.txt");
        String result = new ReadMailTwino().parseMail(data);
       assertEquals("500.01 {",result.trim());  
    }
    @Test
    public void MintosTest() throws Exception {
        
        System.out.println("Start Mintos");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/MintosMail.txt");
        String result = new ReadMailMintos().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
      // System.out.println(">"+result+"<");
       assertEquals("1.25 {",result.trim());  
    }
    /*
    @Test
    public void MintosTest2() throws Exception {
        
        System.out.println("Start Mintos");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/MintosMail2.txt");
        String result = new ReadMailMintos().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
       System.out.println(">"+result+"<");
       assertEquals("17.28 {",result.trim());  
    }
    */
    @Test
    public void RobocashTest() throws Exception {
        
        System.out.println("Start Robocash");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/RobocashMail3.txt");
        String result = new  ReadMailRobocash().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
     //  System.out.println(">"+result+"<");
       assertEquals("2865.09 {",result.trim());
    }
    

    
    @Test
    public void PeerBerryTest() throws Exception {
        
        System.out.println("Start PeerBerry");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/PeerBerryMail.txt");
        String result = new  ReadMailPeerBerry().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
     //  System.out.println(">"+result+"<");
       assertEquals("345.98 {",result.trim());
    }
    
    @Test
    public void ViaInvestTest() throws Exception {
        
        System.out.println("Start ViaInvest");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/ViaInvestMail.txt");
        String result = new  ReadMailViaInvest().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
      // System.out.println(">"+result+"<");
       assertEquals("0.20 {",result.trim());  
    }

    @Test
    public void edekaNewTest() throws Exception {

        System.out.println("Start EdekaNew");
        //Transform transform = new Transform();

        String data = readFile("src/test/resources/EdekaNew.txt");
        String result = new ReadMailEdekaNew().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/EdekaNewResult.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }
    @Test
    public void kauflandTest() throws Exception {

        System.out.println("Start Kauflandtest");
        //Transform transform = new Transform();

        String data = readFile("src/test/resources/Kaufland1.txt");
        String result = new ReadMailKaufland().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/Kaufland1Result.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }
    @Test
    public void kaufland2Test() throws Exception {

        System.out.println("Start Kauflandtest");
        //Transform transform = new Transform();

        String data = readFile("src/test/resources/Kaufland2.txt");
        String result = new ReadMailKaufland().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/Kaufland2Result.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }
    @Test
    public void edekaNewNetoTest() throws Exception {
        System.out.println("Start EdekaNewNetto");
        //Transform transform = new Transform();   
        String data = readFile("src/test/resources/EdekaNewNetto.txt");
        String result = new ReadMailEdekaNew().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/EdekaNewNettoResult.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }
    @Test
    public void depotBankTest() throws Exception {
        System.out.println("Start depotBank");
        //Transform transform = new Transform();
       // String data = readFile("src/test/resources/Depotbank.csv");
        String data = readFile("src/test/resources/Depotbank.txt");
        String result = new ReadDepotBank().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/DepotbankResult.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }
     public void edekaNewTest1() throws Exception {

        System.out.println("Start EdekaNew");
        //Transform transform = new Transform();
        String data = readFile("src/test/resources/EdekaNew1.txt");
        String result = new ReadMailEdekaNew().parseMail(data);
        // String hhh = transform.parseFileTwino(data);
        String resultfile = readFile("src/test/resources/EdekaNewResult1.txt");
        System.out.println(">"+result+"<");
        assertEquals(resultfile,result.trim());
    }


}

