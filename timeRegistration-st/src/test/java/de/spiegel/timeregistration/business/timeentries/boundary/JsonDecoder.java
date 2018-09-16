package de.spiegel.timeregistration.business.timeentries.boundary;

import java.io.IOException;
import java.io.Reader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author ingoveith
 */
public class JsonDecoder implements Decoder.TextStream<JsonObject> {

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public JsonObject decode(Reader reader) throws DecodeException, IOException {
        try (JsonReader jsonReader = Json.createReader(reader)) {
            return jsonReader.readObject();
        }
    }

    @Override
    public void destroy() {
    }

}
