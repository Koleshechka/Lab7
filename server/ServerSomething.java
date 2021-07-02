/*
package server;

import commands.AuthorCommand;
import commands.Command;
import commands.SaveCommand;

import javax.imageio.IIOException;

import database.DatabaseHandler;
import tools.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ServerSomething extends Thread {

    private Socket socket;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    public Console serverConsole;


    public ServerSomething(Socket socket, Console console) throws IOException {
        this.socket = socket;
        this.serverConsole = console;
        fromClient = new ObjectInputStream(socket.getInputStream());
        toClient = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                if (System.in.available() != 0 && scanner.nextLine().equals("save")) {
                    SaveCommand.save(serverConsole);
                }
                if (socket.getInputStream().available() != 0) { // WARNING : getByte only from socket.Stream!
                    AuthorCommand command = (AuthorCommand) fromClient.readObject();
                    toClient.writeObject(new ServerResult(command.doit(serverConsole)));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" ");
        }
    }
}


 */