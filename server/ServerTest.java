package server;

import commands.SaveCommand;
import database.DatabaseHandler;
import tools.Console;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerTest {

    public static int PORT = 5000;
    public static LinkedList<ServerSomethingTest> serverList = new LinkedList<>();
    public static Console serverConsole;
    private static ServerSocketChannel serverSocketChannel;
    public static DatabaseHandler databaseHandler;
    public static String path = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        try {
            path = args[0];
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Аргумент - имя файла не найден.");
        }

        Class.forName("org.postgresql.Driver");
        //connectToDatabase();

        /*
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

         */

        try {
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if (PORT <= 1024) throw new NumberFormatException();
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }


        try {
            serverConsole = new Console(connectToDatabase());
        }catch (Exception e) {
            System.out.println("Нет подключения к бд");
        }

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);

        System.out.println("Server created!");
        SocketChannel socketChannel;


        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                if (System.in.available() != 0 && scanner.hasNextLine() && scanner.nextLine().equals("save")) {
                    System.out.println("read save");
                    SaveCommand.save(serverConsole);
                    continue;
                }
                //TODO тут вроде ничего менять не надо

                socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) continue;
                serverList.add(new ServerSomethingTest(socketChannel, serverConsole));
            }
        }finally {
            serverSocketChannel.close();
        }
    }

    public static DatabaseHandler connectToDatabase() {
        if(path.equals("")) {
            System.exit(-1);
        }
        Scanner scanner = null;
        String URL = "", username = "", password = "";
        try {
            scanner = new Scanner(new FileReader(path));
        }catch (FileNotFoundException e) {
            System.out.println("Не найден файл для входа  базу данных");
            System.exit(-1);
        }

        try {
            URL = scanner.nextLine().trim();
            username = scanner.nextLine().trim();
            password = scanner.nextLine().trim();
        }catch (NoSuchElementException e) {
            System.out.println("Не найдены данные для входа");
            System.exit(-1);
        }

        try {
            //databaseHandler = new DatabaseHandler("jdbc:postgresql://localhost:9999/studs", "s264922", "ton895");
            databaseHandler = new DatabaseHandler(URL, username, password);
            if(databaseHandler.connectToDatabase()) {
                return databaseHandler;
            }else{
                throw new Exception("ds lehbr");
            }
        }catch(Exception e) {
            System.out.println("Не можем подключиться к дб");
            databaseHandler = null;
            return databaseHandler;
        }
    }

    /*
    public static void login() {
        Scanner scanner;
        String URL, username, password;
        try {
            scanner = new Scanner(new FileReader("my.txt"));
        }catch (FileNotFoundException e) {
            System.out.println("Не найден файл для входа  базу данных");
            System.exit(-1);
        }
        try {
            URL = scanner.nextLine().trim();
            username = scanner.nextLine().trim();
            password = scanner.nextLine().trim();
        }catch (NoSuchElementException e) {
            System.out.println("Не найдены данные для входа");
            System.exit(-1);
        }
    }

     */
}