package de.lechner.readslip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.lechner.readslip.bon.ReadJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@SpringBootApplication
public class BudgetReadSlipApplication {

	public static void main(String[] args) {		
		SpringApplication.run(BudgetReadSlipApplication.class, args);	
	}
}
