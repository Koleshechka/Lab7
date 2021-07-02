package client;


//import commands.AuthorCommand;
import commands.Command;
import server.ServerResult;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class CLientTest2 {

    private int PORT = 5000;
    private static CLientTest2 client;
    private SocketChannel socketChannel;
    private LogCommand logger = null;
    public static User user = null;
    //private String name; // TODO
    //private String password; // TODO метод для чтения и изменения текущего пользователя

    public static void main(String[] args) throws InterruptedException {
        client = new CLientTest2();
        client.searchPort();
    }

    public CLientTest2(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public CLientTest2 () {}

    public void searchPort() throws InterruptedException {
        try {
            System.out.println("Введите порт: ");
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if (PORT <= 1024) throw new NumberFormatException();
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Некорректное значение, устанавливаем значение по умолчанию (5000)");
            PORT = 5000;
        }
        client.connect(PORT);
    }

    public void connect(int PORT) throws InterruptedException {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(PORT));

            client = new CLientTest2(socketChannel);
            System.out.println("Мы успешно подключились к серверу!\nВведите команду log для авторизации в системе.");
            client.speak(client);
        } catch (IOException e) {
            System.out.println("Мы не можем подключиться к серверу по указанному порту. Давайте попробуем еще раз.");
            client.searchPort();
        }
    }


    public void speak(CLientTest2 client) throws IOException, InterruptedException {

        while (true) {
            String command;
            Command now;
            Scanner scanner = new Scanner(System.in);
            try {
                while (scanner.hasNextLine()) {
                    command = scanner.nextLine();
                    now = Listener.read(command, scanner);

                    // TODO теперь будем делать AuthorCommand и передавать user,
                    //  password (хотя вроде бы надо уже передавать хэш)
                    //  поэтому в методе чтения сразу храним не пароль, а хэш
                    if (now == null) {
                        continue;
                    } else {
                        now.setUser(user);
                        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(4096); // buff size
                        ObjectOutputStream output = new ObjectOutputStream(byteOutput);
                        output.writeObject(now);
                        client.socketChannel.write(ByteBuffer.wrap(byteOutput.toByteArray())); // send command to Server
                        System.out.println("we've just sent sth to server");

                        ByteBuffer inputBuffer = ByteBuffer.allocate(4096);
                        client.socketChannel.read(inputBuffer);
                        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
                        ServerResult serverResult = (ServerResult) input.readObject();

                        System.out.println(serverResult.result);
                        if (serverResult.getResult().equals("Авторизуйтесь!!!!")) {
                            System.out.println("Введите команду log для авторизации.");
                        } else if (serverResult.getResult().equals("Успешная авторизация.") || serverResult.getResult().equals("Успешная регистрация")){
                            //System.out.println("Авторизация прошла успешно" + serverResult.getUser());
                            System.out.println("запоминаем юзера");
                            user = serverResult.getUser();
                            //user = serverResult.getUser();
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + e.toString());
                System.out.println("Соединение с сервером потеряно. Попробуем еще раз через некоторое время.");
                client.connect(PORT);
            }
            socketChannel.close();
        }
    }
}

