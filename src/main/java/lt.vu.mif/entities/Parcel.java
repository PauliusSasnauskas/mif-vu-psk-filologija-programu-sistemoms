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
@ToString

public class Parcel implements Serializable {

    public static final int PAY_OPTION_BANK = 0;
    public static final int PAY_OPTION_CASH = 1;
    public static final int PAY_OPTION_PAYPAL = 2;

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
    private String sentDate;
    private String status;

    private int payOption;
}
