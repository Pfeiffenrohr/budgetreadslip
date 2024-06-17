package transformer;

import org.junit.Test;
import transformer.transform.ReadConsorsBank;
import transformer.transform.ReadMailTwino;
import transformer.transform.SlipEntry;
import transformer.transform.Transform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReadConsorsBankTest {

    private String readFile(String filename)
    {
        String data="";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj,"iso-8859-1");
            while (myReader.hasNextLine()) {
                data = data+myReader.nextLine() +"\n";
                // System.out.println(data);
            }
            data=data.substring(0, data.length() - 1);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    @Test
    public void ConsorsTest() throws Exception {

        System.out.println("Start ConsorsTest");
        Transform transform = new Transform();

        String data = readFile("src/test/resources/Consors.csv");
        ReadConsorsBank cons = new ReadConsorsBank();
        String result = cons.parseMail(data);
        List <SlipEntry> list = cons.getList();
        assertTrue(list.size() == 5);
        assertEquals("INVESCOMI BB CMTY EXAGR A",list.get(0).getName());
        assertEquals("626.40",list.get(0).getSum());
    }

}
