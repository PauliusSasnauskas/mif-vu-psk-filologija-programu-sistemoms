package lt.vu.mif.persistence;

import lt.vu.mif.entities.Parcel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ParcelsDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Parcel> loadAll() {
        return em.createNamedQuery("Parcel.findAll", Parcel.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Parcel p){
        this.em.persist(p);
    }

    public Parcel findOne(int id) {
        return em.find(Parcel.class, id);
    }
}
