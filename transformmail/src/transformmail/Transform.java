package transformmail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class



public class Transform {

	public static void main(String[] args) {
		
		Transform tr = new Transform();
		String txt=tr.readFile(args[0]);
		String content = tr.parseFile(txt);
		tr.writeFile(content,args[1]);
		
	
	}
	
	private void writeFile(String content,String filename)
	{
		System.out.println(content);
		 try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(content);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file. " +filename);
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private String parseFile(String txt)
	{
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
		while (!splited[count].contains("Summe") && count < splited.length) {
			while (count < splited.length && !splited[count].startsWith("ans-serif")) {
				count++;
				continue;
			}
			if (count >= splited.length) return content;
			//System.out.println(splited[count]);
			String name = splited[count];
			name = name.substring(name.indexOf(">") + 1);
			name = name.substring(0, name.indexOf("<"));
			name = name.replaceAll("=E2=82=AC", "€");
			//System.out.println("Name =" + name);
			content=content +name+" ";
			count++;
			if (count >= splited.length) return content;
			while (count < splited.length && !splited[count].contains("sans-serif")) {
				count++;
				continue;
			}
			String summe = splited[count];
			summe = summe.substring(summe.indexOf(">") + 1);
			if (summe.contains("=")) {
				summe = summe.substring(0, summe.indexOf("="));
			} else {
				summe = summe.substring(0, summe.indexOf("<"));
			}
			//System.out.println("Summe =" + summe);
			content=content +summe+" €\n";
			count++;
			if (count >= splited.length) return content;
		}
		return content;
	}

	private String readFile(String inputfile)
	{
	    File file = new File(inputfile);
	    String txt ="";
        if (! file.exists()) {
        	return "File not found";
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(inputfile));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
               // System.out.println("Gelesene Zeile: " + zeile);
                txt=txt+"\n"+zeile;
            }
           // System.out.println(txt);
            
            return txt;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {  try {
            in.close();
        } catch (IOException e) {
        }
	}
	
}
}
