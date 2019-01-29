package ftc.shift.untilstepuha.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;
    private String token;
    private String name;
    private String password;
    private int karma;
    private double maxRequest;
    private double balance;
}
