package de.spiegel.timeregistration.business.timeentries.entity;

import de.spiegel.timeregistration.business.timeentries.boundary.ChangeEvent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 *
 * @author veithi
 */
class TimeEntryAuditor {

    @Inject
    @ChangeEvent(ChangeEvent.Type.CREATION)
    Event<TimeEntry> create;
    @Inject
    @ChangeEvent(ChangeEvent.Type.UPDATE)
    Event<TimeEntry> update;

    @PostPersist
    public void onPersist(TimeEntry timeEntry) {
        this.create.fire(timeEntry);
    }

    @PostUpdate
    public void onUpdate(TimeEntry timeEntry) {
        this.update.fire(timeEntry);
    }

}
