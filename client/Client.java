package client;


import commands.Command;
import server.ServerResult;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {

    private int PORT = 5000;
    private static Client client;
    //private Client client;
    ObjectOutputStream toServer;
    ObjectInputStream fromServer;
    private boolean connect;

    public Client(ObjectOutputStream toServer, ObjectInputStream fromServer) {
        this.toServer = toServer;
        this.fromServer = fromServer;
    }

/*
    public Client() throws InterruptedException {
        try {
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if(PORT <= 1024) throw new NumberFormatException();
        }catch(NumberFormatException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }
        this.client.connect(PORT);
    }

 */

    public Client() {

    }

    public void searchPort() throws InterruptedException {
        try {
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if(PORT <= 1024) throw new NumberFormatException();
        }catch(NumberFormatException | NoSuchElementException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }
        client.connect(PORT);
    }


    public static void main(String[] args) throws InterruptedException {
        client = new Client();
        client.searchPort();
        //client.connect(PORT);
        /*
        try {
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if(PORT <= 1024) throw new NumberFormatException();
        }catch(NumberFormatException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }

         */
        //client.connect(PORT);
        //client.connect(PORT);
        //client.speak();


    }


    public void waiting() {

    }

    public void connect(int PORT) throws InterruptedException {
        try(Socket socket = new Socket("127.0.0.1", PORT)) {
            try {
                toServer = new ObjectOutputStream(socket.getOutputStream());
                fromServer = new ObjectInputStream(socket.getInputStream());
                client = new Client(toServer, fromServer);
                System.out.println("Мы успешно подключились к серверу!");
                client.speak(client);


            }catch(IOException e) {
                System.out.println("or we're here");
            }
        }catch (IOException e) {

            System.out.println("Мы не можем подключиться к серверу по указанному порту. Давайте попробуем еще раз.");
            client.searchPort();
            //client.connect(PORT);

        }
    }


    public void speak(Client client) throws IOException, InterruptedException {
        while (true) {
            String command;
            Scanner scanner = new Scanner(System.in);
            try {
                while (scanner.hasNextLine()) {
                    command = scanner.nextLine();
                    Command now = Listener.read(command, scanner);
                    if(now == null) {
                        continue;
                    } else {
                        client.toServer.writeObject(now);
                        // Ответ сервера
                        ServerResult serverResult = (ServerResult) client.fromServer.readObject();
                        System.out.println(serverResult.result);
                    }

                }
            } catch (EOFException | SocketException e) {
                //client.wait(20000);
                System.out.println("Соединение с сервером потеряно. Попробуем еще раз через некоторое время.");
                client.connect(PORT);
            } catch(Exception e) {
                System.out.println(e.toString() + "we're here");
            }
            client.fromServer.close();
        }
    }
    /*

    public void speak() {
        try (Socket socket = new Socket("127.0.0.1", PORT)) {
            //socket.setSoTimeout(20000);
            try (ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream())) {
                // Отправка данных на сервер
                ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    String command;
                    Scanner scanner = new Scanner(System.in);
                    try {
                        while (scanner.hasNextLine()) {
                            command = scanner.nextLine();
                            Command now = Listener.read(command, scanner);
                            if(now == null) {
                                continue;
                            } else {
                                toServer.writeObject(now);
                                // Ответ сервера
                                ServerResult serverResult = (ServerResult) fromServer.readObject();
                                System.out.println(serverResult.result);
                            }

                        }
                    } catch (EOFException | SocketException e) {
                        client.speak();
                    } catch(Exception e) {
                        System.out.println(e.toString() + "we're here");
                    }
                    fromServer.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

     */
}
