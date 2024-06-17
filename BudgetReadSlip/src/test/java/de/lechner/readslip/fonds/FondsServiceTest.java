package de.lechner.readslip.fonds;

import de.lechner.readslip.bon.SlipEntry;
import de.lechner.readslip.bon.SlipEntryList;
import de.lechner.readslip.message.Budget;
import de.lechner.readslip.parser.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FondsServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Budget budget;
    @Test
    void depotTest()  {
        SlipEntry slipEntry = new SlipEntry();
        slipEntry.setName("somename");
        slipEntry.setSum("200");
        List list = new ArrayList<SlipEntry>();
        list.add(slipEntry);
        SlipEntryList slipEntryList = new SlipEntryList();
        slipEntryList.setList(list);
        Mockito.when(restTemplate.getForObject(anyString(),any())).thenReturn("100.0");

       Mockito.when(restTemplate.postForEntity(anyString(),any(),any())).thenReturn(new ResponseEntity("", HttpStatus.OK));
      // Mockito.when(budget.getInternalKontoname(anyString(),anyString(),anyString())).thenReturn("realkontoname");
        Mockito.when(budget.getInternalKontoname(anyString(),isNull(),isNull())).thenReturn("realkontoname");
        FondsService fondsService = new  FondsService(restTemplate,budget);
        Transaction trans = fondsService.parseFonds(list);
        assertEquals(Optional.ofNullable(trans.getWert()),Optional.ofNullable(100.0));
    }
}
