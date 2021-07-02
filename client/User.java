package client;

import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public String toString() {
        if(this == null) {
            return "user = null";
        } else {
            return username + "   " + password;
        }
    }
}
