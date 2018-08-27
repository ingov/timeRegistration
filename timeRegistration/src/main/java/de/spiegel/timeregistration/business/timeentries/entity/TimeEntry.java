package de.spiegel.timeregistration.business.timeentries.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author veithi
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeEntry {

    private String caption;
    private String description;
    private int priority;

    public TimeEntry(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }

    public TimeEntry() {
    }

}
