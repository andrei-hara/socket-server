package proiect;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    // initializare socket si input/output streams

    private Socket socket = null;
    private BufferedReader input = null;
    private DataOutputStream out = null;

    // constructor pt IP Adress si port

    public Client (String address, int port)
    {
        // initializare conexiune

        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            //preluare input din terminal
             input = new BufferedReader(new InputStreamReader(System.in));

            //transmite output catre socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException i) {
            i.printStackTrace();
        }

        // String pt a citi mesajul din tabul pt input

        String line = "";

        // Citeste pana "Over" apare pe ecran

        while(!line.equals("Over"))
        {
            try {
                line=input.readLine();
                out.writeUTF(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //inchidere conexiune
        try {
            input.close();
            out.close();
            socket.close();
        }
         catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int port = 0;
        System.out.println("portul: " + args[0]);

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }


        Client client = new Client("127.0.0.1", port );
    }

}
