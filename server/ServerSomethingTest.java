package server;

//import commands.AuthorCommand;
import commands.Command;
import database.DatabaseHandler;
import tools.Console;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;


public class ServerSomethingTest extends Thread {

    public Console serverConsole;
    private final SocketChannel socketChannel;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    ByteBuffer inputBuffer;
    ByteArrayOutputStream byteOutput;

    public ServerSomethingTest(SocketChannel socketChannel, Console console) {
        this.serverConsole = console;
        this.socketChannel = socketChannel;
        inputBuffer = ByteBuffer.allocate(4096);
        byteOutput = new ByteArrayOutputStream(4096);
        System.out.println("new client copy server!!!");
        start();
    }

    public static void clear(Buffer buffer)
    {
        buffer.clear();
    }

    @Override
    public void run() {

        try {
            socketChannel.configureBlocking(false);
            while (true) {
                ((Buffer)inputBuffer).clear();
                int count = socketChannel.read(inputBuffer);
                if (count <= 0) continue;
                inputStream = new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
                Command command = (Command) inputStream.readObject();
                System.out.println("we've just received sth from client");
                byteOutput = new ByteArrayOutputStream(4096);
                outputStream = new ObjectOutputStream(byteOutput);
                if (command.getUser() == null) {
                    System.out.println("we must write avtorisuites' ");
                    outputStream.writeObject(new ServerResult("Авторизуйтесь!!!!", command.getUser()));
                    socketChannel.write(ByteBuffer.wrap(byteOutput.toByteArray()));
                } else {

                    ServerResult result = new ServerResult(command.doit(serverConsole), command.getUser());
                    if(result.getResult().equals("неа")) {
                        this.serverConsole = new Console(ServerTest.connectToDatabase());
                        result.setResult("Нет подключения к бд");
                        outputStream.writeObject(result);
                        socketChannel.write(ByteBuffer.wrap(byteOutput.toByteArray()));

                        continue;
                    }
                    outputStream.writeObject(result);
                    System.out.println("we've just done sth");

                    socketChannel.write(ByteBuffer.wrap(byteOutput.toByteArray()));
                    System.out.println("we've just sent sth to client");
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println("SQLException in ServerSomething");
        }
    }
}