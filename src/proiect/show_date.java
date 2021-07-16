package proiect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class show_date {


    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    public void runDate(DataOutputStream out){

        String toreturn;

        //creare obiect Date
        Date date = new Date();

        toreturn = fordate.format(date) + fortime.format(date);
        try {
            out.writeUTF(toreturn);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
