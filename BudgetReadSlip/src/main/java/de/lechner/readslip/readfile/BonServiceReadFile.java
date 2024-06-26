package de.lechner.readslip.readfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.parser.ParseSlip;
import de.lechner.readslip.transaction.UpdateTransactions;


@Service
public class BonServiceReadFile {
	
	 @Value("${budget.inputdir}")
	 String inputdir="";
	 @Value("${budget.filename}")
     String[] companies;
	/*@Autowired
	private BonRepository slipRepository;*/
	
	@Autowired
	private ParseSlip parseslip;
	
	@Autowired
	private UpdateTransactions updateTrans;

    private static final Logger LOG = LoggerFactory.getLogger(BonServiceReadFile.class);
    public BonServiceReadFile() {
    }

    public void handleSlipFile()
	{
        for (String company : companies) {
            String txt = readSlip(company + ".txt");
            if (txt.equals("File not found")) {
                continue;
            }
            parseslip.analyse(txt, company);
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
		StringBuilder txt = new StringBuilder();

        File file = new File(datName);
        if (! file.exists()) {
        	return "File not found";
        }

        if (!file.canRead() || !file.isFile()) {
        	LOG.error("Can not read Inputfile!");
        
           return "";
	}
            BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile;
            while ((zeile = in.readLine()) != null) {
                txt.append("\n").append(zeile);
            }
            
            return txt.toString();
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
                        if (!theDir.mkdirs()) {
                            LOG.error("Can not create directory");
                        }
                    }
                    if(file.renameTo(new File(inputdir+filename+"Files/"+ file.getName()+"-"+sdf.format(timestamp)))){
                  //  if (file.renameTo(new File("T:\\\\tmp\\oldNettoFiles\\gemoved.txt"))) {
                        LOG.info("File is moved successful!");
                       }else{
                        LOG.error("File is failed to move!");
                       }
                } catch (IOException e) {
                    LOG.error("Exception "+e);
                }
        }
	}
}