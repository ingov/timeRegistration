package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author veithi
 */
@Stateless
public class TimeEntryManager {

    @PersistenceContext
    EntityManager em;

    public TimeEntry findById(long id) {
        return this.em.find(TimeEntry.class, id);
    }

    public void delete(long id) {
        TimeEntry reference = this.em.getReference(TimeEntry.class, id);
        this.em.remove(reference);
    }

    public List<TimeEntry> all() {
        return this.em.createNamedQuery(TimeEntry.findAll, TimeEntry.class)
                .getResultList();
    }

    public TimeEntry save(TimeEntry timeEntry) {
        return this.em.merge(timeEntry);
    }

}
