package ftc.shift.untilstepuha.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "karma", nullable = false)
    private int karma;

    @Column(name = "max_request", nullable = false)
    private double maxRequest;

    @Column(name = "balance", nullable = false)
    private double balance;
}
