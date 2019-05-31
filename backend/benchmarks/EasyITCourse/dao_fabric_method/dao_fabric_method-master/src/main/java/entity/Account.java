package entity;

import java.util.List;

public class Account {
    private int id;
    private String username;
    private String password;
    public Account() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Object getRoles() {
        return new Object();
    }


}
