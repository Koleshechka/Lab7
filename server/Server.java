/*
package server;

import commands.SaveCommand;
import tools.Console;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {

    public static int PORT = 5000;
    public static LinkedList<ServerSomething> serverList = new LinkedList<>();
    public static Console serverConsole;

    public static void main(String[] args) throws IOException {

        Runtime.getRuntime().addShutdownHook(
                new Thread(
                        () -> {
                            try {
                                SaveCommand.save(serverConsole);
                            } catch (IOException e) {
                                System.out.println("не получилось");
                            }
                        }
                )
        );

        String path ="/Users/svetlana/Documents/GitHub/Lab5/lab5maven/src/main/java/input.json";

        try{
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if (PORT<=1024) throw new NumberFormatException();
        }catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }

        serverConsole = new Console(path);
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomething(socket, serverConsole));
                } catch(IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

 */
