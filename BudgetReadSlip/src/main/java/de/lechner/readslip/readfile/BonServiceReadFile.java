package de.lechner.readslip.bon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.lechner.readslip.parser.ParseSlip;
import de.lechner.readslip.transaction.UpdateTransactions;


@Service
public class BonService {
	/*@Autowired
	private BonRepository slipRepository;*/
	
	@Autowired
	private ParseSlip parseslip;
	
	@Autowired
	private UpdateTransactions uodateTrans;
		
	public void handleSlip()
	{
		String txt = readSlip();
		if (txt.equals("File not found"))
		{
			System.out.println("File not found!");
			return;
		}
		parseslip.analyse(txt);
	}
	
	public void updateOldTransactions()
	{
		uodateTrans.update();
	}
		
	private String readSlip() {
		String datName = "Z:\\tmp\\netto.txt";
		String txt ="";

        File file = new File(datName);
         
        if (! file.exists()) {
        	return "File not found";
        }

        if (!file.canRead() || !file.isFile()) {
        	System.err.println("Can not read Inputfile!");
        
           return "";
	}
            BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
               // System.out.println("Gelesene Zeile: " + zeile);
                txt=txt+"\n"+zeile;
            }
            System.out.println(txt);
            
            return txt;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (in != null)
                try {
                    in.close();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    //
                    File theDir = new File("Z:\\tmp\\oldNettoFiles");
                    if (!theDir.exists()){
                        theDir.mkdirs();
                    }
                    System.out.println("Timestamp=" +timestamp);
                    if(file.renameTo(new File("Z:\\tmp\\oldNettoFiles\\" + file.getName()+" "+sdf.format(timestamp)))){
                  //  if (file.renameTo(new File("T:\\\\tmp\\oldNettoFiles\\gemoved.txt"))) {
                        System.out.println("File is moved successful!");
                       }else{
                        System.err.println("File is failed to move!");
                       }
                } catch (IOException e) {
                }
        }
	}
}