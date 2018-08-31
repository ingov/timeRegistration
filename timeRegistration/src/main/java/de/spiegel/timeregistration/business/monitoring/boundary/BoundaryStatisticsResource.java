package de.spiegel.timeregistration.business.monitoring.boundary;

import java.util.LongSummaryStatistics;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author veithi
 */
@Stateless
@Path("boundary-statistics")
public class BoundaryStatisticsResource {

    @Inject
    MonitorSink ms;

    @GET
    public JsonObject statistics() {
        LongSummaryStatistics statistics = ms.getStatistics();
        return Json.createObjectBuilder().add("average-duration", statistics.getAverage())
                .add("invocation-count", statistics.getCount())
                .add("min-count", statistics.getMin())
                .add("max-count", statistics.getMax())
                .build();


    }

}
