package proiect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {

    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Socket s = null;

    //Constructor
    public ClientHandler(Socket s ,DataInputStream in ,DataOutputStream out){
        this.s = s;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(){
        String received;
        showdate d = new showdate();
        serverinfo s = new serverinfo();

        while(true)
        {
            try {
                // dialog cu utilizator
                out.writeUTF("Type Exit to terminate connection / Date to display time and date / Info to display server Info");

                received = in.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch(received){
                    case "Date":
                    d.runDate(out);
                    break;
                    case "Info" :
                        s.run_server_info(out);
                        break;

                }

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
}

