package ftc.shift.untilstepuha.models.db;

public class Request {
    private String id;
    private String name;
    private String description;
    private String authorID;
    private double aim;
    private double balance;

    Request(){}

    public Request(String id, String name, String description, String authorID, double aim, double balance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authorID = authorID;
        this.aim = aim;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public double getAim() {
        return aim;
    }

    public void setAim(double aim) {
        this.aim = aim;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
