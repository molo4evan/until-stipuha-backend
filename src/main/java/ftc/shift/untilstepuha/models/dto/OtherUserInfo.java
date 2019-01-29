package ftc.shift.untilstepuha.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherUserInfo implements IUserInfo {
    private String id;
    private String name;
    private int karma;
    private double maxRequest;
}
