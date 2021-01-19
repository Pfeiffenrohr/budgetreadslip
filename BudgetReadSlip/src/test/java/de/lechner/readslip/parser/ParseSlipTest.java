package de.lechner.readslip.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
		List <String> str =  ps.scanner("eins € \n zwei € \n drei €");
		
		assertEquals(str,3);

}
}