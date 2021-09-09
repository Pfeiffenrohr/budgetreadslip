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
    //  System.out.println(splited[count]);
        String summe;
        while (count < splited.length && !splited[count].startsWith(";\"><b>") ) {
            SlipEntry se = new SlipEntry();
            while (count < splited.length && ( ! splited[count].startsWith(";\">") )  ) {
               //System.out.println(splited[count]);
               
                count++;
                continue;
            }
            if (splited[count].contains(";\"><b>Summe:</b></td>"))
            {
                return content;
            }
            if (count >= splited.length) return content;
            //System.out.println(splited[count]);
            String name = splited[count];
            name = name.substring(name.indexOf(">") + 1);
            name = name.substring(0, name.indexOf("<"));
            name = name.replaceAll("=E2=82=AC", "€");
            System.out.println("Name =" + name);
            content=content +name+" ";
            if (name.startsWith("-"))
            {
                count++;
                continue;
            }
            
            if (Character.isDigit(name.charAt(0)) && ! name.contains("Rabatt"))
            {
                count++;
                continue;
            }
            if (name.contains("Rabatt"))
            {
                name="Rabatt";
            }
            se.setName(name);
            
            count++;
            if (count >= splited.length) return content;
            while (count < splited.length && !splited[count].contains("sans-serif")) {
                count++;
                continue;
            }
            summe = splited[count];
            summe = summe.substring(summe.indexOf(">") + 1);
            if (summe.contains("=")) {
                summe = summe.substring(0, summe.indexOf("="));
            } else {
                summe = summe.substring(0, summe.indexOf("<"));
            }
            //System.out.println("Summe =" + summe);
            content=content +summe+" { \n";
            System.out.println("Summe =" + summe);
            se.setSum(summe);
            count++;
            list.add(se);
            if (count >= splited.length) return content;
        }
        return content;
    }

}
