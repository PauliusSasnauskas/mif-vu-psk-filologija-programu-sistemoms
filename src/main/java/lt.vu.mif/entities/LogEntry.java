package lt.vu.mif.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "LogEntry.findAll", query = "select e from LogEntry as e")
})
@Getter @Setter
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String userRights;

    private String datetime;

    private String classAndMethodName;
}
