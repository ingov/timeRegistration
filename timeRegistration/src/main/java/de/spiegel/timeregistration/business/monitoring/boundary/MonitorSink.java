package de.spiegel.timeregistration.business.monitoring.boundary;

import de.spiegel.timeregistration.business.logging.boundary.LogSink;
import de.spiegel.timeregistration.business.monitoring.entity.CallEvent;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author veithi
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MonitorSink {

    @Inject
    LogSink LOG;

    CopyOnWriteArrayList<CallEvent> recentEvents;

    @PostConstruct
    public void init() {
        this.recentEvents = new CopyOnWriteArrayList<>();
    }


    public void onCallEvent(@Observes CallEvent event) {
        LOG.log(event.toString());
        recentEvents.add(event);
    }
    public List<CallEvent> getRecentEvents() {
        return this.recentEvents;
    }

    public LongSummaryStatistics getStatistics() {
        return this.recentEvents.stream()
                .collect(Collectors.summarizingLong(CallEvent::getDration));

    }


}
