package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailViaInvest implements ReadMail {

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
            // Suche den Anfang
            while ( count < splited.length && !splited[count].contains("Zinseinnahmen:")) {
                count++;
                continue;
            }
        //  System.out.println(splited[count]);
            while (count < splited.length && !splited[count].contains("Hauptbetrag") ) {
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
                while (count < splited.length && !splited[count].contains("=E2=82=AC")) {
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
                //Wir wollen nur eine zeile haben !!
                return content;
                //if (count >= splited.length) return content;
            }
            return content;
        }

}
