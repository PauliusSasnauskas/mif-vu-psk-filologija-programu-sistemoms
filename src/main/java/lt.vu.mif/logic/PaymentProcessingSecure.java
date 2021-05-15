package lt.vu.mif.logic;

import lt.vu.mif.entities.Parcel;
import lt.vu.mif.interceptors.LoggedInvocation;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Date;

@Alternative
@ApplicationScoped
public class PaymentProcessingSecure implements PaymentProcessingStrategy{

    private String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    @Inject
    private ParcelsDAO parcelsDAO;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private void processStep1(Parcel p){
        p.setSentDate("-");
        p.setStatus("Apmokėjimas apdorojamas...");
        parcelsDAO.persist(p);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private Parcel processStep2(Parcel p){
        p.setSentDate(getCurrentDate());
        p.setStatus("Išsiųsta");
        p = parcelsDAO.merge(p);
        return p;
    }

    @Override
    @LoggedInvocation
    public Parcel processParcelPayment(Parcel p) {

        processStep1(p);

        try {
            // log things
            Thread.sleep(7000); // Simulate security checks and other operations
        } catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("Thread sleep failed");
        }

        p = processStep2(p);
        return p;
    }
}
