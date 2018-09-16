package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.encoders.JsonEncoder;
import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.io.IOException;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author veithi
 */
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@ServerEndpoint(value = "/changes", encoders = {JsonEncoder.class})
public class TimeEntryChangeTracker {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() {
        this.session = null;
    }

    public void onTimeEntryChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) @ChangeEvent(ChangeEvent.Type.CREATION) TimeEntry timeEntry) throws EncodeException {
        if (this.session != null && this.session.isOpen()) {
            try {
                JsonObject event = Json.createObjectBuilder()
                        .add("id", timeEntry.getId())
                        .add("cause", "creation")
                        .build();
                this.session.getBasicRemote().sendObject(event);
            } catch (IOException ex) {
                // we ignore
            }

        }
    }

}
