package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailDebitum implements ReadMail {

 private List <SlipEntry> list ;
    
    @Override
    public List <SlipEntry> getList () {
        return this.list;
    }

    
    @Override
    public String parseMail(String txt) {
            list = new ArrayList<SlipEntry>();
            String content=""; 
            String splited [] = txt.trim().split("\n");
            System.out.println("File has " + splited.length + " lines");
            int count = 0;
            String summe = "";
            // Suche den Anfang
            while ( count < splited.length && !splited[count].contains("Einnahmen =C3=BCber die gesamte Zeit")) {
                count++;
                continue;
            }
            
            while ( count < splited.length && !splited[count].contains("=E2=82=AC")) {
                count++;
                continue;
            }
            
           // count--;
          
         // System.out.println(splited[count]);
          
                SlipEntry se = new SlipEntry();
                String name = "Gesamtertrag";
                se.setName(name);
                summe = splited[count];
                summe = summe.substring(summe.indexOf("AC")+3);
              //summe = summe.substring(0, summe.indexOf("AC")-1);
                
                
                // summe = summe.substring(0, summe.indexOf(" "));
       
                System.out.println("Summe =" + summe);
                content=content +summe+" { \n";
                se.setSum(summe);
                count++;
                list.add(se);
                //Wir wollen nur eine zeile haben !!
                return content;
                //if (count >= splited.length) return content;
           
        }
}
