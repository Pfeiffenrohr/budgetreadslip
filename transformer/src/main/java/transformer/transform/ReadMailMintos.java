package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailMintos implements ReadMail {

    @Override
    public String parseMail(String txt) {
        // TODO Auto-generated method stub
        {
            List list = new ArrayList<SlipEntry>();
            String content=""; 
            String splited [] = txt.trim().split("\n");
            System.out.println("File has " + splited.length + " lines");
            int count = 0;
            // Suche den Anfang
            while ( count < splited.length && !splited[count].contains("Gesamtertrag")) {
                count++;
                continue;
            }
        //  System.out.println(splited[count]);
            while (count < splited.length && !splited[count].contains("Auszug") ) {
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
                while (count < splited.length && !splited[count].startsWith("=AC")) {
                    count++;
                    if (count >= splited.length) return content;
                    continue;
                }
                String summe = splited[count];
                summe = summe.substring(summe.indexOf(" ") + 1);
                
                    summe = summe.substring(0, summe.indexOf("<"));
       
                System.out.println("Summe =" + summe);
                content=content +summe+" { \n";
                se.setSum(summe);
                count++;
                list.add(se);
                return content;
            }
            return content;
        }
        
    }

}
