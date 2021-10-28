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

import transformer.transform.ReadMailMintos;
import transformer.transform.ReadMailPeerBerry;
import transformer.transform.ReadMailRobocash;
import transformer.transform.ReadMailTwino;
import transformer.transform.ReadMailViaInvest;
import transformer.transform.Transform;





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
        
       System.out.println(">"+result+"<");
       assertEquals("1.25 {",result.trim());  
    }
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
    @Test
    public void RobocashTest() throws Exception {
        
        System.out.println("Start Robocash");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/RobocashMail.txt");
        String result = new  ReadMailRobocash().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
       System.out.println(">"+result+"<");
       assertEquals("1400.31 {",result.trim());  
    }
    
    @Test
    public void PeerBerryTest() throws Exception {
        
        System.out.println("Start PeerBerry");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/PeerBerryMail.txt");
        String result = new  ReadMailPeerBerry().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
       System.out.println(">"+result+"<");
       assertEquals("0.12 {",result.trim());  
    }
    
    @Test
    public void ViaInvestTest() throws Exception {
        
        System.out.println("Start ViaInvest");
        //Transform transform = new Transform();
        
        String data = readFile("src/test/resources/ViaInvestMail.txt");
        String result = new  ReadMailViaInvest().parseMail(data);
      // String hhh = transform.parseFileTwino(data);
        
       System.out.println(">"+result+"<");
       assertEquals("0.20 {",result.trim());  
    }
   

}

