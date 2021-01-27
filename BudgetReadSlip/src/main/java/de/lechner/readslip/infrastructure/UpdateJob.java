package de.lechner.readslip.infrastructure;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component 
public class UpdateJob implements Job { 
	
    @Autowired
	BonReadServiceCron hs; 
    //private BonReadServiceCron hs = new BonReadServiceCron();
     
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
        hs.sayHello();
        hs.readBon();
        hs.updateTrancaction();
        } 
}