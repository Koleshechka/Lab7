package server;

import client.User;

import java.io.Serializable;

public class ServerResult implements Serializable {

    public String result = "no result";
    public User user;

    public ServerResult(String s , User user) {
        this.result = s;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {this.user = user;}
    public String getResult() {return result;}
    public void setResult(String result) {
        this.result = result;
    }

}
