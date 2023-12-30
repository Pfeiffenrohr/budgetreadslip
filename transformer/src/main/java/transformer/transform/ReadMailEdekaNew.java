package transformer.transform;

import java.util.ArrayList;
import java.util.List;

public class ReadMailEdekaNew implements ReadMail{
    
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
        //Überspringe die Zeilen, bis zum Anfang
        while (!splited[count].startsWith("EUR")) {
            count++;
        }
        count++;
        while (!splited[count].startsWith("---") && !splited[count].startsWith("SUMME") ) {
            String line = splited[count];
            //Tokenize the line
            if (line.startsWith("G&G So")) {
                System.out.println("ddd");
            }
            line=line.replace("*"," ");
            line=line.replace("/"," ");
            line=line.replace("PREPACK","");
            line=line.replace("BED","");
            line = eliminateSonderzeichen(line);

            if (hasPattern(line)) {
                count++;
                continue;
            }
            line = optimizeLine(line);
            line= line.replaceAll("\\s+", " ");
            String tokens[] = line.trim().split(" ");

            SlipEntry se = new SlipEntry();
            String name = "";
            //Überspringe alle falschen Zeilen
            if ( tokens[0].startsWith("/kg") || tokens[0].startsWith("EURkg") || tokens[0].startsWith("SUMME") || tokens[0].startsWith("2x"))
            {
                count++;
                continue;
            }
            name=tokens[0];
            for (int j = 1; j < tokens.length; j++) {
                if (!isNumber(tokens[j]) ||  (j < tokens.length-2) ) {
                        name = name + " " + tokens[j];
                } else {
                    System.out.println("Name: " + name);
                    se.setName(name);
                    content = content + name + " ";
                    String summe = tokens[j];
                    se.setSum(summe);
                    System.out.println("Summe: " + summe);
                    content = content + summe + " {\n";
                    break;
                }
            }
            if (se.getName() != null && se.getSum() != null) {
                list.add(se);
            }
            count++;
        }
        System.out.println("Content "+content);
        return content;
    }

    /**
     * Prüft, ob es sich bei dem String um eine Zahl handelt
     */


   private boolean isNumber(String str)
    {
        str=str.replace(",",".");
        try {
             Double d = new Double(str);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }

    private boolean hasPattern(String str)  {
        String tokens[] = str.trim().split(" ");
        if (tokens.length < 3) {
            return false;
        }
        if (isNumber(tokens[0]) && tokens[1].equals("x") && isNumber(tokens[2])) {
            return true;
        }
        return false;
    }
    private String optimizeLine(String line) {
        for (int i = 0; i < line.length(); i++) {
            Character ch = line.charAt(i);
            if (isRealLetterAndNotx(ch)) {
                return line.substring(i);
            }
        }
        return line;
    }
    private static boolean isRealLetterAndNotx(char c) {
       if (c=='x' ) {return false; }
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z');
    }

    private String eliminateSonderzeichen (String line ) {
        String result = "";
        for (int i = 0; i < line.length(); i++) {
            Character ch = line.charAt(i);
            if (isLetterOrDigit(ch)) {
               result = result+ch;
            }

        }
        return result;
    }
    private static boolean isLetterOrDigit(char c) {
       if ( c==' ') {return true;}
        if ( c=='-') {return true;}
        if ( c==',') {return true;}
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }
}
