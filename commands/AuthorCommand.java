/*
package commands;

import client.User;
import tools.Console;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public class AuthorCommand implements Serializable {

    public Command command;
    //public Logger logger;
    public User user;

    public AuthorCommand(Command command, User user) {
        this.command = command;
        this.user = user;
    }

    public String doit(Console console) throws IOException, SQLException {
        if (!console.databaseHandler.checkUser(user.username, user.password)) {
            //TODO вроде бы такого не должно быть,
            // ибо мы сначала авторизируемся а потом уже делваем запросы
            return "Не авторизированный пользователь! Поиск по БД не выявил совпадений!";
        }
        //console.setUser(user.username);
        String result = this.command.doit(console);
        console.databaseHandler.update();
        return result;
    };
}

 */
