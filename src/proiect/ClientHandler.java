package proiect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread{

    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Socket s = null;
    //DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    //DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

    //Constructor

    public ClientHandler(Socket s ,DataInputStream in ,DataOutputStream out){
        this.s = s;
        this.in = in;
        this.out = out;

    }

    @Override
    public void run(){
        String received;
        String toreturn;
        Data obj = new Data();

        while(true)
        {
            try {
                // dialog cu utilizator
                out.writeUTF("Type Exit to terminate connection or anything else to display time and date");

                received = in.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                obj.runData();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Data
    {
        DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat fortime = new SimpleDateFormat("hh:mm:ss");

        public void runData(){

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
}

