package transformer.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

;


//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;




public class Transform {
    
    private static List list = new ArrayList<SlipEntry>();

    public static void main(String[] args) {
        String url = args[3];
        Transform tr = new Transform();
        String txt=tr.readFile(args[0]);
        String content;
        System.out.println (" Mode = "+args[2]);
        if (args[2].equals("netto"))
        {
            ReadMail rm =  new ReadMailNetto();   
            content = rm.parseMail(txt);
            tr.writeFile(content,args[1],url,"netto",rm.getList());
        }
        if (args[2].equals("edeka"))
        {
            ReadMail rm =  new ReadMailEdeka();   
            content = rm.parseMail(txt);
            tr.writeFile(content,args[1],url,"edeka",rm.getList());
        } 
        if (args[2].equals("wuensche"))
        {
            ReadMail rm =  new ReadMailWuensche();   
            content = rm.parseMail(txt);
            tr.writeFile(content,args[1],url,"wuensche",rm.getList());
        } 
        if (args[2].equals("rewe"))
        {
            ReadMail rm =  new ReadMailRewe();   
            content = rm.parseMail(txt);
            tr.writeFile(content,args[1],url,"rewe",rm.getList());
        }
        if (args[2].equals("mintos"))
        {
        ReadMail rm =  new ReadMailMintos();   
        content = rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Mintos",rm.getList());
        }  
        if (args[2].equals("viainvest"))
        {
            ReadMail rm =  new ReadMailViaInvest();  
        content = rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"ViaInvest",rm.getList());
        } 
        if (args[2].equals("peerberry"))
        {
            ReadMail rm =  new ReadMailPeerBerry();    
        content = rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"PeerBerry",rm.getList());
        } 
        if (args[2].equals("robocash"))
        { 
            ReadMail rm =  new ReadMailRobocash();    
        content = rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Robocash",rm.getList());
        } 
        if (args[2].equals("twino"))
        { 
            ReadMail rm =  new ReadMailTwino();
        content =rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Twino",rm.getList());
        } 
        if (args[2].equals("income"))
        { 
            ReadMail rm =  new ReadMailIncome();
        content =rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Income",rm.getList());
        } 
        if (args[2].equals("depotbank"))
        { 
            ReadMail rm =  new ReadDepotBank();
        content =rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Depotbank",rm.getList());
        } 
        if (args[2].equals("swaper"))
        { 
            ReadMail rm =  new ReadMailSwaper();
        content =rm.parseMail(txt);
        tr.writeFile(content,args[1],url,"Swaper",rm.getList());
        }
      
    }
    
    private void writeFile(String content,String filename, String url,String company, List <SlipEntry> list)
    {
        String body = transformToJson(list);
        System.out.println(body);
        
      
        if (company.equals("Mintos") 
                ||company.equals("ViaInvest") 
                || company.equals("PeerBerry") 
                || company.equals("Robocash")
                || company.equals("Twino")
                || company.equals("Income")
                || company.equals("Swaper"))
            
        {
            url=url + "/p2p";
        }
        else
        {
            if (company.equals("Depotbank"))
            {
                url=url + "/fonds";
            }
            else
            {    
            url=url + "/bon";
            }
        }
         
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            
            HttpPost request = new HttpPost(url);
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

