package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author veithi
 */
@Stateless
@Path("timeEntries")
public class TimeEntriesResource {

    @Inject
    TimeEntryManager manager;

    @GET
    @Path("{id}")
    public TimeEntry find(@PathParam("id") long id) {
        return this.manager.findById(id);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        this.manager.delete(id);
    }

    @GET
    public List<TimeEntry> all() {
        return this.manager.all();

    }

    @POST
    public void save(TimeEntry timeEntry) {
        this.manager.save(timeEntry);
    }



}
