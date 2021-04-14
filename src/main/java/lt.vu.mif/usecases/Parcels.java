package lt.vu.mif.usecases;

import lt.vu.mif.entities.Parcel;
import lt.vu.mif.persistence.ParcelsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Parcels {

    private List<Parcel> parcelList;

    @Inject
    private ParcelsDAO parcelsDAO;

    private Parcel parcelToCreate = new Parcel();

    @PostConstruct
    public void init(){
        loadParcels();
    }

    private void loadParcels() {
        parcelList = parcelsDAO.loadAll();
    }

    public List<Parcel> getParcelList(){
        return parcelList;
    }

    @Transactional
    public String createParcel(){
        parcelsDAO.persist(parcelToCreate);
        return "index?faces-redirect=true";
    }

    public Parcel getParcelToCreate(){
        return parcelToCreate;
    }

    public void setParcelToCreate(Parcel p){
        parcelToCreate = p;
    }
}
