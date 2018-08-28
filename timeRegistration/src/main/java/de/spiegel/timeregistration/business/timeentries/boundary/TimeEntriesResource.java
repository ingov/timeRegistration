package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author veithi
 */
@Stateless
@Path("timeentries")
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
    public Response save(TimeEntry timeEntry, @Context UriInfo uriInfo) {
        TimeEntry saved = this.manager.save(timeEntry);
        long id = saved.getId();
        URI location = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(location).build();
    }

}
