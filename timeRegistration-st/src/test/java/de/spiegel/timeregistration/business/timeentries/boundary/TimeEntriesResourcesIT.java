package de.spiegel.timeregistration.business.timeentries.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author veithi
 */
public class TimeEntriesResourcesIT {

    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/timeRegistration/api/timeentries");

    @Test
    public void crud() {
        JsonObjectBuilder timeEntryBuilder = Json.createObjectBuilder();
        JsonObject timeEntryJson = timeEntryBuilder.add("caption", "implement")
                .add("description", "...")
                .add("priority", 42).build();

        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        assertThat(postReponse.getStatus(), is(201));
        String loaction = postReponse.getHeaderString("Location");
        System.out.println("loaction = " + loaction);

        Response response = this.provider.target()
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(200));
        JsonArray allTimeEntries = response.readEntity(JsonArray.class);
        assertFalse(allTimeEntries.isEmpty());

        // GET with id
        JsonObject dedicatedTimeEntry = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(dedicatedTimeEntry.getString("caption").contains("42"));

        Response deleteResponse = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .delete();
//        System.out.println("deleteResponse = " + deleteResponse);
        assertThat(deleteResponse.getStatus(), is(204));
    }

}
