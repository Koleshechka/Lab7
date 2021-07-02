package commands;

import client.User;
import database.DatabaseHandler;
import tools.Console;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class Command implements Serializable {
    public User user;

    public abstract String doit(Console console) throws IOException, SQLException;

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {return user;}
}
