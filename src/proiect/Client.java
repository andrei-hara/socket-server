package proiect;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    // initializare socket si input/output streams

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Scanner scn = null;

    // constructor pt IP Adress si port

    public Client (String address, int port) {
        // initializare conexiune
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void init(){
        try {
            //preluare input din terminal
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            //transmitere output catre socket
            out = new DataOutputStream(socket.getOutputStream());

            // scanner pt citire linii
            scn = new Scanner(System.in);

            // exchange intre client si clienthandler
            while (true) {
                System.out.println(in.readUTF());
                String tosend = scn.nextLine();
                out.writeUTF(tosend);

//                 If client sends exit,close this connection
//                 and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }
            }
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException i) {
            i.printStackTrace();
        }

        //inchidere conexiune
        try {
            in.close();
            out.close();
            socket.close();
            scn.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        int port = 2250;
        System.out.println("port: " + args[0]);

        //preluare port din linie de comanda
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }

        Client client = new Client("127.0.0.1", port);
        client.init();
    }

}
