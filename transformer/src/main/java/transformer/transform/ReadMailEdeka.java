package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailEdeka  implements ReadMail{
    
private List <SlipEntry> list ;
    
    @Override
    public List <SlipEntry> getList () {
        return this.list;
    }

    
    @Override
    public String parseMail(String txt) {
        list = new ArrayList<SlipEntry>();
        String content = "";

        String splited[] = txt.trim().split("\n");
        System.out.println("File has " + splited.length + " lines");
        int count = 0;
        // Suche den Anfang
        while (count < splited.length && !splited[count].contains("Ihren Einkauf.")) {
            count++;
            continue;
        }
        System.out.println(splited[count]);
        while (count < splited.length && !splited[count].contains("Summe")) {
            SlipEntry se = new SlipEntry();
            while (count < splited.length && (!splited[count].contains("<td>") && !splited[count].contains("Summe") || (splited[count].contains("=E2=82=AC") && ! splited[count].contains("Rabatt")))) {
                count++;
                //System.out.println("counter");    
                continue;
            }
            System.out.println(splited[count]);
            if (count >= splited.length || splited[count].contains("Summe"))
                return content;
            String name = splited[count];
            
            name = name.substring(name.indexOf(">") + 1);
            name = name.substring(0, name.indexOf("<"));
            name = name.replaceAll("=E2=82=AC", "€");
            content = content + name + " ";
            se.setName(name);

            if (count >= splited.length)
                return content;
            while (count < splited.length && (!splited[count].contains("=E2=82=AC")|| splited[count].contains("Rabatt"))) {
                count++;
                continue;
            }
            String summe = splited[count];
            System.out.println("Summe "+summe);
            summe = summe.substring(summe.indexOf(">") + 1);
            if (summe.contains("=")) {
                summe = summe.substring(0, summe.indexOf("="));
            } else {
                summe = summe.substring(0, summe.indexOf("<"));
            }
            se.setSum(summe);
            content = content + summe + " { \n";
            count++;
            list.add(se);
            if (count >= splited.length)
                return content;
        
        }
        return content;

   }
}
