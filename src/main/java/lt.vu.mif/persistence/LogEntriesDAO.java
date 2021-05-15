package lt.vu.mif.persistence;

import lt.vu.mif.entities.LogEntry;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LogEntriesDAO {
    @PersistenceContext
    private EntityManager em;

    public List<LogEntry> loadAll() {
        return em.createNamedQuery("LogEntry.findAll", LogEntry.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void persist(LogEntry le){
        this.em.persist(le);
    }

    public LogEntry findOne(int id) { return em.find(LogEntry.class, id); }

    @Transactional
    public LogEntry merge(LogEntry le){
        return this.em.merge(le);
    }
}
