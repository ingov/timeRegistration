package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.logging.boundary.BoundaryLogger;
import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author veithi
 */
@Stateless
@Interceptors(BoundaryLogger.class)
public class TimeEntryManager {

    @PersistenceContext
    EntityManager em;

    public TimeEntry findById(long id) {
        return this.em.find(TimeEntry.class, id);
    }

    public void delete(long id) {
        try {
            TimeEntry reference = this.em.getReference(TimeEntry.class, id);
            this.em.remove(reference);
        } catch (EntityNotFoundException e) {
            // we want to remove it... 
        }
    }

    public List<TimeEntry> all() {
        return this.em.createNamedQuery(TimeEntry.findAll, TimeEntry.class)
                .getResultList();
    }

    public TimeEntry save(TimeEntry timeEntry) {
        return this.em.merge(timeEntry);
    }

    public TimeEntry updateStatus(long id, boolean done) {
        TimeEntry timeEntry = this.findById(id);
        if (timeEntry == null) {
            return null;
        }
        timeEntry.setDone(done);
        return timeEntry;
    }

}
