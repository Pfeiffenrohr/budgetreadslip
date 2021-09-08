package transformer.transform;

import java.util.List;

import transformer.transform.SlipEntry;

public interface ReadMail {
    public List <SlipEntry> getList ();
    public String parseMail(String txt); 

}
