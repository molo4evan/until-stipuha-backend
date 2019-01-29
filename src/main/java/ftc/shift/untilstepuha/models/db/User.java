package ftc.shift.untilstepuha.models.db;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String token;
    private String name;
    private int karma;
    private double maxRequest;
    private double balance;

    public User(){}

    public User(String id, String token, String name, int karma, double maxRequest, double balance) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.karma = karma;
        this.maxRequest = maxRequest;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public double getMaxRequest() {
        return maxRequest;
    }

    public void setMaxRequest(double maxRequest) {
        this.maxRequest = maxRequest;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
