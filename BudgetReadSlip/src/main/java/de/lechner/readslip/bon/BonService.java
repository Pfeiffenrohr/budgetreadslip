package de.lechner.readslip.bon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.lechner.readslip.parser.ParseSlip;
import de.lechner.readslip.transaction.UpdateTransactions;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class BonService {
	@Autowired
	private BonRepository slipRepository;
	
	@Autowired
	private ParseSlip parseslip;
	
	@Autowired
	private UpdateTransactions uodateTrans;
		
	public void handleSlip()
	{
		String txt = readSlip();
		parseslip.analyse(txt);
	}
	
	public void updateOldTransactions()
	{
		uodateTrans.update();
	}
		
	private String readSlip() {
		String datName = "T:\\tmp\\netto2.txt";
		String txt ="";

        File file = new File(datName);

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
                } catch (IOException e) {
                }
        }
	}
}