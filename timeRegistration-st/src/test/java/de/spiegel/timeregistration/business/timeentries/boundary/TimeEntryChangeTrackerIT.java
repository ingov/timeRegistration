package de.spiegel.timeregistration.business.timeentries.boundary;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.json.JsonObject;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author veithi
 */
public class TimeEntryChangeTrackerIT {

    private WebSocketContainer container;
    private ChangesListener listener;

    @Before
    public void initContainer() throws URISyntaxException, DeploymentException, IOException {
        this.container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/timeRegistration/changes");
        this.listener = new ChangesListener();
        ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
                .decoders(Arrays.asList(JsonDecoder.class)).build();
        this.container.connectToServer(this.listener, cec, uri);

    }

    @Test
    public void reciveNotivications() throws InterruptedException {
        JsonObject message = this.listener.getMessage();
        assertNotNull(message);
        System.out.println("message = " + message);
    }

}
