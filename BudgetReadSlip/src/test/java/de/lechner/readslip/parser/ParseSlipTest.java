package de.lechner.readslip.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.MockitoAnnotations;




//@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ParseSlipTest {
	
	@BeforeEach
	private void setUp() throws Exception {
		System.out.println("Setup");
		//MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void ScannerTest() throws Exception {
		ParseSlip ps = new ParseSlip();
		List <String> result= new ArrayList <String>(); 
		result.add("eins €");
		result.add(" zwei €");
		result.add(" drei €");
		List <String> str =  ps.scanner("eins €\n zwei €\n irgendwas \n drei €");
		
		assertEquals(result,str);

}
	
	/*@Test
	void ParserTest() throws Exception {
		ParseSlip ps = new ParseSlip();
		List <String> input= new ArrayList <String>(); 
		input.add("eins 34,45 €");
		input.add(" zwei 23,23 €");
		input.add("-1 x  zwei 45,00 €");
		input.add("1 x eer 42,00 €");
		
		
		List <SlipEntry> result= new ArrayList <SlipEntry>(); 
		SlipEntry se = new SlipEntry();
		se.setName("eins");
		se.setSum("34,45");
		result.add(se);
		
		SlipEntry se1 = new SlipEntry();
		se1.setName("zwei");
		se1.setSum("23,23");
		result.add(se1);
		
		List <SlipEntry> str =  ps.parser(input);
		
		assertEquals(result,str);

}*/
	
	
}