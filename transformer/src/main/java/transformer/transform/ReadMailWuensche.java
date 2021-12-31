package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailWuensche implements ReadMail {
 
    private List <SlipEntry> list ;
    
    @Override
    public List<SlipEntry> getList() {
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
        while ( count < splited.length && !splited[count].contains("Wuensche_Hohenwart")) {
            count++;
            continue;
        }
        SlipEntry se = new SlipEntry();
    //  System.out.println(splited[count]);
        String summe;
            String name = "Semmeln Brezen" ;
            se.setName(name);
            
           
            summe = splited[count];
            summe = summe.substring(summe.indexOf("Hohenwart_f=C3=BCr_") + 19);
                summe = summe.substring(0, summe.indexOf("_"));
            summe = summe.replace("=2C",".");
            //System.out.println("Summe =" + summe);
            content=content +summe+" { \n";
            System.out.println("Summe =" + summe);
            se.setSum(summe);
            count++;
            list.add(se);
            if (count >= splited.length) return content;
        return content;
    }

}