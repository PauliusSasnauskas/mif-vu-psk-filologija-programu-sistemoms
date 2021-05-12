package lt.vu.mif.logic;

import lt.vu.mif.entities.Parcel;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Default
public class PaymentProcessingFast implements PaymentProcessingStrategy{

    private String getTodaysDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Inject
    private ParcelsDAO parcelsDAO;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Parcel processParcelPayment(Parcel p) {
        System.out.println("Added0...");
        p.setSentDate(getTodaysDate());
        p.setStatus("Išsiųsta");
        parcelsDAO.persist(p);
        return p;
    }
}
