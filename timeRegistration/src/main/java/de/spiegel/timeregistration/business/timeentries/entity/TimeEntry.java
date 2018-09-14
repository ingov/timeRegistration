package de.spiegel.timeregistration.business.timeentries.entity;

import de.spiegel.timeregistration.business.CrossCheck;
import de.spiegel.timeregistration.business.ValidEntity;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author veithi
 */
@Entity
@NamedQuery(name = TimeEntry.findAll, query = "SELECT t from TimeEntry t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CrossCheck
@EntityListeners(TimeEntryAuditor.class)
public class TimeEntry implements ValidEntity {

    @Id
    @GeneratedValue
    private long id;
    static final String PREFIX = "timeentries.entity.TimeEntry.";
    public static final String findAll = PREFIX + "findAll";

    @NotNull
    @Size(min = 2, max = 256)
    private String caption;
    private String description;
    private int priority;
    private boolean done;

    @Version
    private long version;

    public TimeEntry(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }

    public TimeEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean isValide() {
        if (this.priority <= 10) {
            return true;
        }
        return this.description != null && !this.description.isEmpty();
    }

}
