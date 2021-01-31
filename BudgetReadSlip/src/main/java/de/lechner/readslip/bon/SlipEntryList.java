package de.lechner.readslip.bon;

import java.util.List;

public class SlipEntryList {
	
	List <SlipEntry> list;
	
	public SlipEntryList() {
		
	}
	
	public SlipEntryList(List<SlipEntry> list) {
		super();
		this.list = list;
	}

	public List<SlipEntry> getList() {
		return list;
	}

	public void setList(List<SlipEntry> list) {
		this.list = list;
	}
	
	

}
