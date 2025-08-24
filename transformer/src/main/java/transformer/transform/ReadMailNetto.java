package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailNetto implements ReadMail {
 
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
        while ( count < splited.length && !splited[count].contains("Filiale")) {
            count++;
            continue;
        }
      System.out.println(splited[count]);

      count++;
        String summe;
        while (count < splited.length && !splited[count].contains("Summe") ) {
            SlipEntry se = new SlipEntry();
            System.out.println("In while 1 "+count);
            

            
            System.out.println(splited[count]);
            String name = splited[count];
            System.out.println("Name = " +name);
            name = name.replaceAll("=", "").trim();
            name = name.replaceAll("\n", "").trim();
            name = name.substring(name.indexOf(">")+1);
            System.out.println("Name = " +name);
            name = name.substring(0, name.indexOf("<"));
            name = name.trim();
            System.out.println("Name = " +name);
            se.setName(name);
            count++;
            summe = splited[count];
           System.out.println("summe " +summe);
            summe = summe.substring(summe.indexOf(">")+1);
            System.out.println("summe " +summe);
            summe = summe.substring(0, summe.indexOf(" "));
            System.out.println("Summe =" + summe);
            se.setSum(summe);
            count++;
            list.add(se);
            if (count >= splited.length) return content;
            //System.out.println(splited[count]);
           // return "";
        }
        return content;
    }

}
