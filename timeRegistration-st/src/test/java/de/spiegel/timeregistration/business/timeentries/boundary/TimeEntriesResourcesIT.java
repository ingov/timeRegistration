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
import static org.junit.Assert.assertNotNull;
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
        JsonObject timeEntryJson = timeEntryBuilder
                .add("caption", "implement 42")
                .add("description", "...")
                .add("priority", 10)
                .build();

        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        assertThat(postReponse.getStatus(), is(201));
        String location = postReponse.getHeaderString("Location");
        System.out.println("loaction = " + location);

        // find all
        Response response = this.provider.target()
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(200));
        JsonArray allTimeEntries = response.readEntity(JsonArray.class);
        assertFalse(allTimeEntries.isEmpty());

        // find
        JsonObject dedicatedTimeEntry = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(dedicatedTimeEntry.getString("caption").contains("42"));
        long version = dedicatedTimeEntry.getJsonNumber("version").longValue();

        //update
        JsonObjectBuilder updateBuilder = Json.createObjectBuilder();
        JsonObject updated = updateBuilder.
                add("caption", "implemented").
                add("version", version).
                build();

        Response updateResponse = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON)
                .put(Entity.json(updated));
        assertThat(updateResponse.getStatus(), is(200));

        //update again
        updateBuilder = Json.createObjectBuilder();
        updated = updateBuilder.
                add("caption", "implemented").
                add("priority", 42).
                build();

        updateResponse = this.provider.client().
                target(location).
                request(MediaType.APPLICATION_JSON)
                .put(Entity.json(updated));
        assertThat(updateResponse.getStatus(), is(409));
        String conflictInformation = updateResponse.getHeaderString("cause");
        assertNotNull(conflictInformation);
        System.out.println("conflictInformation = " + conflictInformation);

        //find again
        JsonObject updatedTimeEntry = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(updatedTimeEntry.getString("caption").contains("implemented"));

        // update status
        JsonObjectBuilder statusBuilder = Json.createObjectBuilder();
        JsonObject status = updateBuilder
                .add("done", true)
                .build();

        Response statusResponse = this.provider.client().target(location)
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(status));

        //verify status
        updatedTimeEntry = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(updatedTimeEntry.getBoolean("done"));

        // update not existing status
        JsonObjectBuilder notexistingBuilder = Json.createObjectBuilder();
        status = updateBuilder
                .add("done", true)
                .build();

        statusResponse = this.provider.target()
                .path("-42")
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(status));
        assertThat(statusResponse.getStatus(), is(400));
        assertFalse(statusResponse.getHeaderString("reason").isEmpty());

        // update malformed status
        JsonObjectBuilder malformedBuilder = Json.createObjectBuilder();
        status = updateBuilder
                .add("womething wrong", true)
                .build();

        statusResponse = this.provider.client()
                .target(location)
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(status));
        assertThat(statusResponse.getStatus(), is(400));
        assertFalse(statusResponse.getHeaderString("reason").isEmpty());

//      delete non-existing
        Response deleteResponse = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        System.out.println("deleteResponse = " + deleteResponse);
        assertThat(deleteResponse.getStatus(), is(204));
    }

    @Test
    public void createTimeEntryWithoutCaption() {
        JsonObjectBuilder timeEntryBuilder = Json.createObjectBuilder();
        JsonObject timeEntryJson = timeEntryBuilder
                .add("priority", 42)
                .build();
        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        assertThat(postReponse.getStatus(), is(400));
        postReponse.getHeaders().entrySet().forEach(System.out::println);
    }

    @Test
    public void createValideTimeEntry() {
        JsonObjectBuilder timeEntryBuilder = Json.createObjectBuilder();
        JsonObject timeEntryJson = timeEntryBuilder
                .add("caption", "sdsa")
                .add("description", "...")
                .add("priority", 42)
                .build();
        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        assertThat(postReponse.getStatus(), is(201));
        postReponse.getHeaders().entrySet().forEach(System.out::println);
    }

    @Test
    public void createTimeEntryWithHighPrio() {
        JsonObjectBuilder timeEntryBuilder = Json.createObjectBuilder();
        JsonObject timeEntryJson = timeEntryBuilder
                .add("caption", "10")
                .add("priority", 12)
                .build();
        Response postReponse = this.provider.target().request().post(Entity.json(timeEntryJson));
        postReponse.getHeaders().entrySet().forEach(System.out::println);
        assertThat(postReponse.getStatus(), is(400));
    }
}
