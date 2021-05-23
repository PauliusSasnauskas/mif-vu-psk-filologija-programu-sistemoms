package lt.vu.mif.logic;

import lt.vu.mif.entities.Parcel;

import java.io.Serializable;

public interface PaymentProcessingStrategy extends Serializable {
    Parcel processParcelPayment(Parcel p);
}
