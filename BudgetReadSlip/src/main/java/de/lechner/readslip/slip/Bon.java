package de.lechner.readslip.slip;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Slip")
public class Bon {
	
	
	@Id
	int id;
	String company;
	String rawname;
	String internalname;
	String rawnameMutant;
	String category;
	



	public Bon(int id, String company, String rawname, String internalname, String rawnameMutant, String category) {
		super();
		this.id = id;
		this.company = company;
		this.rawname = rawname;
		this.internalname = internalname;
		this.rawnameMutant = rawnameMutant;
		this.category = category;
	}
	
	public Bon() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRawname() {
		return rawname;
	}
	public void setRawname(String rawname) {
		this.rawname = rawname;
	}
	public String getInternalname() {
		return internalname;
	}
	public void setInternalname(String internalname) {
		this.internalname = internalname;
	}
	public String getRawnameMutant() {
		return rawnameMutant;
	}

	public void setRawnameMutant(String rawnameMutant) {
		this.rawnameMutant = rawnameMutant;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
