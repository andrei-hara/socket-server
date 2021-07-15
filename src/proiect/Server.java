package proiect;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Server {

    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    // constructor cu port
    public Server(int port)
    {

        try {
            server = new ServerSocket(port);

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void start()
    {
        while (true) {

            Socket socket = null;
            try {
                System.out.println("Server started");

                System.out.println("Waiting for client ... ");


                socket = server.accept();
                System.out.println("A new Client is accepted :" + socket);

                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // obiect thread nou

                Thread t = new ClientHandler(socket, in, out);

                // exec thread

                t.start();


            } catch (IOException e) {
                e.printStackTrace();
            }
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

        Server server = new Server(port);
        server.start();
    }


}
