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
            while ( count < splited.length && !splited[count].contains("Interest income")) {
                count++;
                continue;
            }
        //  System.out.println(splited[count]);
                String zeile = splited [count];
                SlipEntry se = new SlipEntry();
                String name = "Gesamtertrag";
                se.setName(name);
                String tokens  [] = zeile.trim().split(" ");

                summe = tokens [10];
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
