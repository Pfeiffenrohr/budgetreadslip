package transformer.transform;

import java.util.ArrayList;
import java.util.List;

import transformer.transform.SlipEntry;

public class ReadMailPeerBerry implements ReadMail {

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
            while ( count < splited.length && !splited[count].contains("Invested funds")) {
                count++;
                continue;
            }
        //  System.out.println(splited[count]);
            while (count < splited.length && !splited[count].contains("Profit") ) {
                SlipEntry se = new SlipEntry();
              /*  while (count < splited.length && !splited[count].startsWith("=AC")) {
                    System.out.println(splited[count]);
                    count++;
                    continue;
                }*/
                if (count >= splited.length) return content;
                //System.out.println(splited[count]);
                String name = "Gesamtertrag";
                se.setName(name);
                count++;
                if (count >= splited.length) return content;
                while (count < splited.length && !splited[count].contains("Profit")) {
                    count++;
                    if (count >= splited.length) return content;
                    continue;
                }
                summe = splited[count];
                summe = summe.substring(summe.indexOf(":") + 2);
              //  summe = summe.substring(0, summe.indexOf(":"));
                
                
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
            return content;
        }
        

}
