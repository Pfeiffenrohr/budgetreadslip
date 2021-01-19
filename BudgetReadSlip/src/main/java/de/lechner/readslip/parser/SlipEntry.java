package de.lechner.readslip.parser;

public class SlipEntry {
	
	String name;
	String sum;

	
	public SlipEntry(String name, String sum) {
		super();
		this.name = name;
		this.sum = sum;
	}
	public SlipEntry() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}

}
