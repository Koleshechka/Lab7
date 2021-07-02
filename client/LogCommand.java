package client;

import commands.Command;
import database.DatabaseHandler;
import tools.Console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class LogCommand extends Command {
    public User user;

    public LogCommand(User user) {
        this.user = user;
    }

    @Override
    public String doit(Console console) throws IOException, SQLException {
        /*
        if(console.getDatabaseHandler().registerUser(this.getUser())) {
            return "Авторизиация прошла успешно";
        } else return "Вы дурак";

         */
        if(console.getDatabaseHandler() == null) {
            return "неа";
        }else {
            return console.getDatabaseHandler().registerUser(this.getUser());
        }
    }

    public User getUser() {return user;}
}
