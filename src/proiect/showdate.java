package proiect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class showdate {
    DateFormat forDate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat forTime = new SimpleDateFormat("hh:mm:ss");
    public void runDate(DataOutputStream out){

        String toReturn;

        //creare obiect Date
        Date date = new Date();

        toReturn = forDate.format(date) + forTime.format(date);
        try {
            out.writeUTF(toReturn);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
