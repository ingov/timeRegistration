package de.spiegel.timeregistration.business.timeentries.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import javax.json.JsonArray;
import javax.ws.rs.core.MediaType;
import org.junit.Assert;
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
        JsonArray all = this.provider.target().request(MediaType.APPLICATION_JSON).get(JsonArray.class);
        Assert.assertFalse(all.isEmpty());
        
    }
    
    
}
