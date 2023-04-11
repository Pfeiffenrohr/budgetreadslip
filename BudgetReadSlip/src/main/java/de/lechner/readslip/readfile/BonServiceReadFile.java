package de.lechner.readslip.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.parser.ParseSlip;
import de.lechner.readslip.transaction.UpdateTransactions;


@Service
public class BonServiceReadFile {
	
	 @Value("${budget.inputdir}")
	 String inputdir;
	 @Value("${budget.filename}") 
	 String companies [];
	/*@Autowired
	private BonRepository slipRepository;*/
	
	@Autowired
	private ParseSlip parseslip;
	
	@Autowired
	private UpdateTransactions updateTrans;
		
	public void handleSlipFile()
	{	
		for (int i = 0; i < companies.length; i++) {			
			String txt = readSlip(companies[i] + ".txt");
			if (txt.equals("File not found")) {
				//System.out.println("File not found! "+ companies[i]);
				continue;
			} 
			parseslip.analyse(txt, companies[i]);
		}
	}
	
	public void handleSlipRest(SlipEntryList list, String company)
	{		
			parseslip.analyseRest(list, company);
	}
	
	

	public void updateOldTransactions()
	{
		updateTrans.update();
	}
		
	private String readSlip(String filename) {
		 
		String datName = inputdir+filename;
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    //
                    File theDir = new File(inputdir+filename+"Files");
                    if (!theDir.exists()){
                        theDir.mkdirs();
                    }
                    // System.out.println("Move fiel to "+inputdir+filename+"Files/"+ file.getName()+"-"+sdf.format(timestamp));
                    if(file.renameTo(new File(inputdir+filename+"Files/"+ file.getName()+"-"+sdf.format(timestamp)))){
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