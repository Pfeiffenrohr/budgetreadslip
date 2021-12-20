package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailRewe implements ReadMail {
 
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
        while ( count < splited.length && !splited[count].contains("EUR")) {
        	System.out.println(splited[count]);
            count++;
            continue;
        }
        count ++;
    //  System.out.println(splited[count]);
        String summe ="";
        while (count < splited.length && !splited[count].contains("SUMME") ) {
            SlipEntry se = new SlipEntry();
            if (splited[count].length()==0)
            {
            	count++;
            	continue;
            }
           // summe = summe.substring(summe.indexOf("AC ") + 3);
     
            if (count >= splited.length) return content;
           // System.out.println(splited[count]);
            String name = splited[count].substring(0,splited[count].indexOf("  "));
            System.out.println("Name = >" + name+"<");
            if (name.equals("") || name.startsWith(" "))
            {
            	count++;
            	continue;
            }
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
            
            String chunk [] = splited[count].trim().split(" ");
            if (chunk[chunk.length-1].equals("*"))
            {
            	 summe= chunk[chunk.length-3];
            }
            else
            {
            summe= chunk[chunk.length-2];
            }
        /*    for (int i=0; i< chunk.length;i++ )
            {
            	if (chunk[i].equals("B"))
            	{
            		summe= chunk[i-1];
            		break;
            	}
            }*/
            
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
