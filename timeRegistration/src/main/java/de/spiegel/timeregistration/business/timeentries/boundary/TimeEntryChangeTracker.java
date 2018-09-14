package de.spiegel.timeregistration.business.timeentries.boundary;

import de.spiegel.timeregistration.business.timeentries.entity.TimeEntry;
import java.io.IOException;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
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
@ServerEndpoint("/changes")
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

    public void onTimeEntryChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) TimeEntry timeEntry) {
        if (this.session != null && this.session.isOpen()) {
            try {
                this.session.getBasicRemote().sendText(timeEntry.toString());
            } catch (IOException ex) {
                // we ignore
            }

        }
    }

}
