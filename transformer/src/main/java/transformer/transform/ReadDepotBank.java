package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadDepotBank implements ReadMail {

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
            for (int i =1;i< splited.length;i++)
            {
                String colums [] = splited[i].split(";");
                SlipEntry se = new SlipEntry();
                String name = colums[0];
                se.setName(name);
                String [] sum= colums[12].split(" ");
                summe= sum[0].replace(".", "").replace(",", "."); 
                se.setSum(summe);
                list.add(se);
            }
            return content;
        }
}
