package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadConsorsBank implements ReadMail {

 private List <SlipEntry> list ;
    
    @Override
    public List <SlipEntry> getList () {
        return this.list;
    }

    
    @Override
    public String parseMail(String txt) {
        System.out.println("ReadConsorsBank");
            list = new ArrayList<SlipEntry>();
            String content=""; 
            String splited [] = txt.trim().split("\n");
            System.out.println("File has " + splited.length + " lines");
            int count = 0;
            String summe = "";
            // Suche den Anfang
            
            for (int i =7;i< splited.length;i++)
            {
                System.out.println("splited " +splited[i]);
                String colums [] = splited[i].split(";");
                if (colums.length < 1 ||colums[0].equals(""))
                {
                    break;
                }
                SlipEntry se = new SlipEntry();
                String name = colums[0].replace("&", "u");
                se.setName(name);
                String [] sum= colums[13].split(" ");
                summe= sum[0].replace(".", "").replace(",", ".");
                summe= sum[0];
                se.setSum(summe);
                list.add(se);
            }
            System.out.println("Content = " +content);
            System.out.println("END Content = ");
            return content;
        }
}
