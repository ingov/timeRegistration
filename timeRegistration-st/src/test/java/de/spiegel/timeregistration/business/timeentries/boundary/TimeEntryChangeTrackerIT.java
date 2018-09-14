package de.spiegel.timeregistration.business.timeentries.boundary;

import javax.websocket.ContainerProvider;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author veithi
 */
public class TimeEntryChangeTrackerIT {

    @Before
    public void initContainer() {
        this.container = ContainerProvider.getWebSocketContainer();
    }

    @Test
    public void method() {

    }

}
