package ftc.shift.untilstepuha.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String id;
    private String name;
    private String description;
    private String authorID;
    private double aim;
    private double balance;
}
