package transformmail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;   // Import the FileWriter class

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

;


//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;




public class Transform {
	
	public List <SlipEntry> list;

	public static void main(String[] args) {
		
		String url = args[3];
		Transform tr = new Transform();
		String txt=tr.readFile(args[0]);
		String content;
		if (args[2].equals("netto"))
		{
		content = tr.parseFileNetto(txt);
		tr.writeFile(content,args[1],url,"netto");
		}
		if (args[2].equals("edeka"))
		{
	    content = tr.parseFileEDEKA(txt);
		tr.writeFile(content,args[1],url,"edeka");
		}                                   
	  
	}
	
	private void writeFile(String content,String filename, String url,String company)
	{
		String body = transformToJson(list);
		System.out.println(body);
	
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			
		    HttpPost request = new HttpPost(url + "/bon");
		    StringEntity params = new StringEntity(body);
		    request.addHeader("content-type", "application/json");
		    request.addHeader("company", company);
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		} catch (Exception ex) {
		} finally {
		    // @Deprecated httpClient.getConnectionManager().shutdown(); 
		}
		
		System.out.println(content);
	/*	 try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(content);
		      myWriter.close();
		      /*File file = new File(filename);
				 BufferedWriter output = null;
		         output = new BufferedWriter(new FileWriter(file));
		         output.write(content);
		         output.close();*/
		       
		       /*  Writer out = new BufferedWriter(new OutputStreamWriter(
		        		    new FileOutputStream("outfilename"), "UTF-8"));
		        		try {
		        		    out.write(aString);
		        		} finally {
		        		    out.close();*/
		         
		    /*     System.out.println("Successfully wrote to the file. " +filename);
			
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }*/
		
	}

	    private String parseFileEDEKA(String txt) {
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
			//while (count < splited.length && (!splited[count].startsWith(">") && !splited[count].contains("Summe") || splited[count].contains("=E2=82=AC"))) {
			while (count < splited.length && (!splited[count].contains("<td>") && !splited[count].contains("Summe") || splited[count].contains("=E2=82=AC"))) {	
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
			while (count < splited.length && !splited[count].contains("=E2=82=AC")) {
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
			se.setSum(summe);
			content = content + summe + " { \n";
			count++;
			list.add(se);
			if (count >= splited.length)
				return content;
		
		}
		return content;
	}

	private String parseFileNetto(String txt)
	{
		list = new ArrayList<SlipEntry>();
		String content=""; 
		String splited [] = txt.trim().split("\n");
		System.out.println("File has " + splited.length + " lines");
		int count = 0;
		// Suche den Anfang
		while ( count < splited.length && !splited[count].contains("Filiale:")) {
			count++;
			continue;
		}
		//System.out.println(splited[count]);
		while (count < splited.length && !splited[count].contains("Summe") ) {
		
			if ( splited[count].contains("=09=09") ||splited[count].contains ("lver;"))
					{
						count++;
						continue;						
					}
			System.out.println(splited[count]);
			SlipEntry se = new SlipEntry();
			while (count < splited.length && !splited[count].startsWith(";\"")) {
				//System.out.println(splited[count]);
				count++;
				continue;
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
			
			if (Character.isDigit(name.charAt(0)))
			{
				count++;
				continue;
			}
			
			se.setName(name);
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
			System.out.println("Summe =" + summe);
			content=content +summe+" { \n";
			se.setSum(summe);
			count++;
			list.add(se);
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
	private String transformToJson(List <SlipEntry> selist)
	{
		String json= "{\r\n"
				+ "    \"list\": [";
		for (int i=0; i< selist.size();i++)
		{
			json=json+"{\r\n"
					+ "        \"name\" : \""+selist.get(i).getName() + "\" ,\n" ;
			json=json+        "   \"sum\" : \"" + selist.get(i).getSum() + "\" \n }";  
			if (i < selist.size()-1)
             {
				json=json+",";
		     }
		}
		json= json+"]\r\n"
				+ "}";
		return json;
	}
}
