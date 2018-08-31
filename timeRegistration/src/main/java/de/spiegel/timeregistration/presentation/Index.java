package de.spiegel.timeregistration.presentation;

import de.spiegel.timeregistration.business.timeentries.boundary.TimeEntryManager;
import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author veithi
 */
@Model
public class Index {

    @Inject
    TimeEntryManager boundary;

    @Inject
    Validator validator;

    TimeEntry timeEntry;

    @PostConstruct
    public void init() {
        this.timeEntry = new TimeEntry();
    }

    public TimeEntry getTimeEntry() {
        return this.timeEntry;
    }

    public List<TimeEntry> getTimeEntries() {
        return this.boundary.all();
    }

    public void showValidationError(String content) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, content, content);
        FacesContext.getCurrentInstance().addMessage("", message);
    }


    public Object save() {
        Set<ConstraintViolation<TimeEntry>> validate = this.validator.validate(this.timeEntry);
        validate.forEach(violation
                -> {
            this.showValidationError(violation.getMessage());
        });
        if (validate.isEmpty()) {
            this.boundary.save(timeEntry);
        }
        return null;
    }

}
