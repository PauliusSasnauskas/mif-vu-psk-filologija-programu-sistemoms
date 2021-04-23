package lt.vu.mif.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NamedQueries({
        @NamedQuery(name = "Parcel.findAll", query = "select a from Parcel as a")
})
@Data

public class Parcel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

//    @Column(name = "LENGTH")
    private float length;

//    @Column(name = "WIDTH")
    private float width;

//    @Column(name = "HEIGHT")
    private float height;

    //    @Column(name = "MASS")
    private float weight;

    // @Column(name = "PRICE")
    private BigDecimal price;
}
