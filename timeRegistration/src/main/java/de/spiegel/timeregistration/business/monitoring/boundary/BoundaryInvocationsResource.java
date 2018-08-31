package de.spiegel.timeregistration.business.monitoring.boundary;

import de.spiegel.timeregistration.business.monitoring.entity.CallEvent;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author veithi
 */
@Stateless
@Path("boundary-invocations")
public class BoundaryInvocationsResource {

    @Inject
    MonitorSink ms;

    @GET
    public List<CallEvent> expose() {
        return this.ms.recentEvents;
    }

}
