package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author veithi
 */
public class TimeEntryResource {

    long id;
    TimeEntryManager manager;

    public TimeEntryResource(long id, TimeEntryManager manager) {
        this.id = id;
        this.manager = manager;
    }

    @GET
    public TimeEntry find() {
        return this.manager.findById(id);
    }

    @PUT
    public TimeEntry delete(TimeEntry timeEntry) {
        timeEntry.setId(id);
        return this.manager.save(timeEntry);
    }

    @DELETE
    public void delete() {
        this.manager.delete(id);
    }

    @PUT
    @Path("/status")
    public Response statusUpdate(JsonObject statusUpdate) {
        if (!statusUpdate.containsKey("done")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "JSON should contains field done")
                    .build();
        }

        boolean done = statusUpdate.getBoolean("done");

        TimeEntry timeEntry = this.manager.updateStatus(id, done);
        if (timeEntry == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "timeEntry with id " + id + " does not exist")
                    .build();
        } else {
            return Response.ok(timeEntry).build();
        }
    }

}
