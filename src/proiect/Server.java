package proiect;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor cu port
    public Server(int port)
    {
        // Porneste serverul si asteapta o conexiune
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for client ... ");

            socket = server.accept();
            System.out.println("Client accepted");

            // preluare input de la socket client
            in =new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String line = "";

            //citese mesaj client pana "Over este afisat"
            while(!line.equals("Over"))
            {
                line = in.readUTF();
                System.out.println(line);
            }

            System.out.println("Closing connection");

            // inchidem conexiune
            socket.close();
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        int port = 2250;
        System.out.println("port: " + args[0]);

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }

        Server Server = new Server(port);
    }


}
