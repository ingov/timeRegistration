package de.spiegel.timeregistration.business.timeentries.entity;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;

/**
 *
 * @author veithi
 */
class TimeEntryAuditor {

    @Inject
    Event<TimeEntry> events;

    @PostPersist
    public void onTimeEntryUpdate(TimeEntry timeEntry) {
        this.events.fire(timeEntry);
    }


}
