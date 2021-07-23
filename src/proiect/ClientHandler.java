package proiect;

import com.sun.deploy.security.SelectableSecurityManager;

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
        ShowDate d = new ShowDate();
        ServerInfo s = new ServerInfo();
        WeatherInfo w = new WeatherInfo();

        while(true)
        {
            try {
                // dialog cu utilizator
                out.writeUTF("Date --> display time and date\n" + "Info --> display server info\n" + "Weather --> display the weather at a specified location\n" + "Exit --> terminate the connection");
                received = in.readUTF();

                if(received.equals("Exit")) {
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
                    case "Info":
                        s.runServerInfo(out);
                        break;
                    case "Weather":
                            w.getWeather(out, in);
                        break;
                }
            }
            catch (IOException e) {
                System.out.println("Invalid output!");
                break;
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

