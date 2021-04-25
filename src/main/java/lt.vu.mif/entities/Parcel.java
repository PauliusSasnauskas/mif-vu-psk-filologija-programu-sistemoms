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

    private float length;
    private float width;
    private float height;

    private float weight;

    private BigDecimal price;

    private String recipient;
    private String street;
    private int houseNumber;
    private int flatNumber;
    private String city;
    private String country;
    private int postalCode;
}
