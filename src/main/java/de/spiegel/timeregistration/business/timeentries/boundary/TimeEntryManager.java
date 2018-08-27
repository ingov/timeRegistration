package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author veithi
 */
@Stateless
public class TimeEntryManager {

    public TimeEntry findById(long id) {
        return new TimeEntry("implement REST endpint" + id, "...", 100);
    }

    public void delete(long id) {
        System.err.println("deleted = " + id);
    }

    public List<TimeEntry> all() {
        List<TimeEntry> all = new ArrayList<>();
        all.add(findById(42));
        return all;
    }

    public void save(TimeEntry timeEntry) {
        System.out.println("timeEntry = " + timeEntry);
    }

}
